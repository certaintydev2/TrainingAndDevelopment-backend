package com.example.usermicroservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.usermicroservice.entity.TestQuestions;

@Repository
public interface TestQuestionsRepository extends JpaRepository<TestQuestions, Long> {

	@Query("select a from TestQuestions a where a.profile=:profile")
	public List<TestQuestions> getQuestionsByProfile(@Param("profile") String profile);
}
