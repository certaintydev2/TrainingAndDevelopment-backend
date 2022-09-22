package com.example.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.course.entity.OnlineAssessmentLinks;

@Repository
public interface OnlineAssessmentLinksRepository extends JpaRepository<OnlineAssessmentLinks, Long> {

	@Query(value = "select * from online_assessment  where sub_topic_sub_topic_id=?1" , nativeQuery = true)
	public List<OnlineAssessmentLinks> findAssessmentinksBySubTopicId(Long subTopicId);
}
