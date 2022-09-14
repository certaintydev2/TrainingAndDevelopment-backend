package com.example.usermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usermicroservice.entity.AssignMentor;

@Repository
public interface AssignMentorRepository extends JpaRepository<AssignMentor, Long> {

}
