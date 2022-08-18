package com.example.course.dto;

import com.example.course.entity.SubTopic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionDTO {

	private String question;
	private SubTopic subTopic;
}
