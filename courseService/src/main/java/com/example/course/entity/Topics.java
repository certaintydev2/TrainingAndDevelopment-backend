package com.example.course.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "topics")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Topics {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "topicId")
	private Long id;
	private String topicName;

	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "courseId", nullable = false)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
	private Course course;
}
