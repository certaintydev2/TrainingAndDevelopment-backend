package com.example.usermicroservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "test_questions_tab")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestQuestions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	private Long id;
	private String profile;
	@Column(length = 4000)
	private String question;
	@Column(length = 500)
	private String input;
	@Column(length = 500)
	private String expectedOutput;
}
