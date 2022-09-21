package com.example.course.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sub_topics")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubTopic {

	@Id
	@Column(name = "subTopicId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String subTopicName;

	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "topicId", nullable = false)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
	private Topics topic;
}
