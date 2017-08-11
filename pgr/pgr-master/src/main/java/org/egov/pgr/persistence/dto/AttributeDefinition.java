package org.egov.pgr.persistence.dto;

import lombok.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDefinition {

    private static final char YES = 'Y';
    private static final char NO = 'N';

    private char variable;
    private String dataType;
    private char required;
    private String dataTypeDescription;
    private Integer ordernum;
    private String description;
    private String code;
    private String serviceCode;
    private String url;
    private String groupCode;
    private String tenantId;
    private Long createdBy;
    private Date createdDate;
    private Long lastModifiedBy;
    private Date lastModifiedDate;
    private List<ValueDefinition> valueDefinitions;

    public org.egov.pgr.domain.model.AttributeDefinition toDomain() {
        return org.egov.pgr.domain.model.AttributeDefinition.builder()
                .code(code)
                .dataType(dataType)
                .required(isRequired())
                .dataTypeDescription(dataTypeDescription)
                .order(ordernum)
                .description(description)
                .url(url)
                .groupCode(groupCode)
                .readOnly(isReadOnly())
                .valueDefinitions(mapToDomainValueDefinitions())
                .build();
    }

    public boolean isValueList(){
        return dataType.equalsIgnoreCase("multivaluelist") || dataType.equalsIgnoreCase("singlevaluelist");
    }

    private boolean isRequired() {
        return required == YES;
    }

    private boolean isReadOnly() {
        return variable == YES;
    }

    private List mapToDomainValueDefinitions(){

        if(null == valueDefinitions)
            return Collections.EMPTY_LIST;

        return   valueDefinitions.stream()
                    .map(ValueDefinition::toDomain)
                    .collect(Collectors.toList());
    }
}