package com.example.usermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usermicroservice.entity.SolveTest;

@Repository
public interface SolveTestRepository extends JpaRepository<SolveTest, Long> {

}
