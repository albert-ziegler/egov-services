/*
 		1* eGov suite of products aim to improve the internal efficiency,transparency,
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

package org.egov.boundary.domain.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.egov.boundary.domain.model.Boundary;
import org.egov.boundary.exception.CustomException;
import org.egov.boundary.persistence.repository.BoundaryRepository;
import org.egov.boundary.persistence.repository.CrossHierarchyRepository;
import org.egov.boundary.persistence.repository.MdmsRepository;
import org.egov.boundary.util.BoundaryConstants;
import org.egov.boundary.util.BoundaryUtilities;
import org.egov.boundary.web.contract.CrossHierarchy;
import org.egov.boundary.web.contract.CrossHierarchySearchRequest;
import org.egov.boundary.web.contract.MdmsCrossHierarchy;
import org.egov.boundary.web.contract.TenantBoundarySearch;
import org.egov.common.contract.request.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONArray;

@Service
public class CrossHierarchyService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BoundaryTypeService.class);

	private static final String CROSSHIERARCHY_BOUNDARYTYPES = "CrosshierarchyBoundaryTypes";
	private static final String ADMINISTRATION = "Administration";

	private CrossHierarchyRepository crossHierarchyRepository;
	private BoundaryRepository boundaryRepository;

	@Autowired
	private MdmsRepository mdmsRepository;
	
	@Autowired
	private BoundaryUtilities boundaryUtilities;

	@Autowired
	public CrossHierarchyService(final CrossHierarchyRepository crossHierarchyRepository,final BoundaryRepository boundaryRepository) {
		this.crossHierarchyRepository = crossHierarchyRepository;
		this.boundaryRepository = boundaryRepository;
	}

	public CrossHierarchy create(final CrossHierarchy crossHierarchy) {
		CrossHierarchy crossHierchy=null;
		try{
			crossHierchy = crossHierarchyRepository.save(crossHierarchy);
		}catch(Exception e){
			LOG.error("Exception while creating CrossHierarchy: ", e);
			throw new CustomException(
					Long.valueOf(HttpStatus.INTERNAL_SERVER_ERROR
							.toString()),
					BoundaryConstants.CROSSHIERARCHY_CREATE_EXCEPTION_MSG,
					BoundaryConstants.CROSSHIERARCHY_CREATE_EXCEPTION_DESC);	
		}
		return crossHierchy;
	}

	public CrossHierarchy update(final CrossHierarchy crossHierarchy) {
		CrossHierarchy crossHierchy=null;
		try{
			crossHierchy = crossHierarchyRepository.update(crossHierarchy);
		}catch(Exception e){
			LOG.error("Exception while updating CrossHierarchy: ", e);
			throw new CustomException(
					Long.valueOf(HttpStatus.INTERNAL_SERVER_ERROR
							.toString()),
					BoundaryConstants.CROSSHIERARCHY_UPDATE_EXCEPTION_MSG,
					BoundaryConstants.CROSSHIERARCHY_UPDATE_EXCEPTION_DESC);	
		}
		return crossHierchy;
	}

	public List<CrossHierarchy> getChildBoundaryNameAndBndryTypeAndHierarchyTypeAndTenantId(
			final String boundaryTypeName, final String hierarchyTypeName, final String parenthierarchyTypeName,
			final String name, final String tenantId) {
		return crossHierarchyRepository.findActiveBoundariesByNameAndBndryTypeNameAndHierarchyTypeNameAndTenantId(
				boundaryTypeName, hierarchyTypeName, parenthierarchyTypeName, name, tenantId);
	}

	public List<Boundary> getActiveChildBoundariesByBoundaryIdAndTenantId(final Long id, final String tenantId) {
		Set<Long> childBoundaryidList = new HashSet<Long>();
		List<CrossHierarchy> hierarchyList = crossHierarchyRepository.findCrossHierarchiesByParentIdAndTenantId(id,
				tenantId);
		for (CrossHierarchy crossHierarchy : hierarchyList) {
			if (crossHierarchy.getChild() != null) {
				childBoundaryidList.add(crossHierarchy.getChild().getId());
			}
		}
		List<Long> childBndryList = new ArrayList<Long>(childBoundaryidList);
		List<Boundary> boundaryList = null;
		if (!childBoundaryidList.isEmpty()) {
			boundaryList = boundaryRepository.findAllBoundariesByIdsAndTenant(tenantId, childBndryList);
		}
		return boundaryList;
	}

	public CrossHierarchy findByCodeAndTenantId(String code, String tenantId) {
		return crossHierarchyRepository.findByCodeAndTenantId(code, tenantId);
	}

	public List<CrossHierarchy> getAllCrossHierarchys(CrossHierarchySearchRequest crossHierarchyRequest) {
		List<CrossHierarchy> crossHierarchy = new ArrayList<CrossHierarchy>();
		if (crossHierarchyRequest.getCrossHierarchy() != null
				&& crossHierarchyRequest.getCrossHierarchy().getTenantId() != null
				&& !crossHierarchyRequest.getCrossHierarchy().getTenantId().isEmpty()) {
			if (crossHierarchyRequest.getCrossHierarchy().getId() != null) {
				crossHierarchy.add(
						crossHierarchyRepository.findByIdAndTenantId(crossHierarchyRequest.getCrossHierarchy().getId(),
								crossHierarchyRequest.getCrossHierarchy().getTenantId()));

			} else {
				if (crossHierarchyRequest.getCrossHierarchy().getCode() != null) {
					crossHierarchy.add(findByCodeAndTenantId(crossHierarchyRequest.getCrossHierarchy().getCode(),
							crossHierarchyRequest.getCrossHierarchy().getTenantId()));

				} else {
					crossHierarchy.addAll(crossHierarchyRepository
							.findAllByTenantId(crossHierarchyRequest.getCrossHierarchy().getTenantId()));
				}
			}
		}
		return crossHierarchy;
	}
	
	public List<CrossHierarchy> getAllCrossHierarchys(String tenantId,Long id){

		JSONArray responseJSONArray;
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		responseJSONArray = mdmsRepository.getCrossHierarchyByIdAndTenantId(tenantId,id,new RequestInfo());
		List<MdmsCrossHierarchy> crossHierarchyList = null;
		if (responseJSONArray != null && responseJSONArray.size() > 0) {
			crossHierarchyList = mapper.convertValue(responseJSONArray, new TypeReference<List<MdmsCrossHierarchy>>() {
			});
		}
		List<Boundary> boundaryList = findAllByTenantIdAndHierarchyType(tenantId,null);
		List<CrossHierarchy> list = new ArrayList<CrossHierarchy>();
		for(MdmsCrossHierarchy crossHierarchy : crossHierarchyList){
		CrossHierarchy crossHierarchy1 = new CrossHierarchy();
		for (Boundary boundary : boundaryList) {
			if (boundary.getId().equals(crossHierarchy.getParent())) {
				if(id != null){
				boundary.setChildren(null);
				boundary.setParent(null);
				}
				crossHierarchy1.setParent(boundary);
			}
			if (boundary.getId().equals(crossHierarchy.getChild())) {
				if(id != null){
				boundary.setChildren(null);
				boundary.setParent(null);
				}
				crossHierarchy1.setChild(boundary);
			}
		}
		crossHierarchy1.setId(crossHierarchy.getId());
		crossHierarchy1.setTenantId(tenantId);
		list.add(crossHierarchy1);
		}
		return list;
	}
	
	public List<Boundary> findAllByTenantIdAndHierarchyType(String tenantId,String hierarchytype) {
		JSONArray responseJSONArray;
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		responseJSONArray = mdmsRepository.getBoundariesByBndryTypeNameAndHierarchyTypeNameAndTenantId(tenantId, hierarchytype,
				new RequestInfo());
		List<TenantBoundarySearch> boundaryList = null;
		if (responseJSONArray != null && responseJSONArray.size() > 0) {
			boundaryList = mapper.convertValue(responseJSONArray, new TypeReference<List<TenantBoundarySearch>>() {
			});
		}
		List<Boundary> list = new ArrayList<Boundary>();
		if (boundaryList != null) {
			for (TenantBoundarySearch tenantBndry : boundaryList) {
				List<Boundary> list1 = boundaryUtilities.prepareListOfBoundaries(tenantBndry.getBoundary());
				list1.add(tenantBndry.getBoundary());
				for (Boundary bndry : list1) {
					list.add(bndry);
				}
			}
		}
		list = boundaryUtilities.addParentAndBoundaryType(list, tenantId);
		return list;
	}
}
