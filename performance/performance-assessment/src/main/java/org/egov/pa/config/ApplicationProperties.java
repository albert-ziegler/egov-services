
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

package org.egov.pa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

@Configuration
@PropertySource(value = { "classpath:config/application-config.properties" }, ignoreResourceNotFound = true)
@Order(0)
public class ApplicationProperties {
    
    @Value("${kafka.topics.newkpi.create.name}")
    private String newKpiCreateTopicName;
    
    @Value("${kafka.topics.newkpi.create.key}")
    private String newKpiCreateTopicKey;
    
    @Value("${egov.services.tenant.host}")
    private String tenantServiceHostName; 
    
    @Value("${egov.services.tenant.searchpath}")
    private String tenantServiceSearchPath;
    
    @Value("${egov.services.mdms_service.hostname}")
    private String mdmsServiceHostName;
    
    @Value("${egov.services.mdms_service.searchpath}")
    private String mdmsServiceSearchPath;
    
    @Value("${egov.services.mdms_service.department_search}")
    private String mdmsServiceSearchGetDepartmentUrl; 
    
    @Value("${egov.services.mdms_service.category_search}")
    private String mdmsServiceSearchGetCategoryUrl; 
    
    @Value("${egov.services.mdms_service.tenant_search}")
    private String mdmsServiceSearchTenantUrl;
    
    @Value("${kafka.topics.kpivaluedetail.create.name}")
    private String kpiValueDetailCreateTopic;
    
    @Value("${kafka.topics.kpivaluedetail.create.key}")
    private String kpiValueDetailCreateKey;
    
    
    
    

	public String getMdmsServiceSearchGetCategoryUrl() {
		return mdmsServiceSearchGetCategoryUrl;
	}

	public void setMdmsServiceSearchGetCategoryUrl(String mdmsServiceSearchGetCategoryUrl) {
		this.mdmsServiceSearchGetCategoryUrl = mdmsServiceSearchGetCategoryUrl;
	}

	public String getKpiValueDetailCreateTopic() {
		return kpiValueDetailCreateTopic;
	}

	public void setKpiValueDetailCreateTopic(String kpiValueDetailCreateTopic) {
		this.kpiValueDetailCreateTopic = kpiValueDetailCreateTopic;
	}

	public String getKpiValueDetailCreateKey() {
		return kpiValueDetailCreateKey;
	}

	public void setKpiValueDetailCreateKey(String kpiValueDetailCreateKey) {
		this.kpiValueDetailCreateKey = kpiValueDetailCreateKey;
	}

	public String getMdmsServiceSearchTenantUrl() {
		return mdmsServiceSearchTenantUrl;
	}

	public void setMdmsServiceSearchTenantUrl(String mdmsServiceSearchTenantUrl) {
		this.mdmsServiceSearchTenantUrl = mdmsServiceSearchTenantUrl;
	}

	public void setNewKpiCreateTopicName(String newKpiCreateTopicName) {
		this.newKpiCreateTopicName = newKpiCreateTopicName;
	}

	public void setNewKpiCreateTopicKey(String newKpiCreateTopicKey) {
		this.newKpiCreateTopicKey = newKpiCreateTopicKey;
	}

	public void setTenantServiceHostName(String tenantServiceHostName) {
		this.tenantServiceHostName = tenantServiceHostName;
	}

	public void setTenantServiceSearchPath(String tenantServiceSearchPath) {
		this.tenantServiceSearchPath = tenantServiceSearchPath;
	}

	public String getMdmsServiceHostName() {
		return mdmsServiceHostName;
	}

	public void setMdmsServiceHostName(String mdmsServiceHostName) {
		this.mdmsServiceHostName = mdmsServiceHostName;
	}

	public String getMdmsServiceSearchPath() {
		return mdmsServiceSearchPath;
	}

	public void setMdmsServiceSearchPath(String mdmsServiceSearchPath) {
		this.mdmsServiceSearchPath = mdmsServiceSearchPath;
	}

	public String getMdmsServiceSearchGetDepartmentUrl() {
		return mdmsServiceSearchGetDepartmentUrl;
	}

	public void setMdmsServiceSearchGetDepartmentUrl(String mdmsServiceSearchGetDepartmentUrl) {
		this.mdmsServiceSearchGetDepartmentUrl = mdmsServiceSearchGetDepartmentUrl;
	}

	public String getNewKpiCreateTopicName() {
		return newKpiCreateTopicName;
	}

	public String getNewKpiCreateTopicKey() {
		return newKpiCreateTopicKey;
	}
	
	public String getTenantServiceHostName() { 
		return tenantServiceHostName; 
	}
	
	public String getTenantServiceSearchPath() { 
		return tenantServiceSearchPath;
	}
}