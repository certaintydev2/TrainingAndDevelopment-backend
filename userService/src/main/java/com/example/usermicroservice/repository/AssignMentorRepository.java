package com.example.usermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.usermicroservice.entity.AssignMentor;
import com.example.usermicroservice.entity.UserData;

@Repository
public interface AssignMentorRepository extends JpaRepository<AssignMentor, Long> {
	
	@Query("select u from AssignMentor u where u.traineeId = :traineeId")
	public AssignMentor getAssignMentorByTraineeId(@Param("traineeId") Long traineeId);

}
