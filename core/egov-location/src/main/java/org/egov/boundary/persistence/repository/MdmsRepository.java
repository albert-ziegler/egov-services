/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *      accountability and the service delivery of the government  organizations.
 *  
 *       Copyright (C) <2015>  eGovernments Foundation
 *  
 *       The updated version of eGov suite of products as by eGovernments Foundation
 *       is available at http://www.egovernments.org
 *  
 *       This program is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       any later version.
 *  
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *  
 *       You should have received a copy of the GNU General Public License
 *       along with this program. If not, see http://www.gnu.org/licenses/ or
 *       http://www.gnu.org/licenses/gpl.html .
 *  
 *       In addition to the terms of the GPL license to be adhered to in using this
 *       program, the following additional terms are to be complied with:
 *  
 *           1) All versions of this program, verbatim or modified must carry this
 *              Legal Notice.
 *  
 *           2) Any misrepresentation of the origin of the material is prohibited. It
 *              is required that all modified versions of this material be marked in
 *              reasonable ways as different from the original version.
 *  
 *           3) This license does not grant any rights to any user of the program
 *              with regards to rights under trademark law for use of the trade names
 *              or trademarks of eGovernments Foundation.
 *  
 *     In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.boundary.persistence.repository;

import org.egov.boundary.web.contract.MasterDetails;
import org.egov.boundary.web.contract.MdmsCriteria;
import org.egov.boundary.web.contract.MdmsRequest;
import org.egov.boundary.web.contract.MdmsResponse;
import org.egov.boundary.web.contract.ModuleDetails;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONArray;

@Service
public class MdmsRepository {

	private final RestTemplate restTemplate;

	private final String mdmsBySearchCriteriaUrl;
	
	private final String moduleName;
	private final String masterName;

	@Autowired
	public MdmsRepository(final RestTemplate restTemplate,
			@Value("${egov.services.egov_mdms.hostname}") final String mdmsServiceHostname,
			@Value("${egov.services.egov_mdms.searchpath}") final String mdmsBySearchCriteriaUrl,
			@Value("${egov.service.egov.mdms.moduleName}") final String moduleName,
			@Value("${egov.service.egov.mdms.masterName}") final String masterName) {

		this.restTemplate = restTemplate;
		this.mdmsBySearchCriteriaUrl = mdmsServiceHostname + mdmsBySearchCriteriaUrl;
		this.moduleName = moduleName;
	    this.masterName = masterName;			
	}

	public JSONArray getByCriteria(String tenantId,String hierarchyTypeCode,RequestInfo requestInfo) {
		
		MasterDetails[] masterDetails;
		ModuleDetails[] moduleDetails;
		MdmsRequest request = null;
		MdmsResponse response = null;
		masterDetails = new MasterDetails[1];
		moduleDetails = new ModuleDetails[1];
		String filter = null;
		if(hierarchyTypeCode!=null && hierarchyTypeCode!=""){
			filter = "[?(@." + "hierarchyType.code" + " in [" + hierarchyTypeCode.toUpperCase() + "])]";
		}
		masterDetails[0] = MasterDetails.builder().name(masterName)
				.filter(filter).build();
		moduleDetails[0] = ModuleDetails.builder().moduleName(moduleName).masterDetails(masterDetails).build();

		request = MdmsRequest.builder()
				.mdmsCriteria(MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId).build())
				.requestInfo(requestInfo).build();
		try{
		response = restTemplate.postForObject(mdmsBySearchCriteriaUrl, request, MdmsResponse.class);
		}catch(Exception e){
			System.out.println("Invalid TenantId" + e.getMessage());
		}
		if (response == null || response.getMdmsRes() == null || !response.getMdmsRes().containsKey(moduleName)
				|| response.getMdmsRes().get(moduleName) == null
				|| !response.getMdmsRes().get(moduleName).containsKey(masterName)
				|| response.getMdmsRes().get(moduleName).get(masterName) == null) {
			return new JSONArray();
		} else {

			return response.getMdmsRes().get(moduleName).get(masterName);

		}
	}
	
	public JSONArray getBoundariesByBndryTypeNameAndHierarchyTypeNameAndTenantId(String tenantId,String hierarchyTypeName,RequestInfo requestInfo) {
		MasterDetails[] masterDetails;
		ModuleDetails[] moduleDetails;
		MdmsRequest request = null;
		MdmsResponse response = null;
		masterDetails = new MasterDetails[1];
		moduleDetails = new ModuleDetails[1];
		String filter = null;
		if(hierarchyTypeName!=null && hierarchyTypeName!=""){
			filter = "[?(@." + "hierarchyType.name" + " in [" + hierarchyTypeName.toUpperCase() + "])]";
		}
		masterDetails[0] = MasterDetails.builder().name(masterName)
				.filter(filter).build();
		moduleDetails[0] = ModuleDetails.builder().moduleName(moduleName).masterDetails(masterDetails).build();

		request = MdmsRequest.builder()
				.mdmsCriteria(MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId).build())
				.requestInfo(requestInfo).build();
		try{
		response = restTemplate.postForObject(mdmsBySearchCriteriaUrl, request, MdmsResponse.class);
		}catch(Exception e){
			System.out.println("Invalid TenantId" + e.getMessage());
		}
		if (response == null || response.getMdmsRes() == null || !response.getMdmsRes().containsKey(moduleName)
				|| response.getMdmsRes().get(moduleName) == null
				|| !response.getMdmsRes().get(moduleName).containsKey(masterName)
				|| response.getMdmsRes().get(moduleName).get(masterName) == null) {
			return new JSONArray();
		} else {

			return response.getMdmsRes().get(moduleName).get(masterName);

		}
	}
	
	public JSONArray getCrossHierarchyByIdAndTenantId(String tenantId,Long id,RequestInfo requestInfo) {
		MasterDetails[] masterDetails;
		ModuleDetails[] moduleDetails;
		MdmsRequest request = null;
		MdmsResponse response = null;
		masterDetails = new MasterDetails[1];
		moduleDetails = new ModuleDetails[1];
		String filter = null;
		if(id!=null){
			filter = "[?(@.id IN [$id])]";
		    filter = filter.replaceAll("\\$id", id.toString());
		}
		
		masterDetails[0] = MasterDetails.builder().name("crossHierarchys")
				.filter(filter).build();
		moduleDetails[0] = ModuleDetails.builder().moduleName(moduleName).masterDetails(masterDetails).build();

		request = MdmsRequest.builder()
				.mdmsCriteria(MdmsCriteria.builder().moduleDetails(moduleDetails).tenantId(tenantId).build())
				.requestInfo(requestInfo).build();
		try{
		response = restTemplate.postForObject(mdmsBySearchCriteriaUrl, request, MdmsResponse.class);
		}catch(Exception e){
			System.out.println("Invalid TenantId" + e.getMessage());
		}
		if (response == null || response.getMdmsRes() == null || !response.getMdmsRes().containsKey(moduleName)
				|| response.getMdmsRes().get(moduleName) == null
				|| !response.getMdmsRes().get(moduleName).containsKey("crossHierarchys")
				|| response.getMdmsRes().get(moduleName).get("crossHierarchys") == null) {
			return new JSONArray();
		} else {

			return response.getMdmsRes().get(moduleName).get("crossHierarchys");

		}
	}
	
}
