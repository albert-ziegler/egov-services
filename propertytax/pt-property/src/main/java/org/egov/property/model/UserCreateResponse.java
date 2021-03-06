package org.egov.property.model;

import java.util.List;

import org.egov.models.ResponseInfo;
import org.egov.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreateResponse {

	private List<User> user;

	private ResponseInfo responseInfo;
}
