package com.example.course.dto;

import com.example.course.entity.Course;

import lombok.Getter;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TopicDTO {
	
	private String topicName;
	private Course course;

}
