/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */

package org.egov.asset.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.egov.asset.model.enums.TransactionType;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Asset sale /Disposal.
 */

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disposal {

    @JsonProperty("tenantId")
    @NotNull
    private String tenantId;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("assetId")
    private Long assetId = null;

    @JsonProperty("buyerName")
    private String buyerName = null;

    @JsonProperty("buyerAddress")
    private String buyerAddress = null;

    @JsonProperty("disposalReason")
    private String disposalReason = null;

    @JsonProperty("disposalDate")
    private Long disposalDate = null;

    @JsonProperty("panCardNumber")
    private String panCardNumber = null;

    @JsonProperty("aadharCardNumber")
    private String aadharCardNumber = null;

    @JsonProperty("assetCurrentValue")
    private BigDecimal assetCurrentValue = null;

    @JsonProperty("saleValue")
    private BigDecimal saleValue = null;

    @JsonProperty("transactionType")
    private TransactionType transactionType = null;

    @JsonProperty("assetSaleAccount")
    private Long assetSaleAccount = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    @JsonProperty("profitLossVoucherReference")
    private String profitLossVoucherReference;
}
