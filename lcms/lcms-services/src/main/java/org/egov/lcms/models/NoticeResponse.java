package org.egov.lcms.models;

import org.egov.common.contract.response.ResponseInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Yosadhara
 *	This object holds information about the Notice response
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponse {
	
	@JsonProperty("ResponseInfo")
	private ResponseInfo responseInfo;
	
	@JsonProperty("notice")
    private Notice notice;
}
