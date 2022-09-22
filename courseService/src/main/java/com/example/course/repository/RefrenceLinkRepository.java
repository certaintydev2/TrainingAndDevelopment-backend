package com.example.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.course.entity.RefrenceLinks;

@Repository
public interface RefrenceLinkRepository extends JpaRepository<RefrenceLinks, Long> {
	
	@Query(value = "select * from refrence_links  where sub_topic_sub_topic_id=?1" , nativeQuery = true)
	public List<RefrenceLinks> findRefrenceLinksBySubTopicId(Long subTopicId);

}
