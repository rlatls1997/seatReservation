package net.skhu.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserSignIn {

		@NotEmpty(message="아이디를 입력하세요.")
		String userId;

		@NotEmpty(message="비밀번호를 입력하세요")
		String password;
}
