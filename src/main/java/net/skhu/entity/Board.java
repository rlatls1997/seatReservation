package net.skhu.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class) /* JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다. */
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String title;
	String content;

	@ManyToOne
	@JoinColumn(name = "writer")
	User user;

	@CreatedDate
    @Column(updatable = false)
    LocalDateTime reportingDate;
}