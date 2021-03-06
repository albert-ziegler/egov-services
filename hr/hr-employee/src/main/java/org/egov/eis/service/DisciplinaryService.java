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

package org.egov.eis.service;

import java.util.ArrayList;
import java.util.List;

import org.egov.eis.model.Disciplinary;
import org.egov.eis.model.DisciplinaryDocuments;
import org.egov.eis.repository.DisciplinaryDocumentsRepository;
import org.egov.eis.repository.DisciplinaryRepository;
import org.egov.eis.repository.rowmapper.DisciplinaryDocumentsRowMapper;
import org.egov.eis.web.contract.DisciplinaryGetRequest;
import org.egov.eis.web.contract.DisciplinaryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DisciplinaryService {

    @Autowired
    private DisciplinaryRepository disciplinaryRepository;

    @Autowired
    private DisciplinaryDocumentsRepository disciplinaryDocumentsRepository;

    public Disciplinary create(DisciplinaryRequest disciplinaryRequest) {
        log.debug("DisciplinaryRequest::" + disciplinaryRequest);
        disciplinaryRequest.getDisciplinary().setId(disciplinaryRepository.generateSequences());
        disciplinaryRepository.create(disciplinaryRequest);
        return disciplinaryRequest.getDisciplinary();

    }

    public Disciplinary update(DisciplinaryRequest disciplinaryRequest) {
        log.debug("DisciplinaryRequest::" + disciplinaryRequest);
        disciplinaryRepository.update(disciplinaryRequest);
        return disciplinaryRequest.getDisciplinary();

    }

    public List<Disciplinary> getDisciplinarys(final DisciplinaryGetRequest disciplinaryGetRequest) {
        List<Disciplinary> disciplinaries = disciplinaryRepository.findForCriteria(disciplinaryGetRequest);

        for (final Disciplinary disciplinary : disciplinaries) {
            List<DisciplinaryDocuments> disciplinaryDocuments = new ArrayList<>();
            final List<DisciplinaryDocuments> documents = disciplinaryDocumentsRepository.findByDisciplinaryId(disciplinary.getId(),
                    disciplinary.getTenantId());
            for (final DisciplinaryDocuments document : documents) {
                DisciplinaryDocuments disciplinaryDocs= new DisciplinaryDocuments();
                disciplinaryDocs.setId(document.getId());
                disciplinaryDocs.setDisciplinaryId(disciplinary.getId());
                disciplinaryDocs.setDocumentType(document.getDocumentType());
                disciplinaryDocs.setFileStoreId(document.getFileStoreId());
                disciplinaryDocs.setTenantId(document.getTenantId());
                disciplinaryDocuments.add(disciplinaryDocs);
            }
            disciplinary.getDisciplinaryDocuments().addAll(disciplinaryDocuments);

        }
        return disciplinaries;
    }

}
