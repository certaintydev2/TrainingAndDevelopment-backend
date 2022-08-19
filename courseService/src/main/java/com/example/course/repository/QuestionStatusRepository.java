package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.course.entity.QuestionsStatus;

@Repository
public interface QuestionStatusRepository extends JpaRepository<QuestionsStatus, Long> {
	
	@Query("select t from QuestionsStatus t where t.questionId=:questionId")
	public QuestionsStatus findStatusByQuestionId(@Param("questionId") Long questionId);

}
