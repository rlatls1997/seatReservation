package net.skhu.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class BoardCreateDto {
	@NotEmpty(message="제목을 입력하세요.")
	String title;

	@NotEmpty(message="내용을 입력하세요.")
	String content;

	String userId;
	LocalDateTime reportingDate;
}
