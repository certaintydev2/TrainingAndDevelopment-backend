package com.example.course.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "questionStatus")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionsStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long questionId;
	private Long userId;
	private String status;
}
