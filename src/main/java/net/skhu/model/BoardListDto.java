package net.skhu.model;

import java.time.LocalDateTime;

import lombok.Data;
import net.skhu.entity.User;

@Data
public class BoardListDto {
	int id;
	String title;
	User user;
	LocalDateTime reportingDate;
}
