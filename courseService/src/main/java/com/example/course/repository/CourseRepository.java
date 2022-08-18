package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.course.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	
	@Query("select c from Course c where c.courseName=:courseName")
	public Course findCourseByCourseName(@Param("courseName") String courseName);
	
	@Query("select c from Course c where c.courseId=:courseId")
	public Course findCourseByCourseId(@Param("courseId") Long courseId);

}
