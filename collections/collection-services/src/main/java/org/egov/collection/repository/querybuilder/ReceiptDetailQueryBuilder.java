/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) 2016  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.collection.repository.querybuilder;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.egov.collection.model.Instrument;
import org.egov.collection.model.ReceiptSearchCriteria;
import org.egov.collection.repository.InstrumentRepository;
import org.egov.common.contract.request.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
@Component
public class ReceiptDetailQueryBuilder {

    @Autowired
    private InstrumentRepository instrumentRepository;

    public static final Logger logger = LoggerFactory
            .getLogger(ReceiptDetailQueryBuilder.class);

    private static final String RECEIPT_HEADER_QUERY = "Select rh.id as rh_id,rh.payeename as rh_payeename,"
            + "rh.payeeAddress as rh_payeeAddress,rh.payeeEmail as rh_payeeEmail,rh.paidBy as rh_paidBy,"
            + "rh.referenceNumber as rh_referenceNumber,rh.referenceDate as rh_referenceDate,"
            + "rh.receiptType as rh_receiptType,rh.receiptNumber as rh_receiptNumber,rh.receiptDate"
            + " as rh_receiptDate,rh.referenceDesc as rh_referenceDesc,rh.manualReceiptNumber"
            + " as rh_manualReceiptNumber, rh.businessDetails as rh_businessDetails,rh.collectionType"
            + " as rh_collectionType,rh.stateId as rh_stateId,rh.location as rh_location,"
            + "rh.isReconciled as rh_isReconciled,rh.status as rh_status,rh.reasonForCancellation"
            + " as rh_reasonForCancellation,rh.minimumAmount as rh_minimumAmount,rh.totalAmount"
            + " as rh_totalAmount,rh.collModesNotAllwd as rh_collModesNotAllwd,"
            + "rh.consumerCode as rh_consumerCode,rh.function as rh_function,"
            + "rh.version as rh_version,rh.channel as rh_channel,rh.reference_ch_id as rh_reference_ch_id,rh.consumerType"
            + " as rh_consumerType,rh.fund as rh_fund,rh.fundSource as rh_fundSource,"
            + "rh.boundary as rh_boundary,rh.department as rh_department,rh.depositedBranch"
            + " as rh_depositedBranch,rh.tenantId as rh_tenantId,rh.displayMsg as rh_displayMsg,"
            + "rh.voucherheader as rh_voucherheader,rh.cancellationRemarks as rh_cancellationRemarks,"
            + "rh.createdBy as rh_createdBy,rh.createdDate as rh_createdDate,"
            + "rh.lastModifiedBy as rh_lastModifiedBy,rh.lastModifiedDate as rh_lastModifiedDate,rh.transactionid as rh_transactionid "
            + " from egcl_receiptheader rh ";

    private static final String LEGACY_RECEIPT_HEADER_QUERY = "Select lrh.id as lrh_id,"
            + "lrh.legacyreceiptid as lrh_legacyreceiptid,lrh.receiptNo as lrh_receiptNo,"
            + "lrh.receiptDate as lrh_receiptDate,lrh.department as lrh_department,"
            + "lrh.serviceName as lrh_serviceName,lrh.consumerNo as lrh_consumerNo,"
            + "lrh.consumerName as lrh_consumerName,lrh.totalAmount as lrh_totalAmount,"
            + "lrh.advanceAmount as lrh_advanceAmount,lrh.adjustmentAmount as lrh_adjustmentAmount,"
            + "lrh.consumerAddress as lrh_consumerAddress,lrh.payeeName as lrh_payeeName,"
            + "lrh.instrumentType as lrh_instrumentType,lrh.instrumentDate as lrh_instrumentDate,"
            + "lrh.instrumentNo as lrh_instrumentNo,lrh.bankName as lrh_bankName,"
            + "lrh.manualreceiptnumber as lrh_manualreceiptnumber,lrh.manualreceiptDate as"
            + " lrh_manualreceiptDate,lrh.tenantid as lrh_tenantid,lrh.remarks as lrh_remarks"
            + " from egcl_legacy_receipt_header lrh";

    private static final String RECEIPT_DETAILS_QUERY = "select rd.id as rd_id,rd.receiptHeader as rh_id, " +
            " rd.dramount as rd_dramount,rd.cramount as rd_cramount,rd.actualcramountToBePaid as " +
            " rd_actualcramountToBePaid,rd.ordernumber as rd_ordernumber, " +
            " rd.description as rd_description,rd.chartOfAccount as rd_chartOfAccount,rd.isActualDemand " +
            " as rd_isActualDemand,rd.financialYear as rd_financialYear,rd.purpose as rd_purpose, " +
            " rd.tenantId as rd_tenantId from egcl_receiptdetails rd WHERE rd.tenantId = ? and rd.receiptheader = ?";

    private static final String UPDATE_QUERY = "Update egcl_receiptheader set";

    @SuppressWarnings("rawtypes")
    public String getQueryForUpdate(Long stateId, String status, Long id,
            String tenantId) {
        StringBuilder updateQuery = new StringBuilder(UPDATE_QUERY);
        addSetUpValues(stateId, status, updateQuery);
        addWhereClause(updateQuery, id, tenantId);
        return updateQuery.toString();
    }

    @SuppressWarnings("unchecked")
    private void addWhereClause(StringBuilder updateQuery, Long id,
            String tenantId) {
        updateQuery.append(" WHERE");
        Boolean isAppendAndClause = false;
        if (id != null) {
            isAppendAndClause = true;
            updateQuery.append(" id = ?");

        }
        if (tenantId != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    updateQuery);
            updateQuery.append(" tenantId = ?");

        }
    }

    private void addSetUpValues(Long stateId, String status,
            StringBuilder updateQuery) {
        Boolean isAppendCommmaSeparator = false;
        if (stateId != null) {
            isAppendCommmaSeparator = true;
            updateQuery.append(" stateId = ?");

        }
        if (status != null) {
            isAppendCommmaSeparator = addCommmaSeparatorIfRequired(
                    isAppendCommmaSeparator, updateQuery);
            updateQuery.append(" status = ?");

        }
        isAppendCommmaSeparator = addCommmaSeparatorIfRequired(
                isAppendCommmaSeparator, updateQuery);
        updateQuery.append(" lastModifiedBy = ?");

        isAppendCommmaSeparator = addCommmaSeparatorIfRequired(
                isAppendCommmaSeparator, updateQuery);
        updateQuery.append(" lastModifiedDate = ?");

    }

    private Boolean addCommmaSeparatorIfRequired(
            Boolean isAppendCommmaSeparator, StringBuilder updateQuery) {
        if (isAppendCommmaSeparator)
            updateQuery.append(" ,");
        return true;
    }

    @SuppressWarnings("rawtypes")
    public String getQuery(ReceiptSearchCriteria searchCriteria,
            List preparedStatementValues) throws ParseException {
        StringBuilder selectQuery = new StringBuilder(RECEIPT_HEADER_QUERY);

        addWhereClause(selectQuery, preparedStatementValues, searchCriteria);
        addOrderByClause(selectQuery, searchCriteria);

        logger.debug("RECEIPT HEADER Query : " + selectQuery);
        return selectQuery.toString();
    }

    public String getReceiptDetailByReceiptHeader() {
        StringBuilder selectQuery = new StringBuilder(RECEIPT_DETAILS_QUERY);
        logger.debug("RECEIPT DETAILS Query : " + selectQuery);
        return selectQuery.toString();
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
    private void addWhereClause(StringBuilder selectQuery,
            List preparedStatementValues, ReceiptSearchCriteria searchCriteria)
            throws ParseException {

        if (searchCriteria.getTenantId() == null)
            return;

        selectQuery.append(" WHERE");
        boolean isAppendAndClause = false;

        if (StringUtils.isNotBlank(searchCriteria.getTenantId())) {
            isAppendAndClause = true;
            selectQuery.append(" rh.tenantId = ?");
            preparedStatementValues.add(searchCriteria.getTenantId());

        }

        if (StringUtils.isNotBlank(searchCriteria.getPaymentType())) {
            List<Instrument> instruments = instrumentRepository.searchInstrumentsByPaymentMode(searchCriteria.getPaymentType(),
                    searchCriteria.getTenantId(), new RequestInfo());
            List<String> instrumentIds = instruments.stream()
                    .map(Instrument::getId).collect(Collectors.toList());
            if (!instrumentIds.isEmpty()) {
                isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                        selectQuery);
                selectQuery.append(" rh.id in(select receiptheader from egcl_receiptinstrument receiptinstrument " +
                        "where receiptinstrument.instrumentheader IN " + getInstrumentIdQuery(instrumentIds) + ")");
            }
        }

        if (searchCriteria.getReceiptNumbers() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    selectQuery);
            selectQuery.append(" rh.receiptNumber ilike any  "
                    + getNumberQuery(searchCriteria.getReceiptNumbers()));
        }

        if (StringUtils.isNotBlank(searchCriteria.getConsumerCode())) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    selectQuery);
            selectQuery.append(" rh.consumerCode = ?");
            preparedStatementValues.add(searchCriteria.getConsumerCode());
        }

        if (StringUtils.isNotBlank(searchCriteria.getStatus())) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    selectQuery);
            selectQuery.append(" rh.status = ?");
            preparedStatementValues.add(searchCriteria.getStatus());
        }

        if (StringUtils.isNotBlank(searchCriteria.getCollectedBy())) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    selectQuery);
            selectQuery.append(" rh.createdBy = ?");
            preparedStatementValues.add(new Long(searchCriteria
                    .getCollectedBy()));
        }

        if (searchCriteria.getFromDate() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    selectQuery);
            selectQuery.append(" rh.receiptDate >= ?");
            preparedStatementValues.add(searchCriteria.getFromDate());
        }

        if (searchCriteria.getToDate() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    selectQuery);
            selectQuery.append(" rh.receiptDate <= ?");
            if (searchCriteria.getToDate().equals(searchCriteria.getFromDate())) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date(searchCriteria.getToDate()));
                c.add(Calendar.DATE, 1);
                searchCriteria.setToDate(c.getTime().getTime());
            }
            preparedStatementValues.add(searchCriteria.getToDate());
        }

        if (StringUtils.isNotBlank(searchCriteria.getBusinessCode())) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    selectQuery);
            selectQuery.append(" rh.businessDetails = ?");
            preparedStatementValues.add(searchCriteria.getBusinessCode());
        }

        if (searchCriteria.getIds() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    selectQuery);
            selectQuery.append(" rh.id IN "
                    + getIdQuery(searchCriteria.getIds()));
        }

        if (StringUtils.isNotBlank(searchCriteria.getTransactionId())) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    selectQuery);
            selectQuery.append(" rh.transactionid = ? ");
            preparedStatementValues.add(searchCriteria.getTransactionId());
        }
    }

    private static String getIdQuery(List<Long> idList) {
        StringBuilder query = new StringBuilder("(");
        if (idList.size() >= 1) {
            query.append(idList.get(0).toString());
            for (int i = 1; i < idList.size(); i++) {
                query.append(", " + idList.get(i));
            }
        }
        return query.append(")").toString();
    }

    private static String getInstrumentIdQuery(List<String> instrumentIdList) {
        StringBuilder query = new StringBuilder("(");
        if (instrumentIdList.size() >= 1) {
            query.append("'" + instrumentIdList.get(0).toString() + "'");
            for (int i = 1; i < instrumentIdList.size(); i++) {
                query.append(", '" + instrumentIdList.get(i) + "'");
            }
        }
        return query.append(")").toString();
    }

    private void addOrderByClause(StringBuilder selectQuery,
            ReceiptSearchCriteria criteria) {
        String sortBy = (criteria.getSortBy() == null ? "rh.receiptDate"
                : "rh." + criteria.getSortBy());
        String sortOrder = (criteria.getSortOrder() == null ? "DESC" : criteria
                .getSortOrder());
        selectQuery.append(" ORDER BY " + sortBy + " " + sortOrder);
    }

    private boolean addAndClauseIfRequired(boolean appendAndClauseFlag,
            StringBuilder queryString) {
        if (appendAndClauseFlag)
            queryString.append(" AND");
        return true;
    }

    private static String getNumberQuery(List<String> receiptNumbersList) {
        StringBuilder query = new StringBuilder("(array [");

        if (receiptNumbersList.size() >= 1) {
            query.append("'%").append(receiptNumbersList.get(0).toString()).append("%'");
            for (int i = 1; i < receiptNumbersList.size(); i++) {
                query.append(", '%" + receiptNumbersList.get(i) + "%'");
            }
        }
        return query.append("])").toString();
    }

    public static String getNextSeqForRcptHeader() {
        return "select NEXTVAL('SEQ_EGCL_RECEIPTHEADER')";
    }

    public static String insertReceiptHeader() {
        logger.info("Returning insertReceiptHeaderQuery query to the repository");

        return "INSERT INTO egcl_receiptheader(id, payeename, payeeaddress, payeeemail, paidby, referencenumber, receipttype, "
                + "receiptnumber, receiptdate, businessdetails, collectiontype, reasonforcancellation, minimumamount, totalamount, "
                + "collmodesnotallwd, consumercode, channel, fund, fundsource, function, boundary, department, voucherheader, "
                + "depositedbranch, createdby, createddate, lastmodifiedby, lastmodifieddate, tenantid, referencedate, referencedesc, "
                + "manualreceiptnumber, reference_ch_id, stateid, location, isreconciled, "
                + "status, transactionid) "
                + "VALUES (:id, :payeename, :payeeaddress, :payeeemail, :paidby, :referencenumber, :receipttype, "
                + ":receiptnumber, :receiptdate, :businessdetails, :collectiontype, :reasonforcancellation, :minimumamount, :totalamount, "
                + ":collmodesnotallwd, :consumercode, :channel, :fund, :fundsource, :function, :boundary, :department, :voucherheader, "
                + ":depositedbranch, :createdby, :createddate, :lastmodifiedby, :lastmodifieddate, :tenantid, :referencedate, :referencedesc, "
                + ":manualreceiptnumber, :reference_ch_id, :stateid, :location, :isreconciled, "
                + ":status, :transactionid)";
    }

    public static String insertReceiptDetails() {
        logger.info("Returning insertReceiptDetailsQuery query to the repository");

        return "INSERT INTO egcl_receiptdetails(id, chartofaccount, dramount, cramount, ordernumber, receiptheader, actualcramounttobepaid, "
                + "description, financialyear, isactualdemand, purpose, tenantid) "
                + "VALUES (NEXTVAL('SEQ_EGCL_RECEIPTDETAILS'), :chartofaccount, :dramount, :cramount, :ordernumber, :receiptheader, :actualcramounttobepaid, "
                + ":description, :financialyear, :isactualdemand, :purpose, :tenantid)";
    }

    // TODO: Vishal: Remove this unused method and do the necessary changes in the test cases
    public static String getreceiptHeaderId() {
        logger.info("Returning getreceiptHeaderId query to the repository");

        return "SELECT MAX(id) FROM egcl_receiptheader WHERE payeename = ? AND paidby = ? AND createddate = ?";
    }

    public String getCancelQuery() {

        return "Update egcl_receiptheader set status=? , reasonforcancellation=? , cancellationremarks=? ,"
                + " lastmodifiedby=? , lastmodifieddate=?"
                + " where id =? and tenantId=?";
    }

    public String searchQuery() {
        return "select distinct createdby from egcl_receiptheader where tenantId = ?";
    }

    public String searchStatusQuery() {
        return "select distinct status from egcl_receiptheader where tenantId = ? order by status ASC";
    }

    public String searchBusinessDetailsQuery() {
        return "select distinct(trim(businessdetails,'&nbsp')) from egcl_receiptheader where tenantId = ?";
    }

    public String searchChartOfAccountsQuery() {
        return "select distinct chartofaccount from egcl_receiptdetails where tenantId = ? and purpose != 'OTHERS' and description != '' ";
    }

    public String insertInstrumentId() {
        return "insert into egcl_receiptinstrument(instrumentheader, receiptheader, tenantId) values (?,?,?)";
    }

    public String searchReceiptInstrument() {
        return "select instrumentheader from egcl_receiptinstrument where receiptheader = ? and tenantId = ? ";
    }

    public static String insertOnlinePayments() {
        logger.info("Returning Online payment query to the repository");

        return "INSERT INTO egcl_onlinepayments(id, receiptheader, paymentgatewayname, transactionnumber, transactionamount, transactiondate, authorisation_statuscode, "
                + " status, remarks, createdby, lastmodifiedby, createddate, lastmodifieddate, tenantId) "
                + "VALUES (NEXTVAL('seq_egcl_onlinepayments'), :receiptheader, :paymentgatewayname, :transactionnumber, :transactionamount, :transactiondate, :authorisation_statuscode, "
                + ":status, :remarks, :createdby, :lastmodifiedby, :createddate, :lastmodifieddate, :tenantId)";
    }

    public String getLegacyReceiptSearchQuery(ReceiptSearchCriteria receiptSearchCriteria, List<Object> preparedStatementValues) {
        StringBuilder legacySelectQuery = new StringBuilder(LEGACY_RECEIPT_HEADER_QUERY);
        addWhereClauseForLegacy(receiptSearchCriteria, legacySelectQuery, preparedStatementValues);
        addOrderByClauseForLegacy(receiptSearchCriteria,legacySelectQuery);
        addLimitForLegacy(receiptSearchCriteria,legacySelectQuery);
        return legacySelectQuery.toString();
    }

    private void addLimitForLegacy(ReceiptSearchCriteria receiptSearchCriteria, StringBuilder legacySelectQuery) {
       if(receiptSearchCriteria.getLimit()!=null){
           legacySelectQuery.append(" limit " + receiptSearchCriteria.getLimit());
       }
    }

    private void addOrderByClauseForLegacy(ReceiptSearchCriteria receiptSearchCriteria, StringBuilder legacySelectQuery) {
        String sortBy = (receiptSearchCriteria.getSortBy() == null ? "lrh.receiptDate"
                : "lrh." + receiptSearchCriteria.getSortBy());
        String sortOrder = (receiptSearchCriteria.getSortOrder() == null ? "DESC" : receiptSearchCriteria
                .getSortOrder());
        legacySelectQuery.append(" ORDER BY " + sortBy + " " + sortOrder);         
    }

    private void addWhereClauseForLegacy(ReceiptSearchCriteria receiptSearchCriteria, StringBuilder legacySelectQuery,
            List<Object> preparedStatementValues) {
        if (receiptSearchCriteria.getTenantId() == null)
            return;
        legacySelectQuery.append(" WHERE");
        Boolean isAppendAndClause = false;
        if (receiptSearchCriteria.getTenantId() != null) {
            isAppendAndClause = true;
            legacySelectQuery.append(" lrh.tenantid = ?");
            preparedStatementValues.add(receiptSearchCriteria.getTenantId());
        }
        if (receiptSearchCriteria.getReceiptNumbers() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    legacySelectQuery);
            legacySelectQuery.append(" lrh.receiptNo ilike any  "
                    + getNumberQuery(receiptSearchCriteria.getReceiptNumbers()));
        }
        if (receiptSearchCriteria.getConsumerNo() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    legacySelectQuery);
            legacySelectQuery.append(" lrh.consumerNo = ?");
            preparedStatementValues.add(receiptSearchCriteria.getConsumerNo());
        }
        if (receiptSearchCriteria.getServiceName() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    legacySelectQuery);
            legacySelectQuery.append(" lrh.serviceName = ?");
            preparedStatementValues.add(receiptSearchCriteria.getServiceName());
        }
        if (receiptSearchCriteria.getFromDate() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    legacySelectQuery);
            legacySelectQuery.append(" lrh.receiptDate >= ?");
            preparedStatementValues.add(receiptSearchCriteria.getFromDate());
        }
        if (receiptSearchCriteria.getToDate() != null) {
            isAppendAndClause = addAndClauseIfRequired(isAppendAndClause,
                    legacySelectQuery);
            legacySelectQuery.append(" lrh.receiptDate <= ?");
            if (receiptSearchCriteria.getToDate().equals(receiptSearchCriteria.getFromDate())) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date(receiptSearchCriteria.getToDate()));
                c.add(Calendar.DATE, 1);
                receiptSearchCriteria.setToDate(c.getTime().getTime());
            }
            preparedStatementValues.add(receiptSearchCriteria.getToDate());
        }
    }

    public String getLegacyReceiptDetailByLegacyReceiptHeader() {
        return "Select lrd.id as lrd_id,lrd.billNo as lrd_billNo,lrd.billId as lrd_billId,lrd.billYear as lrd_billYear,"
                + "lrd.taxId as lrd_taxId,lrd.billDate as lrd_billDate,lrd.description as lrd_description,lrd.currDemand as lrd_currDemand,"
                + "lrd.arrDemand as lrd_arrDemand,lrd.currCollection as lrd_currCollection,lrd.arrCollection as lrd_arrCollection,"
                + "lrd.currBalance as lrd_currBalance,lrd.arrBalance as lrd_arrBalance,lrd.id_receipt_header as lrd_id_receipt_header,"
                + "lrd.tenantid as lrd_tenantid from egcl_legacy_receipt_details lrd where lrd.tenantid =? and lrd.id_receipt_header =?";

    }
}