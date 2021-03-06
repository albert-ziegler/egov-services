package org.egov.pt.calculator.service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.pt.calculator.util.CalculatorConstants;
import org.egov.pt.calculator.util.Configurations;
import org.egov.pt.calculator.validator.CalculationValidator;
import org.egov.pt.calculator.web.models.BillingSlab;
import org.egov.pt.calculator.web.models.BillingSlabSearchCriteria;
import org.egov.pt.calculator.web.models.Calculation;
import org.egov.pt.calculator.web.models.CalculationCriteria;
import org.egov.pt.calculator.web.models.CalculationReq;
import org.egov.pt.calculator.web.models.CalculationRes;
import org.egov.pt.calculator.web.models.TaxHeadEstimate;
import org.egov.pt.calculator.web.models.demand.Category;
import org.egov.pt.calculator.web.models.demand.TaxHeadMaster;
import org.egov.pt.calculator.web.models.property.OwnerInfo;
import org.egov.pt.calculator.web.models.property.Property;
import org.egov.pt.calculator.web.models.property.PropertyDetail;
import org.egov.pt.calculator.web.models.property.Unit;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

import static org.egov.pt.calculator.util.CalculatorConstants.*;

@Service
@Slf4j
public class EstimationService {

	@Autowired
	private BillingSlabService billingSlabService;

	@Autowired
	private PayService payService;

	@Autowired
	private Configurations configs;

	@Autowired
	private MasterDataService mDataService;

	@Autowired
	private DemandService demandService;

	@Autowired
	CalculationValidator calcValidator;

	/**
	 * Generates a map with assessment-number of property as key and estimation
	 * map(taxhead code as key, amount to be paid as value) as value
	 * will be called by calculate api
	 *
	 * @param request incoming calculation request containing the criteria.
	 * @return Map<String, Calculation> key of assessment number and value of calculation object.
	 */
	public Map<String, Calculation> getEstimationPropertyMap(CalculationReq request) {

		RequestInfo requestInfo = request.getRequestInfo();
		List<CalculationCriteria> criteriaList = request.getCalculationCriteria();
		Map<String, Calculation> calculationPropertyMap = new HashMap<>();
		for (CalculationCriteria criteria : criteriaList) {
			Property property = criteria.getProperty();
			PropertyDetail detail = property.getPropertyDetails().get(0);
			calcValidator.validatePropertyForCalculation(detail);
			String assessmentNumber = detail.getAssessmentNumber();
			Calculation calculation = getCalculation(requestInfo, criteria, getEstimationMap(criteria, requestInfo));
			calculation.setServiceNumber(assessmentNumber);
			calculationPropertyMap.put(assessmentNumber, calculation);
		}
		return calculationPropertyMap;
	}

	/**
	 * Method to estimate the tax to be paid for given property
	 * will be called by estimate api
	 *
	 * @param request incoming calculation request containing the criteria.
	 * @return CalculationRes calculation object containing all the tax for the given criteria.
	 */
    public CalculationRes getTaxCalculation(CalculationReq request) {

        CalculationCriteria criteria = request.getCalculationCriteria().get(0);
        Property property = criteria.getProperty();
        PropertyDetail detail = property.getPropertyDetails().get(0);
        calcValidator.validatePropertyForCalculation(detail);
        return new CalculationRes(new ResponseInfo(), Collections.singletonList(getCalculation(request.getRequestInfo(), criteria,
                getEstimationMap(criteria, request.getRequestInfo()))));
    }

	/**
	 * Generates a List of Tax head estimates with tax head code,
	 * tax head category and the amount to be collected for the key.
     *
     * @param criteria criteria based on which calculation will be done.
     * @param requestInfo request info from incoming request.
	 * @return Map<String, Double>
	 */
	private Map<String,List> getEstimationMap(CalculationCriteria criteria, RequestInfo requestInfo) {

		BigDecimal taxAmt = BigDecimal.ZERO;
		BigDecimal usageExemption = BigDecimal.ZERO;
		Property property = criteria.getProperty();
		PropertyDetail detail = property.getPropertyDetails().get(0);
		String assessmentYear = detail.getFinancialYear();
		String tenantId = property.getTenantId();

		List<BillingSlab> filteredBillingSlabs = getSlabsFiltered(property, requestInfo);

		Map<String, Map<String, List<Object>>> propertyBasedExemptionMasterMap = new HashMap<>();
		Map<String, JSONArray> timeBasedExemptionMasterMap = new HashMap<>();
		mDataService.setPropertyMasterValues(requestInfo, tenantId, propertyBasedExemptionMasterMap,
				timeBasedExemptionMasterMap);

		List<String> billingSlabIds = new LinkedList<>();

		/*
		 * by default land should get only one slab from database per tenantId
		 */
		if (PT_TYPE_VACANT_LAND.equalsIgnoreCase(detail.getPropertyType()) && filteredBillingSlabs.size() != 1)
			throw new CustomException(PT_ESTIMATE_BILLINGSLABS_UNMATCH_VACANCT,PT_ESTIMATE_BILLINGSLABS_UNMATCH_VACANT_MSG
					.replace("{count}",String.valueOf(filteredBillingSlabs.size())));

		else if (PT_TYPE_VACANT_LAND.equalsIgnoreCase(detail.getPropertyType())) {
			taxAmt = taxAmt.add(BigDecimal.valueOf(filteredBillingSlabs.get(0).getUnitRate() * detail.getLandArea()));
		} else {

			double unBuiltRate = 0.0;
			int groundUnitsCount = 0;
			Double groundUnitsArea = 0.0;
			int i = 0;

			for (Unit unit : detail.getUnits()) {

				BillingSlab slab = getSlabForCalc(filteredBillingSlabs, unit);
				BigDecimal currentUnitTax = getTaxForUnit(slab, unit);
				billingSlabIds.add(slab.getId()+"|"+i);

				/*
				 * counting the number of units & total area in ground floor for unbuilt area
				 * tax calculation
				 */
				if (unit.getFloorNo().equalsIgnoreCase("0")) {
					groundUnitsCount += 1;
					groundUnitsArea += unit.getUnitArea();
					if (null != slab.getUnBuiltUnitRate())
						unBuiltRate += slab.getUnBuiltUnitRate();
				}
				taxAmt = taxAmt.add(currentUnitTax);
				usageExemption = usageExemption
						.add(getExemption(unit, currentUnitTax, assessmentYear, propertyBasedExemptionMasterMap));
				i++;
			}
			/*
			 * making call to get unbuilt area tax estimate
			 */
			taxAmt = taxAmt.add(getUnBuiltRate(detail, unBuiltRate, groundUnitsCount, groundUnitsArea));

			/*
			 * special case to handle property with one unit
			 */
			if (detail.getUnits().size() == 1)
				usageExemption = getExemption(detail.getUnits().get(0), taxAmt, assessmentYear,
						propertyBasedExemptionMasterMap);
		}
		List<TaxHeadEstimate> taxHeadEstimates =  getEstimatesForTax(assessmentYear, taxAmt, usageExemption, detail, propertyBasedExemptionMasterMap,
				timeBasedExemptionMasterMap);

		Map<String,List> estimatesAndBillingSlabs = new HashMap<>();
		estimatesAndBillingSlabs.put("estimates",taxHeadEstimates);
		estimatesAndBillingSlabs.put("billingSlabIds",billingSlabIds);

		return estimatesAndBillingSlabs;

	}

	/**
	 * Private method to calculate the un-built area tax estimate
	 *
	 * gives the subtraction of landArea and buildUpArea if both are present.
	 *
	 * on absence of landArea Zero will be given.
	 *
	 * on absence of buildUpArea sum of all unit areas of ground floor
	 *
	 * will be subtracted from the landArea.
	 *
	 * the un-Built UnitRate is the average of unBuilt rates from ground units.
	 *
	 * @param detail The property detail
	 * @param unBuiltRate The unit rate for the un-built area in the given property detail.
	 * @param groundUnitsCount The count of all ground floor units.
	 * @param groundUnitsArea Sum of ground floor units area
	 * @return calculated tax for un-built area in the property detail.
	 */
	private BigDecimal getUnBuiltRate(PropertyDetail detail, double unBuiltRate, int groundUnitsCount, Double groundUnitsArea) {

        BigDecimal unBuiltAmt = BigDecimal.ZERO;
        if (0.0 < unBuiltRate && null != detail.getLandArea() && groundUnitsCount > 0) {

            double diffArea = null != detail.getBuildUpArea() ? detail.getLandArea() - detail.getBuildUpArea()
                    : detail.getLandArea() - groundUnitsArea;
            // ignoring if land Area is lesser than buildUpArea/groundUnitsAreaSum in estimate instead of throwing error
            // since property service validates the same for calculation
            diffArea = diffArea < 0.0 ? 0.0 : diffArea;
            unBuiltAmt = unBuiltAmt.add(BigDecimal.valueOf((unBuiltRate / groundUnitsCount) * (diffArea)));
        }
			return unBuiltAmt;
    }

	/**
	 * Returns Tax amount value for the unit from the list of slabs passed
	 *
	 * The tax is dependent on the unit rate and unit area for all cases
	 *
	 * except for commercial units which is rented, for this a percent will
	 *
	 * be applied on the annual rent value from the slab.
	 *
	 * arvPercent is not provided in the slab, it will be picked from the config
	 *
	 * which is common for the slab.
	 *
	 * @param slab The single billing slab that has been filtered for this particular unit.
	 * @param unit the unit for which tax should be calculated.
	 * @return calculated tax amount for the incoming unit
	 */
	private BigDecimal getTaxForUnit(BillingSlab slab, Unit unit) {

		boolean isUnitCommercial = unit.getUsageCategoryMajor().equalsIgnoreCase(configs.getUsageMajorNonResidential());
		boolean isUnitRented = unit.getOccupancyType().equalsIgnoreCase(configs.getOccupancyTypeRented());
		BigDecimal currentUnitTax;

        if (null == slab) {
            String msg = BILLING_SLAB_MATCH_ERROR_MESSAGE
                    .replace(BILLING_SLAB_MATCH_AREA, unit.getUnitArea().toString())
                    .replace(BILLING_SLAB_MATCH_FLOOR, unit.getFloorNo())
                    .replace(BILLING_SLAB_MATCH_USAGE_DETAIL,
                     null != unit.getUsageCategoryDetail() ? unit.getUsageCategoryDetail() : "nill");
            throw new CustomException(BILLING_SLAB_MATCH_ERROR_CODE, msg);
        }

		if (isUnitCommercial && isUnitRented) {

			if (unit.getArv() == null)
                throw new CustomException(EG_PT_ESTIMATE_ARV_NULL, EG_PT_ESTIMATE_ARV_NULL_MSG);

			BigDecimal multiplier;
			if (null != slab.getArvPercent())
				multiplier = BigDecimal.valueOf(slab.getArvPercent() / 100);
			else
				multiplier = BigDecimal.valueOf(configs.getArvPercent() / 100);
			currentUnitTax = unit.getArv().multiply(multiplier);
		} else {
			currentUnitTax = BigDecimal.valueOf(unit.getUnitArea() * slab.getUnitRate());
		}
		return currentUnitTax;
	}

	/**
	 * Return an Estimate list containing all the required tax heads
	 * mapped with respective amt to be paid.
	 *
	 * @param assessmentYear year for which calculation is being done
	 * @param taxAmt tax amount for which rebate & penalty will be applied
	 * @param usageExemption  total exemption value given for all unit usages
	 * @param detail proeprty detail object
	 * @param propertyBasedExemptionMasterMap property masters which contains exemption values associated with them
	 * @param timeBasedExemeptionMasterMap masters with period based exemption values
	 */
	private List<TaxHeadEstimate> getEstimatesForTax(String assessmentYear, BigDecimal taxAmt, BigDecimal usageExemption, PropertyDetail detail,
			Map<String, Map<String, List<Object>>> propertyBasedExemptionMasterMap,
			Map<String, JSONArray> timeBasedExemeptionMasterMap) {

		BigDecimal payableTax = taxAmt;
		List<TaxHeadEstimate> estimates = new ArrayList<>();

		// taxes
		estimates.add(TaxHeadEstimate.builder().taxHeadCode(PT_TAX).estimateAmount(taxAmt.setScale(2, 2)).build());

		// usage exemption
		estimates.add(TaxHeadEstimate.builder().taxHeadCode(PT_UNIT_USAGE_EXEMPTION).estimateAmount(
		        usageExemption.setScale(2, 2)).build());
		payableTax = payableTax.subtract(usageExemption);

		// owner exemption
		BigDecimal userExemption = getExemption(detail.getOwners(), payableTax, assessmentYear, propertyBasedExemptionMasterMap);
		estimates.add(TaxHeadEstimate.builder().taxHeadCode(PT_OWNER_EXEMPTION).estimateAmount(
		        userExemption.setScale(2, 2)).build());
		payableTax = payableTax.subtract(userExemption);

		// Fire cess
		List<Object> fireCessMasterList = timeBasedExemeptionMasterMap.get(CalculatorConstants.FIRE_CESS_MASTER);
		BigDecimal fireCess = mDataService.getCess(payableTax, assessmentYear, fireCessMasterList);
		estimates.add(
				TaxHeadEstimate.builder().taxHeadCode(PT_FIRE_CESS).estimateAmount(fireCess.setScale(2, 2)).build());

		// Cancer cess
		List<Object> cancerCessMasterList = timeBasedExemeptionMasterMap.get(CalculatorConstants.CANCER_CESS_MASTER);
		BigDecimal cancerCess = mDataService.getCess(payableTax, assessmentYear, cancerCessMasterList);
		estimates.add(
				TaxHeadEstimate.builder().taxHeadCode(PT_CANCER_CESS).estimateAmount(cancerCess.setScale(2, 2)).build());

		// get applicable rebate and penalty
		Map<String, BigDecimal> rebatePenaltyMap = payService.applyPenaltyRebateAndInterest(payableTax, BigDecimal.ZERO,
				0L, assessmentYear, timeBasedExemeptionMasterMap);

		if (null != rebatePenaltyMap) {

			BigDecimal rebate = rebatePenaltyMap.get(PT_TIME_REBATE);
			BigDecimal penalty = rebatePenaltyMap.get(PT_TIME_PENALTY);
			BigDecimal interest = rebatePenaltyMap.get(PT_TIME_INTEREST);
			estimates.add(TaxHeadEstimate.builder().taxHeadCode(PT_TIME_REBATE).estimateAmount(rebate).build());
			estimates.add(TaxHeadEstimate.builder().taxHeadCode(PT_TIME_PENALTY).estimateAmount(penalty).build());
			estimates.add(TaxHeadEstimate.builder().taxHeadCode(PT_TIME_INTEREST).estimateAmount(interest).build());
			payableTax = payableTax.subtract(rebate).add(penalty).add(interest);
		}

		// AdHoc Values (additional rebate or penalty manually entered by the employee)
		if (null != detail.getAdhocPenalty())
			estimates.add(TaxHeadEstimate.builder().taxHeadCode(PT_ADHOC_PENALTY)
					.estimateAmount(detail.getAdhocPenalty()).build());

		if (null != detail.getAdhocExemption() && detail.getAdhocExemption().compareTo(payableTax.add(fireCess)) <= 0) {
			estimates.add(TaxHeadEstimate.builder().taxHeadCode(PT_ADHOC_REBATE)
					.estimateAmount(detail.getAdhocExemption()).build());
		}
		else if (null != detail.getAdhocExemption()) {
			throw new CustomException(PT_ADHOC_REBATE_INVALID_AMOUNT, PT_ADHOC_REBATE_INVALID_AMOUNT_MSG + taxAmt);
		}
		return estimates;
	}

	/**
	 * Prepares Calculation Response based on the provided TaxHeadEstimate List
	 *
	 * All the credit taxHeads will be payable and all debit tax heads will be deducted.
	 *
	 * @param criteria criteria based on which calculation will be done.
	 * @param requestInfo request info from incoming request.
	 * @return Calculation object constructed based on the resulting tax amount and other applicables(rebate/penalty)
	 */
    private Calculation getCalculation(RequestInfo requestInfo, CalculationCriteria criteria,
									   Map<String,List> estimatesAndBillingSlabs) {

		List<TaxHeadEstimate> estimates = estimatesAndBillingSlabs.get("estimates");
		List<String> billingSlabIds = estimatesAndBillingSlabs.get("billingSlabIds");

        Property property = criteria.getProperty();
        PropertyDetail detail = property.getPropertyDetails().get(0);
        String assessmentYear = detail.getFinancialYear();
        String assessmentNumber = null != detail.getAssessmentNumber() ? detail.getAssessmentNumber() : criteria.getAssesmentNumber();
        String tenantId = null != property.getTenantId() ? property.getTenantId() : criteria.getTenantId();

		Map<String, Object> finYearMap = mDataService.getFinancialYear(requestInfo, assessmentYear, tenantId);
		Long fromDate = (Long) finYearMap.get(FINANCIAL_YEAR_STARTING_DATE);
		Long toDate = (Long) finYearMap.get(FINANCIAL_YEAR_ENDING_DATE);
		Map<String, Category> taxHeadCategoryMap = mDataService.getTaxHeadMasterMap(requestInfo, tenantId).stream()
				.collect(Collectors.toMap(TaxHeadMaster::getCode, TaxHeadMaster::getCategory));

		BigDecimal taxAmt = BigDecimal.ZERO;
		BigDecimal penalty = BigDecimal.ZERO;
		BigDecimal exemption = BigDecimal.ZERO;
		BigDecimal rebate = BigDecimal.ZERO;
		BigDecimal ptTax = BigDecimal.ZERO;

		for (TaxHeadEstimate estimate : estimates) {

			Category category = taxHeadCategoryMap.get(estimate.getTaxHeadCode());
			estimate.setCategory(category);

			switch (category) {

			case TAX:
				taxAmt = taxAmt.add(estimate.getEstimateAmount());
				if(estimate.getTaxHeadCode().equalsIgnoreCase(PT_TAX))
					ptTax = ptTax.add(estimate.getEstimateAmount());
				break;

			case PENALTY:
				penalty = penalty.add(estimate.getEstimateAmount());
				break;

			case REBATE:
				rebate = rebate.add(estimate.getEstimateAmount());
				break;

			case EXEMPTION:
				exemption = exemption.add(estimate.getEstimateAmount());
				break;

			default:
				taxAmt = taxAmt.add(estimate.getEstimateAmount());
				break;
			}
		}
		TaxHeadEstimate decimalEstimate = payService.roundOfDecimals(taxAmt.add(penalty), rebate.add(exemption));
        if (null != decimalEstimate) {

            estimates.add(decimalEstimate);
            if (PT_DECIMAL_CEILING_CREDIT.equalsIgnoreCase(decimalEstimate.getTaxHeadCode()))
                taxAmt = taxAmt.add(decimalEstimate.getEstimateAmount());
            else
                rebate = rebate.add(decimalEstimate.getEstimateAmount());
        }

		BigDecimal totalAmount = taxAmt.add(penalty).subtract(rebate).subtract(exemption);
		// false in the argument represents that the demand shouldn't be updated from this call
		BigDecimal collectedAmtForOldDemand = demandService.getCarryForwardAndCancelOldDemand(ptTax, criteria, requestInfo, false);

		if(collectedAmtForOldDemand.compareTo(BigDecimal.ZERO) > 0)
			estimates.add(TaxHeadEstimate.builder()
					.taxHeadCode(PT_ADVANCE_CARRYFORWARD)
					.estimateAmount(collectedAmtForOldDemand).build());
		else if(collectedAmtForOldDemand.compareTo(BigDecimal.ZERO) < 0)
			throw new CustomException(EG_PT_DEPRECIATING_ASSESSMENT_ERROR, EG_PT_DEPRECIATING_ASSESSMENT_ERROR_MSG_ESTIMATE);

		return Calculation.builder()
				.totalAmount(totalAmount.subtract(collectedAmtForOldDemand))
				.taxAmount(taxAmt)
				.penalty(penalty)
				.exemption(exemption)
				.rebate(rebate)
				.fromDate(fromDate)
				.toDate(toDate)
				.tenantId(tenantId)
				.serviceNumber(assessmentNumber)
				.taxHeadEstimates(estimates)
				.billingSlabIds(billingSlabIds)
				.build();
	}

	/**
	 * method to do a first level filtering on the slabs based on the values present in Property detail
	 */
	private List<BillingSlab> getSlabsFiltered(Property property, RequestInfo requestInfo) {

		PropertyDetail detail = property.getPropertyDetails().get(0);
		String tenantId = property.getTenantId();
		BillingSlabSearchCriteria slabSearchCriteria = BillingSlabSearchCriteria.builder().tenantId(tenantId).build();
		List<BillingSlab> billingSlabs = billingSlabService.searchBillingSlabs(requestInfo, slabSearchCriteria)
				.getBillingSlab();

		log.debug(" the slabs count : " + billingSlabs.size());
		final String all = configs.getSlabValueAll();

		Double plotSize = null != detail.getLandArea() ? detail.getLandArea() : detail.getBuildUpArea();

		final String dtlPtType = detail.getPropertyType();
		final String dtlPtSubType = detail.getPropertySubType();
		final String dtlOwnerShipCat = detail.getOwnershipCategory();
		final String dtlSubOwnerShipCat = detail.getSubOwnershipCategory();
		final String dtlAreaType = property.getAddress().getLocality().getArea();
		final Boolean dtlIsMultiFloored = detail.getNoOfFloors() > 1;

		return billingSlabs.stream().filter(slab -> {

			Boolean slabMultiFloored = slab.getIsPropertyMultiFloored();
			String  slabAreaType = slab.getAreaType();
			String  slabPropertyType = slab.getPropertyType();
			String  slabPropertySubType = slab.getPropertySubType();
			String  slabOwnerShipCat = slab.getOwnerShipCategory();
			String  slabSubOwnerShipCat = slab.getSubOwnerShipCategory();
			Double  slabAreaFrom = slab.getFromPlotSize();
			Double  slabAreaTo = slab.getToPlotSize();

			boolean isPropertyMultiFloored = slabMultiFloored.equals(dtlIsMultiFloored);

			boolean isAreaMatching = slabAreaType.equalsIgnoreCase(dtlAreaType) || all.equalsIgnoreCase(slab.getAreaType());

			boolean isPtTypeMatching = slabPropertyType.equalsIgnoreCase(dtlPtType);

			boolean isPtSubTypeMatching = slabPropertySubType.equalsIgnoreCase(dtlPtSubType)
					|| all.equalsIgnoreCase(slabPropertySubType);

			boolean isOwnerShipMatching = slabOwnerShipCat.equalsIgnoreCase(dtlOwnerShipCat)
					|| all.equalsIgnoreCase(slabOwnerShipCat);

			boolean isSubOwnerShipMatching = slabSubOwnerShipCat.equalsIgnoreCase(dtlSubOwnerShipCat)
					|| all.equalsIgnoreCase(slabSubOwnerShipCat);

			boolean isPlotMatching = false;

			if (plotSize == 0.0)
				isPlotMatching = slabAreaFrom <= plotSize && slabAreaTo >= plotSize;
			else
				isPlotMatching = slabAreaFrom < plotSize && slabAreaTo >= plotSize;

			return isPtTypeMatching && isPtSubTypeMatching && isOwnerShipMatching && isSubOwnerShipMatching
					&& isPlotMatching && isAreaMatching && isPropertyMultiFloored;

		}).collect(Collectors.toList());
	}

	/**
	 * Second level filtering to get the matching billing slab for the respective unit
	 * will return only one slab per unit.
	 *
	 * @param billingSlabs slabs filtered with property detail related values
	 * @param unit unit of the property for which the tax has be calculated
	 */
	private BillingSlab getSlabForCalc(List<BillingSlab> billingSlabs, Unit unit) {

		final String all = configs.getSlabValueAll();

		List<BillingSlab> matchingList = new ArrayList<>();

		for (BillingSlab billSlb : billingSlabs) {

			Double floorNo = Double.parseDouble(unit.getFloorNo());

			boolean isMajorMatching = billSlb.getUsageCategoryMajor().equalsIgnoreCase(unit.getUsageCategoryMajor())
					|| (billSlb.getUsageCategoryMajor().equalsIgnoreCase(all));

			boolean isMinorMatching = billSlb.getUsageCategoryMinor().equalsIgnoreCase(unit.getUsageCategoryMinor())
					|| (billSlb.getUsageCategoryMinor().equalsIgnoreCase(all));

			boolean isSubMinorMatching = billSlb.getUsageCategorySubMinor().equalsIgnoreCase(
					unit.getUsageCategorySubMinor()) || (billSlb.getUsageCategorySubMinor().equalsIgnoreCase(all));

			boolean isDetailsMatching = billSlb.getUsageCategoryDetail().equalsIgnoreCase(unit.getUsageCategoryDetail())
					|| (billSlb.getUsageCategoryDetail().equalsIgnoreCase(all));

			boolean isFloorMatching = billSlb.getFromFloor() <= floorNo && billSlb.getToFloor() >= floorNo;

			boolean isOccupancyTypeMatching = billSlb.getOccupancyType().equalsIgnoreCase(unit.getOccupancyType())
					|| (billSlb.getOccupancyType().equalsIgnoreCase(all));

			if (isMajorMatching && isMinorMatching && isSubMinorMatching && isDetailsMatching && isFloorMatching
					&& isOccupancyTypeMatching) {

				matchingList.add(billSlb);
				log.debug(" The Id of the matching slab : " + billSlb.getId());
			}
		}
		if (matchingList.size() == 1)
			return matchingList.get(0);
		else if (matchingList.size() == 0)
			return null;
		else throw new CustomException(PT_ESTIMATE_BILLINGSLABS_UNMATCH, PT_ESTIMATE_BILLINGSLABS_UNMATCH_MSG
					.replace(PT_ESTIMATE_BILLINGSLABS_UNMATCH_replace_id, matchingList.toString()) + unit);
	}

	/**
	 * Usage based exemptions applied on unit.
	 *
	 * The exemption discount will be applied based on the exemption rate of the
	 * usage master types.
	 */
	private BigDecimal getExemption(Unit unit, BigDecimal currentUnitTax, String financialYear,
			Map<String, Map<String, List<Object>>> propertyMasterMap) {

		Map<String, Object> exemption = getExemptionFromUsage(unit, financialYear, propertyMasterMap);
		return mDataService.calculateApplicables(currentUnitTax, exemption);
	}

	/**
	 * Applies discount on Total tax amount OwnerType based on exemptions.
	 */
	private BigDecimal getExemption(Set<OwnerInfo> owners, BigDecimal taxAmt, String financialYear,
			Map<String, Map<String, List<Object>>> propertyMasterMap) {

		Map<String, List<Object>> ownerTypeMap = propertyMasterMap.get(OWNER_TYPE_MASTER);
		BigDecimal userExemption = BigDecimal.ZERO;
		final int userCount = owners.size();
		BigDecimal share = taxAmt.divide(BigDecimal.valueOf(userCount),2, 2);

		for (OwnerInfo owner : owners) {

			if (null == ownerTypeMap.get(owner.getOwnerType()))
				continue;

			Map<String, Object> applicableOwnerType = mDataService.getApplicableMaster(financialYear,
					ownerTypeMap.get(owner.getOwnerType()));

			if (null != applicableOwnerType) {

				BigDecimal currentExemption = mDataService.calculateApplicables(share,
						applicableOwnerType.get(EXEMPTION_FIELD_NAME));

				userExemption = userExemption.add(currentExemption);
			}
		}
		return userExemption;
	}

	/**
	 * Returns the appropriate exemption object from the usage masters
	 *
	 * Search happens from child (usageCategoryDetail) to parent
	 * (usageCategoryMajor)
	 *
	 * if any appropriate match is found in getApplicableMasterFromList, then the
	 * exemption object from that master will be returned
	 *
	 * if no match found(for all the four usages) then null will be returned
	 *
	 * @param unit unit for which usage exemption will be applied
	 * @param financialYear year for which calculation is being done
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getExemptionFromUsage(Unit unit, String financialYear,
			Map<String, Map<String, List<Object>>> propertyBasedExemptionMasterMap) {

		Map<String, List<Object>> usageDetails = propertyBasedExemptionMasterMap.get(USAGE_DETAIL_MASTER);
		Map<String, List<Object>> usageSubMinors = propertyBasedExemptionMasterMap.get(USAGE_SUB_MINOR_MASTER);
		Map<String, List<Object>> usageMinors = propertyBasedExemptionMasterMap.get(USAGE_MINOR_MASTER);
		Map<String, List<Object>> usageMajors = propertyBasedExemptionMasterMap.get(USAGE_MAJOR_MASTER);

		Map<String, Object> applicableUsageMasterExemption = null;

		if (null != usageDetails.get(unit.getUsageCategoryDetail()))
			applicableUsageMasterExemption = mDataService.getApplicableMaster(financialYear,
					usageDetails.get(unit.getUsageCategoryDetail()));

		if (isExemptionNull(applicableUsageMasterExemption)
				&& null != usageSubMinors.get(unit.getUsageCategorySubMinor()))
			applicableUsageMasterExemption = mDataService.getApplicableMaster(financialYear,
					usageSubMinors.get(unit.getUsageCategorySubMinor()));

		if (isExemptionNull(applicableUsageMasterExemption) && null != usageMinors.get(unit.getUsageCategoryMinor()))
			applicableUsageMasterExemption = mDataService.getApplicableMaster(financialYear,
					usageMinors.get(unit.getUsageCategoryMinor()));

		if (isExemptionNull(applicableUsageMasterExemption) && null != usageMajors.get(unit.getUsageCategoryMajor()))
			applicableUsageMasterExemption = mDataService.getApplicableMaster(financialYear,
					usageMajors.get(unit.getUsageCategoryMajor()));

		if (null != applicableUsageMasterExemption)
			applicableUsageMasterExemption = (Map<String, Object>) applicableUsageMasterExemption.get(EXEMPTION_FIELD_NAME);

		return applicableUsageMasterExemption;
	}

	private boolean isExemptionNull(Map<String, Object> applicableUsageMasterExemption) {

		return !(null != applicableUsageMasterExemption
				&& null != applicableUsageMasterExemption.get(EXEMPTION_FIELD_NAME));
	}
}
