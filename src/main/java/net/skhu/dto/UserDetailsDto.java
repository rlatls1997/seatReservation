package net.skhu.dto;

import lombok.Data;

@Data
public class UserDetailsDto {
	int id;
	String userId;
	String name;
	String password;

	String email;
	String typeId;

}
