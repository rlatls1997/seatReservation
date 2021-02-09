package net.skhu.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class BoardCreate {
	@NotEmpty(message="제목을 입력하세요")
	String title;

	@NotEmpty(message="내용을 입력하세요")
	String content;

	String userId;
	LocalDateTime reportingDate;
}
