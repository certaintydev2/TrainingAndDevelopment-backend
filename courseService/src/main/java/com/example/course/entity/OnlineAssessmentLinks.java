package com.example.course.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "online_assessment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OnlineAssessmentLinks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String assessmentLinks;
	
	@ManyToOne
	private SubTopic subTopic;
}
