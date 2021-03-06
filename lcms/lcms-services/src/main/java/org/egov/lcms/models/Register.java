package org.egov.lcms.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Veswanth
 *	This object holds information about the Register
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Register {
	
	@JsonProperty("code")
	private String code;
	
	@NotNull
	@Size(min = 4, max = 128)
	@JsonProperty("tenantId")
	private String tenantId ;

	@NotNull
	@JsonProperty("register")
	private String register;

	@NotNull
	@JsonProperty("isActive")
	private Boolean isActive;

	@JsonProperty("auditDetails")
	private AuditDetails auditDetails;
}
