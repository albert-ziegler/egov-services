package org.egov.enc.web.models;

import java.util.LinkedList;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.egov.enc.web.models.RequestInfo;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * Object with the value to be signed
 */
@ApiModel(description = "Object with the value to be signed")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2018-10-11T17:31:52.360+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignRequest   {

    /**
     * Type of the value to be encrypted value / object. Sign object as a whole or sign values seperately inside that object.
     */
//    public enum TypeOfValueEnum {
//        VALUE("VALUE"),
//
//        OBJECT("OBJECT");
//
//        private String value;
//
//        TypeOfValueEnum(String value) {
//            this.value = value;
//        }
//
//        @Override
//        @JsonValue
//        public String toString() {
//            return String.valueOf(value);
//        }
//
//        @JsonCreator
//        public static TypeOfValueEnum fromValue(String text) {
//            for (TypeOfValueEnum b : TypeOfValueEnum.values()) {
//                if (String.valueOf(b.value).equals(text)) {
//                    return b;
//                }
//            }
//            return null;
//        }
//    }
//
//    @JsonProperty("typeOfValue")
//    private TypeOfValueEnum typeOfValue = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("value")
    private String value = null;

}

