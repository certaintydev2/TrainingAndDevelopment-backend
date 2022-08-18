package com.example.course.dto;

import com.example.course.entity.Topics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubTopicDTO {
	
	private String subTopicName;
	private Topics topic;

}
