package com.example.usermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usermicroservice.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
