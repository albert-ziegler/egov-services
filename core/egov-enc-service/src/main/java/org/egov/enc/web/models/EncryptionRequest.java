package org.egov.enc.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EncryptionRequest {

    @JsonProperty("encryptionRequests")
    private ArrayList<EncReqObject> encryptionRequests;

}
