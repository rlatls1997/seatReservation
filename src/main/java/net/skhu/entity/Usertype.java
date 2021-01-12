package net.skhu.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Usertype {
	@Id
	int id;

	String userType;
}
