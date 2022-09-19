package com.example.usermicroservice.entity;

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
@Table(name = "solve_test")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SolveTest {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String name;
	    private String type;
	    private String filePath;
	    private Long traineeId;
	    private Long questionId;
}
