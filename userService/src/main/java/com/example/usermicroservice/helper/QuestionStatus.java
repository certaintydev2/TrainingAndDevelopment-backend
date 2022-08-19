package com.example.usermicroservice.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionStatus {
	
	private Long id;
	private Long questionId;
	private Long userId;
	private String status;

}
