package com.example.course.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "question")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Questions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String question;

	@ManyToOne(fetch = FetchType.LAZY , optional = false)
	@JoinColumn(name = "subTopicId", nullable = false)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
	private SubTopic subTopic;
}
