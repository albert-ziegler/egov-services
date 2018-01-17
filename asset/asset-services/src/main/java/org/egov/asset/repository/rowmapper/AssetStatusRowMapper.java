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

package org.egov.asset.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.egov.asset.model.AssetStatus;
import org.egov.asset.model.AuditDetails;
import org.egov.asset.model.StatusValue;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AssetStatusRowMapper implements RowMapper<AssetStatus> {

    @Override
    public AssetStatus mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final AssetStatus assetStatus = new AssetStatus();
        final StatusValue statusValue = new StatusValue();
        final AuditDetails auditDetails = new AuditDetails();
        final List<StatusValue> statusValues = new ArrayList<StatusValue>();

        statusValue.setName(rs.getString("name"));
        statusValue.setCode(rs.getString("code"));
        statusValue.setDescription(rs.getString("description"));
        statusValues.add(statusValue);

        assetStatus.setObjectName(rs.getString("objectName"));
        assetStatus.setTenantId(rs.getString("tenantId"));
        assetStatus.setStatusValues(statusValues);

        auditDetails.setCreatedBy(rs.getString("createdby"));
        auditDetails.setCreatedDate(rs.getLong("createddate"));
        auditDetails.setLastModifiedBy(rs.getString("lastmodifiedby"));
        auditDetails.setLastModifiedDate(rs.getLong("lastmodifieddate"));

        assetStatus.setAuditDetails(auditDetails);
        return assetStatus;
    }

}
