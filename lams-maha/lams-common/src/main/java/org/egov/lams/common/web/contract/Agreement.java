package org.egov.lams.common.web.contract;

/*
 * eGov LAMS (Leases And Agreements System) APIs
 * eGov LAMS manages the Lease data for immovable assets owned by a ULB. This module aim is to improve lease operations in ULB. This will gives a comprehensive details of agreements for immovable assets like Land, Shopping Complex, Market, Kalyanamandapam, etc.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: info@egovernments.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */



import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Object holds the data for agreement.
 */
@ApiModel(description = "A Object holds the data for agreement.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-11-01T12:22:22.713Z")
public class Agreement {
  @SerializedName("id")
  private String id = null;

  @SerializedName("tenantId")
  private String tenantId = null;
  
  private String workflowStatus=null;

  public String getWorkflowStatus() {
	return workflowStatus;
}

public void setWorkflowStatus(String workflowStatus) {
	this.workflowStatus = workflowStatus;
}

/**
   * source from which agreement is being persisted.
   */
  @JsonAdapter(SourceEnum.Adapter.class)
  public enum SourceEnum {
    SYSTEM("SYSTEM"),
    
    DATE_ENTRY("DATE_ENTRY"),
    
    MIGRATION("MIGRATION"),
    
    CFC("CFC");

    private String value;

    SourceEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static SourceEnum fromValue(String text) {
      for (SourceEnum b : SourceEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<SourceEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final SourceEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public SourceEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return SourceEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("source")
  private SourceEnum source = null;

  /**
   * enumeration of agreement statuses.
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    ACTIVE("ACTIVE"),
    
    CANCELED("CANCELED"),
    
    EVICTED("EVICTED"),
    
    RENEWED("RENEWED"),
    
    SUSPENDED("SUSPENDED"),
    
    WORKFLOW("WORKFLOW");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return StatusEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("status")
  private StatusEnum status = null;

  @SerializedName("isActive")
  private Boolean isActive = true;

  @SerializedName("parent")
  private Agreement parent = null;

  /**
   * enumeration of action on agreement.
   */
  @JsonAdapter(ActionEnum.Adapter.class)
  public enum ActionEnum {
    CREATE("Create"),
    
    RENEWAL("Renewal");

    private String value;

    ActionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ActionEnum fromValue(String text) {
      for (ActionEnum b : ActionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<ActionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ActionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ActionEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return ActionEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("action")
  private ActionEnum action = null;

  /**
   * The agreements done only on land or estate, this will give the agreement is happening on land or estate.
   */
  @JsonAdapter(LeaseTypeEnum.Adapter.class)
  public enum LeaseTypeEnum {
    LAND("LAND"),
    
    ESTATE("ESTATE");

    private String value;

    LeaseTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static LeaseTypeEnum fromValue(String text) {
      for (LeaseTypeEnum b : LeaseTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<LeaseTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final LeaseTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public LeaseTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return LeaseTypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("leaseType")
  private LeaseTypeEnum leaseType = null;

  @SerializedName("oldAgreementNumber")
  private String oldAgreementNumber = null;

  @SerializedName("agreementNumber")
  private String agreementNumber = null;

  @SerializedName("landEstateNo")
  private String landEstateNo = null;

  @SerializedName("allottee")
  private User allottee = null;

  @SerializedName("registrationNo")
  private String registrationNo = null;

  @SerializedName("registrationDate")
  private Long registrationDate = null;

  @SerializedName("registerPageNo")
  private String registerPageNo = null;

  @SerializedName("depositAmount")
  private Double depositAmount = null;

  @SerializedName("electricityBillAmount")
  private Double electricityBillAmount = null;

  @SerializedName("propertyOutstanding")
  private Double propertyOutstanding = null;

  @SerializedName("propertyTax")
  private Double propertyTax = null;

  @SerializedName("penalty")
  private Double penalty = null;

  @SerializedName("rent")
  private Double rent = null;

  @SerializedName("budgetHead")
  private String budgetHead = null;

  @SerializedName("currentElectricityBillAmount")
  private Double currentElectricityBillAmount = null;

  @SerializedName("rentIncrement")
  private Double rentIncrement = null;

  @SerializedName("currentPenalty")
  private Double currentPenalty = null;

  @SerializedName("agreementFromDate")
  private Long agreementFromDate = null;

  @SerializedName("agreementToDate")
  private Long agreementToDate = null;

  @SerializedName("holdingType")
  private HoldingType holdingType = null;

  @SerializedName("GBResolutionNo")
  private String gbResolutionNo = null;

  /**
   * payment cycle on which the rent has to be paid against agreement.
   */
  @JsonAdapter(PaymentCycleEnum.Adapter.class)
  public enum PaymentCycleEnum {
    MONTH("MONTH"),
    
    QUARTER("QUARTER"),
    
    HALFYEAR("HALFYEAR"),
    
    ANNUAL("ANNUAL");

    private String value;

    PaymentCycleEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static PaymentCycleEnum fromValue(String text) {
      for (PaymentCycleEnum b : PaymentCycleEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<PaymentCycleEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final PaymentCycleEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public PaymentCycleEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return PaymentCycleEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("paymentCycle")
  private PaymentCycleEnum paymentCycle = null;

  @SerializedName("remarks")
  private String remarks = null;

  @SerializedName("applicationNo")
  private String applicationNo = null;

  @SerializedName("documents")
  private List<AgreementDocs> documents = null;

  @SerializedName("demands")
  private List<Demand> demands = null;

  @SerializedName("cancelAgreementNumber")
  private String cancelAgreementNumber = null;

  @SerializedName("evictionAgreementNumber")
  private String evictionAgreementNumber = null;

  @SerializedName("renewalAgreementNumber")
  private String renewalAgreementNumber = null;

  @SerializedName("section")
  private String section = null;

  @SerializedName("actName")
  private String actName = null;

  @SerializedName("auctionDate")
  private Long auctionDate = null;

  @SerializedName("evictionReason")
  private String evictionReason = null;

  @SerializedName("stateId")
  private String stateId = null;

  @SerializedName("workFlowDetails")
  private WorkFlowDetails workFlowDetails = null;

  @SerializedName("auditDetails")
  private AuditDetails auditDetails = null;

  public Agreement id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique Identifier of the agreement.
   * @return id
  **/
  @ApiModelProperty(required = true, value = "Unique Identifier of the agreement.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Agreement tenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

   /**
   * Unique Identifier of which tenant this data is defined. Eg. mh.mumbai.
   * @return tenantId
  **/
  @ApiModelProperty(required = true, value = "Unique Identifier of which tenant this data is defined. Eg. mh.mumbai.")
  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public Agreement source(SourceEnum source) {
    this.source = source;
    return this;
  }

   /**
   * source from which agreement is being persisted.
   * @return source
  **/
  @ApiModelProperty(required = true, value = "source from which agreement is being persisted.")
  public SourceEnum getSource() {
    return source;
  }

  public void setSource(SourceEnum source) {
    this.source = source;
  }

  public Agreement status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * enumeration of agreement statuses.
   * @return status
  **/
  @ApiModelProperty(required = true, value = "enumeration of agreement statuses.")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Agreement isActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

   /**
   * Active flag for agreement
   * @return isActive
  **/
  @ApiModelProperty(value = "Active flag for agreement")
  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public Agreement parent(Agreement parent) {
    this.parent = parent;
    return this;
  }

   /**
   * Get parent
   * @return parent
  **/
  @ApiModelProperty(value = "")
  public Agreement getParent() {
    return parent;
  }

  public void setParent(Agreement parent) {
    this.parent = parent;
  }

  public Agreement action(ActionEnum action) {
    this.action = action;
    return this;
  }

   /**
   * enumeration of action on agreement.
   * @return action
  **/
  @ApiModelProperty(value = "enumeration of action on agreement.")
  public ActionEnum getAction() {
    return action;
  }

  public void setAction(ActionEnum action) {
    this.action = action;
  }

  public Agreement leaseType(LeaseTypeEnum leaseType) {
    this.leaseType = leaseType;
    return this;
  }

   /**
   * The agreements done only on land or estate, this will give the agreement is happening on land or estate.
   * @return leaseType
  **/
  @ApiModelProperty(required = true, value = "The agreements done only on land or estate, this will give the agreement is happening on land or estate.")
  public LeaseTypeEnum getLeaseType() {
    return leaseType;
  }

  public void setLeaseType(LeaseTypeEnum leaseType) {
    this.leaseType = leaseType;
  }

  public Agreement oldAgreementNumber(String oldAgreementNumber) {
    this.oldAgreementNumber = oldAgreementNumber;
    return this;
  }

   /**
   * Old agreement ref no, this is mandatory when source is DATA_ENTRY or MIGRATION
   * @return oldAgreementNumber
  **/
  @ApiModelProperty(value = "Old agreement ref no, this is mandatory when source is DATA_ENTRY or MIGRATION")
  public String getOldAgreementNumber() {
    return oldAgreementNumber;
  }

  public void setOldAgreementNumber(String oldAgreementNumber) {
    this.oldAgreementNumber = oldAgreementNumber;
  }

  public Agreement agreementNumber(String agreementNumber) {
    this.agreementNumber = agreementNumber;
    return this;
  }

   /**
   * unique agreement number.
   * @return agreementNumber
  **/
  @ApiModelProperty(value = "unique agreement number.")
  public String getAgreementNumber() {
    return agreementNumber;
  }

  public void setAgreementNumber(String agreementNumber) {
    this.agreementNumber = agreementNumber;
  }

  public Agreement landEstateNo(String landEstateNo) {
    this.landEstateNo = landEstateNo;
    return this;
  }

   /**
   * Land or Estate registration no for which the agreement being done.
   * @return landEstateNo
  **/
  @ApiModelProperty(required = true, value = "Land or Estate registration no for which the agreement being done.")
  public String getLandEstateNo() {
    return landEstateNo;
  }

  public void setLandEstateNo(String landEstateNo) {
    this.landEstateNo = landEstateNo;
  }

  public Agreement allottee(User allottee) {
    this.allottee = allottee;
    return this;
  }

   /**
   * Get allottee
   * @return allottee
  **/
  @ApiModelProperty(required = true, value = "")
  public User getAllottee() {
    return allottee;
  }

  public void setAllottee(User allottee) {
    this.allottee = allottee;
  }

  public Agreement registrationNo(String registrationNo) {
    this.registrationNo = registrationNo;
    return this;
  }

   /**
   * Registration no for agreement.
   * @return registrationNo
  **/
  @ApiModelProperty(required = true, value = "Registration no for agreement.")
  public String getRegistrationNo() {
    return registrationNo;
  }

  public void setRegistrationNo(String registrationNo) {
    this.registrationNo = registrationNo;
  }

  public Agreement registrationDate(Long registrationDate) {
    this.registrationDate = registrationDate;
    return this;
  }

   /**
   * Registration date for agreement. Date is inclduing timestamp, date in epoch
   * @return registrationDate
  **/
  @ApiModelProperty(required = true, value = "Registration date for agreement. Date is inclduing timestamp, date in epoch")
  public Long getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Long registrationDate) {
    this.registrationDate = registrationDate;
  }

  public Agreement registerPageNo(String registerPageNo) {
    this.registerPageNo = registerPageNo;
    return this;
  }

   /**
   * Registar page no for agreement.
   * @return registerPageNo
  **/
  @ApiModelProperty(value = "Registar page no for agreement.")
  public String getRegisterPageNo() {
    return registerPageNo;
  }

  public void setRegisterPageNo(String registerPageNo) {
    this.registerPageNo = registerPageNo;
  }

  public Agreement depositAmount(Double depositAmount) {
    this.depositAmount = depositAmount;
    return this;
  }

   /**
   * Deposit amount.
   * @return depositAmount
  **/
  @ApiModelProperty(required = true, value = "Deposit amount.")
  public Double getDepositAmount() {
    return depositAmount;
  }

  public void setDepositAmount(Double depositAmount) {
    this.depositAmount = depositAmount;
  }

  public Agreement electricityBillAmount(Double electricityBillAmount) {
    this.electricityBillAmount = electricityBillAmount;
    return this;
  }

   /**
   * Electricity Bill Amount.
   * @return electricityBillAmount
  **/
  @ApiModelProperty(value = "Electricity Bill Amount.")
  public Double getElectricityBillAmount() {
    return electricityBillAmount;
  }

  public void setElectricityBillAmount(Double electricityBillAmount) {
    this.electricityBillAmount = electricityBillAmount;
  }

  public Agreement propertyOutstanding(Double propertyOutstanding) {
    this.propertyOutstanding = propertyOutstanding;
    return this;
  }

   /**
   * Property Outstanding.
   * @return propertyOutstanding
  **/
  @ApiModelProperty(value = "Property Outstanding.")
  public Double getPropertyOutstanding() {
    return propertyOutstanding;
  }

  public void setPropertyOutstanding(Double propertyOutstanding) {
    this.propertyOutstanding = propertyOutstanding;
  }

  public Agreement propertyTax(Double propertyTax) {
    this.propertyTax = propertyTax;
    return this;
  }

   /**
   * Property Tax.
   * @return propertyTax
  **/
  @ApiModelProperty(value = "Property Tax.")
  public Double getPropertyTax() {
    return propertyTax;
  }

  public void setPropertyTax(Double propertyTax) {
    this.propertyTax = propertyTax;
  }

  public Agreement penalty(Double penalty) {
    this.penalty = penalty;
    return this;
  }

   /**
   * penalty.
   * @return penalty
  **/
  @ApiModelProperty(value = "penalty.")
  public Double getPenalty() {
    return penalty;
  }

  public void setPenalty(Double penalty) {
    this.penalty = penalty;
  }

  public Agreement rent(Double rent) {
    this.rent = rent;
    return this;
  }

   /**
   * rent to be paid per payment cycle.
   * @return rent
  **/
  @ApiModelProperty(required = true, value = "rent to be paid per payment cycle.")
  public Double getRent() {
    return rent;
  }

  public void setRent(Double rent) {
    this.rent = rent;
  }

  public Agreement budgetHead(String budgetHead) {
    this.budgetHead = budgetHead;
    return this;
  }

   /**
   * Bugdet Head for agreement.
   * @return budgetHead
  **/
  @ApiModelProperty(value = "Bugdet Head for agreement.")
  public String getBudgetHead() {
    return budgetHead;
  }

  public void setBudgetHead(String budgetHead) {
    this.budgetHead = budgetHead;
  }

  public Agreement currentElectricityBillAmount(Double currentElectricityBillAmount) {
    this.currentElectricityBillAmount = currentElectricityBillAmount;
    return this;
  }

   /**
   * Current Electricity Bill Amount.
   * @return currentElectricityBillAmount
  **/
  @ApiModelProperty(value = "Current Electricity Bill Amount.")
  public Double getCurrentElectricityBillAmount() {
    return currentElectricityBillAmount;
  }

  public void setCurrentElectricityBillAmount(Double currentElectricityBillAmount) {
    this.currentElectricityBillAmount = currentElectricityBillAmount;
  }

  public Agreement rentIncrement(Double rentIncrement) {
    this.rentIncrement = rentIncrement;
    return this;
  }

   /**
   * rent increment amount.
   * @return rentIncrement
  **/
  @ApiModelProperty(required = true, value = "rent increment amount.")
  public Double getRentIncrement() {
    return rentIncrement;
  }

  public void setRentIncrement(Double rentIncrement) {
    this.rentIncrement = rentIncrement;
  }

  public Agreement currentPenalty(Double currentPenalty) {
    this.currentPenalty = currentPenalty;
    return this;
  }

   /**
   * Current Penalty.
   * @return currentPenalty
  **/
  @ApiModelProperty(value = "Current Penalty.")
  public Double getCurrentPenalty() {
    return currentPenalty;
  }

  public void setCurrentPenalty(Double currentPenalty) {
    this.currentPenalty = currentPenalty;
  }

  public Agreement agreementFromDate(Long agreementFromDate) {
    this.agreementFromDate = agreementFromDate;
    return this;
  }

   /**
   * Agreement start date. Date is inclduing timestamp, Date in epoch
   * @return agreementFromDate
  **/
  @ApiModelProperty(required = true, value = "Agreement start date. Date is inclduing timestamp, Date in epoch")
  public Long getAgreementFromDate() {
    return agreementFromDate;
  }

  public void setAgreementFromDate(Long agreementFromDate) {
    this.agreementFromDate = agreementFromDate;
  }

  public Agreement agreementToDate(Long agreementToDate) {
    this.agreementToDate = agreementToDate;
    return this;
  }

   /**
   * End date of agreement. Date is inclduing timestamp, date in epoch
   * @return agreementToDate
  **/
  @ApiModelProperty(required = true, value = "End date of agreement. Date is inclduing timestamp, date in epoch")
  public Long getAgreementToDate() {
    return agreementToDate;
  }

  public void setAgreementToDate(Long agreementToDate) {
    this.agreementToDate = agreementToDate;
  }

  public Agreement holdingType(HoldingType holdingType) {
    this.holdingType = holdingType;
    return this;
  }

   /**
   * code of the master stored here as a ref.
   * @return holdingType
  **/
  @ApiModelProperty(value = "code of the master stored here as a ref.")
  public HoldingType getHoldingType() {
    return holdingType;
  }

  public void setHoldingType(HoldingType holdingType) {
    this.holdingType = holdingType;
  }

  public Agreement gbResolutionNo(String gbResolutionNo) {
    this.gbResolutionNo = gbResolutionNo;
    return this;
  }

   /**
   * GB resolution number.
   * @return gbResolutionNo
  **/
  @ApiModelProperty(value = "GB resolution number.")
  public String getGbResolutionNo() {
    return gbResolutionNo;
  }

  public void setGbResolutionNo(String gbResolutionNo) {
    this.gbResolutionNo = gbResolutionNo;
  }

  public Agreement paymentCycle(PaymentCycleEnum paymentCycle) {
    this.paymentCycle = paymentCycle;
    return this;
  }

   /**
   * payment cycle on which the rent has to be paid against agreement.
   * @return paymentCycle
  **/
  @ApiModelProperty(required = true, value = "payment cycle on which the rent has to be paid against agreement.")
  public PaymentCycleEnum getPaymentCycle() {
    return paymentCycle;
  }

  public void setPaymentCycle(PaymentCycleEnum paymentCycle) {
    this.paymentCycle = paymentCycle;
  }

  public Agreement remarks(String remarks) {
    this.remarks = remarks;
    return this;
  }

   /**
   * Comments.
   * @return remarks
  **/
  @ApiModelProperty(value = "Comments.")
  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public Agreement applicationNo(String applicationNo) {
    this.applicationNo = applicationNo;
    return this;
  }

   /**
   * Acknowldgement number given to allottee on submitting the application.
   * @return applicationNo
  **/
  @ApiModelProperty(value = "Acknowldgement number given to allottee on submitting the application.")
  public String getApplicationNo() {
    return applicationNo;
  }

  public void setApplicationNo(String applicationNo) {
    this.applicationNo = applicationNo;
  }

  public Agreement documents(List<AgreementDocs> documents) {
    this.documents = documents;
    return this;
  }

  public Agreement addDocumentsItem(AgreementDocs documentsItem) {
    if (this.documents == null) {
      this.documents = new ArrayList<AgreementDocs>();
    }
    this.documents.add(documentsItem);
    return this;
  }

   /**
   * Get documents
   * @return documents
  **/
  @ApiModelProperty(value = "")
  public List<AgreementDocs> getDocuments() {
    return documents;
  }

  public void setDocuments(List<AgreementDocs> documents) {
    this.documents = documents;
  }

  public Agreement demands(List<Demand> demands) {
    this.demands = demands;
    return this;
  }

  public Agreement addDemandsItem(Demand demandsItem) {
    if (this.demands == null) {
      this.demands = new ArrayList<Demand>();
    }
    this.demands.add(demandsItem);
    return this;
  }

   /**
   * Get demands
   * @return demands
  **/
  @ApiModelProperty(value = "")
  public List<Demand> getDemands() {
    return demands;
  }

  public void setDemands(List<Demand> demands) {
    this.demands = demands;
  }

  public Agreement cancelAgreementNumber(String cancelAgreementNumber) {
    this.cancelAgreementNumber = cancelAgreementNumber;
    return this;
  }

   /**
   * agreement number generated at the time of cancelation.
   * @return cancelAgreementNumber
  **/
  @ApiModelProperty(value = "agreement number generated at the time of cancelation.")
  public String getCancelAgreementNumber() {
    return cancelAgreementNumber;
  }

  public void setCancelAgreementNumber(String cancelAgreementNumber) {
    this.cancelAgreementNumber = cancelAgreementNumber;
  }

  public Agreement evictionAgreementNumber(String evictionAgreementNumber) {
    this.evictionAgreementNumber = evictionAgreementNumber;
    return this;
  }

   /**
   * agreement number generated at the time of eviction.
   * @return evictionAgreementNumber
  **/
  @ApiModelProperty(value = "agreement number generated at the time of eviction.")
  public String getEvictionAgreementNumber() {
    return evictionAgreementNumber;
  }

  public void setEvictionAgreementNumber(String evictionAgreementNumber) {
    this.evictionAgreementNumber = evictionAgreementNumber;
  }

  public Agreement renewalAgreementNumber(String renewalAgreementNumber) {
    this.renewalAgreementNumber = renewalAgreementNumber;
    return this;
  }

   /**
   * agreement number generated at the time of renewal.
   * @return renewalAgreementNumber
  **/
  @ApiModelProperty(value = "agreement number generated at the time of renewal.")
  public String getRenewalAgreementNumber() {
    return renewalAgreementNumber;
  }

  public void setRenewalAgreementNumber(String renewalAgreementNumber) {
    this.renewalAgreementNumber = renewalAgreementNumber;
  }

  public Agreement section(String section) {
    this.section = section;
    return this;
  }

   /**
   * section at the time of eviction.
   * @return section
  **/
  @ApiModelProperty(value = "section at the time of eviction.")
  public String getSection() {
    return section;
  }

  public void setSection(String section) {
    this.section = section;
  }

  public Agreement actName(String actName) {
    this.actName = actName;
    return this;
  }

   /**
   * Act Name at the time of eviction.
   * @return actName
  **/
  @ApiModelProperty(value = "Act Name at the time of eviction.")
  public String getActName() {
    return actName;
  }

  public void setActName(String actName) {
    this.actName = actName;
  }

  public Agreement auctionDate(Long auctionDate) {
    this.auctionDate = auctionDate;
    return this;
  }

   /**
   * Auction date for eviction, Date is inclduing timestamp,  date in epoch
   * @return auctionDate
  **/
  @ApiModelProperty(value = "Auction date for eviction, Date is inclduing timestamp,  date in epoch")
  public Long getAuctionDate() {
    return auctionDate;
  }

  public void setAuctionDate(Long auctionDate) {
    this.auctionDate = auctionDate;
  }

  public Agreement evictionReason(String evictionReason) {
    this.evictionReason = evictionReason;
    return this;
  }

   /**
   * Reason for Eviction.
   * @return evictionReason
  **/
  @ApiModelProperty(value = "Reason for Eviction.")
  public String getEvictionReason() {
    return evictionReason;
  }

  public void setEvictionReason(String evictionReason) {
    this.evictionReason = evictionReason;
  }

  public Agreement stateId(String stateId) {
    this.stateId = stateId;
    return this;
  }

   /**
   * Work flow ref id.
   * @return stateId
  **/
  @ApiModelProperty(value = "Work flow ref id.")
  public String getStateId() {
    return stateId;
  }

  public void setStateId(String stateId) {
    this.stateId = stateId;
  }

  public Agreement workFlowDetails(WorkFlowDetails workFlowDetails) {
    this.workFlowDetails = workFlowDetails;
    return this;
  }

   /**
   * Get workFlowDetails
   * @return workFlowDetails
  **/
  @ApiModelProperty(value = "")
  public WorkFlowDetails getWorkFlowDetails() {
    return workFlowDetails;
  }

  public void setWorkFlowDetails(WorkFlowDetails workFlowDetails) {
    this.workFlowDetails = workFlowDetails;
  }

  public Agreement auditDetails(AuditDetails auditDetails) {
    this.auditDetails = auditDetails;
    return this;
  }

   /**
   * Get auditDetails
   * @return auditDetails
  **/
  @ApiModelProperty(value = "")
  public AuditDetails getAuditDetails() {
    return auditDetails;
  }

  public void setAuditDetails(AuditDetails auditDetails) {
    this.auditDetails = auditDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Agreement agreement = (Agreement) o;
    return Objects.equals(this.id, agreement.id) &&
        Objects.equals(this.tenantId, agreement.tenantId) &&
        Objects.equals(this.source, agreement.source) &&
        Objects.equals(this.status, agreement.status) &&
        Objects.equals(this.isActive, agreement.isActive) &&
        Objects.equals(this.parent, agreement.parent) &&
        Objects.equals(this.action, agreement.action) &&
        Objects.equals(this.leaseType, agreement.leaseType) &&
        Objects.equals(this.oldAgreementNumber, agreement.oldAgreementNumber) &&
        Objects.equals(this.agreementNumber, agreement.agreementNumber) &&
        Objects.equals(this.landEstateNo, agreement.landEstateNo) &&
        Objects.equals(this.allottee, agreement.allottee) &&
        Objects.equals(this.registrationNo, agreement.registrationNo) &&
        Objects.equals(this.registrationDate, agreement.registrationDate) &&
        Objects.equals(this.registerPageNo, agreement.registerPageNo) &&
        Objects.equals(this.depositAmount, agreement.depositAmount) &&
        Objects.equals(this.electricityBillAmount, agreement.electricityBillAmount) &&
        Objects.equals(this.propertyOutstanding, agreement.propertyOutstanding) &&
        Objects.equals(this.propertyTax, agreement.propertyTax) &&
        Objects.equals(this.penalty, agreement.penalty) &&
        Objects.equals(this.rent, agreement.rent) &&
        Objects.equals(this.budgetHead, agreement.budgetHead) &&
        Objects.equals(this.currentElectricityBillAmount, agreement.currentElectricityBillAmount) &&
        Objects.equals(this.rentIncrement, agreement.rentIncrement) &&
        Objects.equals(this.currentPenalty, agreement.currentPenalty) &&
        Objects.equals(this.agreementFromDate, agreement.agreementFromDate) &&
        Objects.equals(this.agreementToDate, agreement.agreementToDate) &&
        Objects.equals(this.holdingType, agreement.holdingType) &&
        Objects.equals(this.gbResolutionNo, agreement.gbResolutionNo) &&
        Objects.equals(this.paymentCycle, agreement.paymentCycle) &&
        Objects.equals(this.remarks, agreement.remarks) &&
        Objects.equals(this.applicationNo, agreement.applicationNo) &&
        Objects.equals(this.documents, agreement.documents) &&
        Objects.equals(this.demands, agreement.demands) &&
        Objects.equals(this.cancelAgreementNumber, agreement.cancelAgreementNumber) &&
        Objects.equals(this.evictionAgreementNumber, agreement.evictionAgreementNumber) &&
        Objects.equals(this.renewalAgreementNumber, agreement.renewalAgreementNumber) &&
        Objects.equals(this.section, agreement.section) &&
        Objects.equals(this.actName, agreement.actName) &&
        Objects.equals(this.auctionDate, agreement.auctionDate) &&
        Objects.equals(this.evictionReason, agreement.evictionReason) &&
        Objects.equals(this.stateId, agreement.stateId) &&
        Objects.equals(this.workFlowDetails, agreement.workFlowDetails) &&
        Objects.equals(this.auditDetails, agreement.auditDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, tenantId, source, status, isActive, parent, action, leaseType, oldAgreementNumber, agreementNumber, landEstateNo, allottee, registrationNo, registrationDate, registerPageNo, depositAmount, electricityBillAmount, propertyOutstanding, propertyTax, penalty, rent, budgetHead, currentElectricityBillAmount, rentIncrement, currentPenalty, agreementFromDate, agreementToDate, holdingType, gbResolutionNo, paymentCycle, remarks, applicationNo, documents, demands, cancelAgreementNumber, evictionAgreementNumber, renewalAgreementNumber, section, actName, auctionDate, evictionReason, stateId, workFlowDetails, auditDetails);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Agreement {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    leaseType: ").append(toIndentedString(leaseType)).append("\n");
    sb.append("    oldAgreementNumber: ").append(toIndentedString(oldAgreementNumber)).append("\n");
    sb.append("    agreementNumber: ").append(toIndentedString(agreementNumber)).append("\n");
    sb.append("    landEstateNo: ").append(toIndentedString(landEstateNo)).append("\n");
    sb.append("    allottee: ").append(toIndentedString(allottee)).append("\n");
    sb.append("    registrationNo: ").append(toIndentedString(registrationNo)).append("\n");
    sb.append("    registrationDate: ").append(toIndentedString(registrationDate)).append("\n");
    sb.append("    registerPageNo: ").append(toIndentedString(registerPageNo)).append("\n");
    sb.append("    depositAmount: ").append(toIndentedString(depositAmount)).append("\n");
    sb.append("    electricityBillAmount: ").append(toIndentedString(electricityBillAmount)).append("\n");
    sb.append("    propertyOutstanding: ").append(toIndentedString(propertyOutstanding)).append("\n");
    sb.append("    propertyTax: ").append(toIndentedString(propertyTax)).append("\n");
    sb.append("    penalty: ").append(toIndentedString(penalty)).append("\n");
    sb.append("    rent: ").append(toIndentedString(rent)).append("\n");
    sb.append("    budgetHead: ").append(toIndentedString(budgetHead)).append("\n");
    sb.append("    currentElectricityBillAmount: ").append(toIndentedString(currentElectricityBillAmount)).append("\n");
    sb.append("    rentIncrement: ").append(toIndentedString(rentIncrement)).append("\n");
    sb.append("    currentPenalty: ").append(toIndentedString(currentPenalty)).append("\n");
    sb.append("    agreementFromDate: ").append(toIndentedString(agreementFromDate)).append("\n");
    sb.append("    agreementToDate: ").append(toIndentedString(agreementToDate)).append("\n");
    sb.append("    holdingType: ").append(toIndentedString(holdingType)).append("\n");
    sb.append("    gbResolutionNo: ").append(toIndentedString(gbResolutionNo)).append("\n");
    sb.append("    paymentCycle: ").append(toIndentedString(paymentCycle)).append("\n");
    sb.append("    remarks: ").append(toIndentedString(remarks)).append("\n");
    sb.append("    applicationNo: ").append(toIndentedString(applicationNo)).append("\n");
    sb.append("    documents: ").append(toIndentedString(documents)).append("\n");
    sb.append("    demands: ").append(toIndentedString(demands)).append("\n");
    sb.append("    cancelAgreementNumber: ").append(toIndentedString(cancelAgreementNumber)).append("\n");
    sb.append("    evictionAgreementNumber: ").append(toIndentedString(evictionAgreementNumber)).append("\n");
    sb.append("    renewalAgreementNumber: ").append(toIndentedString(renewalAgreementNumber)).append("\n");
    sb.append("    section: ").append(toIndentedString(section)).append("\n");
    sb.append("    actName: ").append(toIndentedString(actName)).append("\n");
    sb.append("    auctionDate: ").append(toIndentedString(auctionDate)).append("\n");
    sb.append("    evictionReason: ").append(toIndentedString(evictionReason)).append("\n");
    sb.append("    stateId: ").append(toIndentedString(stateId)).append("\n");
    sb.append("    workFlowDetails: ").append(toIndentedString(workFlowDetails)).append("\n");
    sb.append("    auditDetails: ").append(toIndentedString(auditDetails)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

