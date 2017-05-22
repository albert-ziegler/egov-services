package org.egov.domain.service;

import org.egov.domain.model.EmailMessageContext;
import org.egov.domain.model.ServiceType;
import org.egov.domain.model.SevaRequest;
import org.trimou.util.ImmutableMap;

import java.util.Map;

public class NewDeliverableEmailMessageStrategy implements EmailMessageStrategy {
    private static final String EMAIL_BODY_EN_TEMPLATE = "email_body_created_deliverable_service";
    private static final String EMAIL_SUBJECT_EN_TEMPLATE = "email_subject_created_deliverable_service";

    @Override
    public boolean matches(SevaRequest sevaRequest, ServiceType serviceType) {
        return false;
    }

    @Override
    public EmailMessageContext getMessageContext(SevaRequest sevaRequest, ServiceType serviceType) {
        return EmailMessageContext.builder()
            .bodyTemplateName(EMAIL_BODY_EN_TEMPLATE)
            .bodyTemplateValues(getBodyTemplate(sevaRequest))
            .subjectTemplateName(EMAIL_SUBJECT_EN_TEMPLATE)
            .subjectTemplateValues(getSubjectTemplateValues(sevaRequest))
            .build();
    }

    private Map<Object, Object> getBodyTemplate(SevaRequest sevaRequest) {
        ImmutableMap.ImmutableMapBuilder<Object, Object> builder = ImmutableMap.builder();
        builder.put("complainantName", sevaRequest.getRequesterName());
        builder.put("crn", sevaRequest.getCrn());
        builder.put("complaintType", sevaRequest.getServiceTypeName());
        builder.put("locationName", sevaRequest.getLocationName());
        builder.put("complaintDetails", sevaRequest.getDetails());
        builder.put("registeredDate", sevaRequest.getFormattedCreatedDate());
        builder.put("statusUpperCase", sevaRequest.getStatusName());
        builder.put("statusLowerCase",sevaRequest.getStatusName().toLowerCase());
        return builder.build();
    }

    private Map<Object, Object> getSubjectTemplateValues(SevaRequest sevaRequest) {
        return ImmutableMap.of("crn", sevaRequest.getCrn(),
            "statusLowerCase",sevaRequest.getStatusName().toLowerCase());
    }
}
