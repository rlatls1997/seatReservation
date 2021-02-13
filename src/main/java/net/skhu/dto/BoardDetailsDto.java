package net.skhu.dto;

import java.time.LocalDateTime;

import lombok.Data;
import net.skhu.entity.User;

@Data
public class BoardDetailsDto {
	int id;
	String title;
	String content;

	User user;
	LocalDateTime reportingDate;
}
