package com.example.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.course.entity.Topics;

@Repository
public interface TopicRepository extends JpaRepository<Topics, Long> {
	
	
	@Query("select t from Topics t where t.topicName=:topicName")
	public Topics findTopicByTopicName(@Param("topicName") String topicName);
	
	@Query(value = "select * from topics" , nativeQuery = true)
	public List<Topics> getAllTopics();
	
	
	@Query(value = "select * from topics  where course_id=?1" , nativeQuery = true)
	public List<Topics> findTopicByCourseId(Long courseId);
	
	@Query("select t from Topics t where t.id=:id")
	public Topics findTopicByTopicId(@Param("id") Long id);

}
