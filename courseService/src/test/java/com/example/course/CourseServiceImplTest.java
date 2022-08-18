package com.example.course;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.example.course.dto.CourseDTO;
import com.example.course.dto.QuestionDTO;
import com.example.course.dto.SubTopicDTO;
import com.example.course.dto.TopicDTO;
import com.example.course.entity.Course;
import com.example.course.entity.Questions;
import com.example.course.entity.SubTopic;
import com.example.course.entity.Topics;
import com.example.course.repository.CourseRepository;
import com.example.course.repository.QuestionRepository;
import com.example.course.repository.SubTopicRepository;
import com.example.course.repository.TopicRepository;
import com.example.course.service.CourseServiceImpl;
import com.example.course.util.Constants;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = {CourseServiceImplTest.class})
class CourseServiceImplTest {
	
	@InjectMocks
	private CourseServiceImpl courseService;
	
	@Mock
	private CourseRepository courseRepo;
	
	@Mock
	private TopicRepository topicRepo;

	@Mock
	private SubTopicRepository subTopicRepo;
	
	@Mock
	private QuestionRepository questionRepo;
	
	@Test
	@Order(1)
	void test_addCourse() {
		CourseDTO courseDTO = new CourseDTO("Java", 1l, 2l);
		Course course = new Course(1l, courseDTO.getCourseName(), courseDTO.getAuthorId(), courseDTO.getMentorId());
		assertNotNull(courseDTO, Constants.COURSE_ADDED_SUCCESSFULLY);
		courseService.addCourse(courseDTO);
		when(courseRepo.save(course)).thenReturn(course);
	}
	
	@Test
	@Order(2)
	void test_addTopics() {
		Course course = new Course(1l, "Java", 1l, 2l);
		TopicDTO topicDTO = new TopicDTO("Java Core", course);
		when(courseRepo.findCourseByCourseName(topicDTO.getCourse().getCourseName())).thenReturn(course);
		Topics topic = new Topics(1l, topicDTO.getTopicName(), course);
		assertNotNull(topicDTO , Constants.TOPIC_ADDED_SUCCESSFULLY);
		courseService.addTopics(topicDTO);
		when(topicRepo.save(topic)).thenReturn(topic);
	}
	
	@Test
	@Order(3)
	void test_addSubTopics() {
		Course course = new Course(1l, "Java", 1l, 2l);
		Topics topic = new Topics(1l, "Java Core", course);
		SubTopicDTO subTopicDTO = new SubTopicDTO("Array", topic);
		when(topicRepo.findTopicByTopicName(subTopicDTO.getTopic().getTopicName())).thenReturn(topic);
		SubTopic subTopic = new SubTopic(1l, subTopicDTO.getSubTopicName(), topic);
		assertNotNull(subTopicDTO , Constants.SUBTOPIC_ADDED_SUCCESSFULLY);
		courseService.addSubTopics(subTopicDTO);
		when(subTopicRepo.save(subTopic)).thenReturn(subTopic);
	}
	
	@Test
	@Order(4)
	void test_addQuestions() {
		Course course = new Course(1l, "Java", 1l, 2l);
		Topics topic = new Topics(1l, "Java Core", course);
		SubTopic subTopic = new SubTopic(1l, "Array", topic);
		QuestionDTO questionDTO = new QuestionDTO("Reverse Array", subTopic);
		when(subTopicRepo.findSubTopicBySubTopicName(questionDTO.getSubTopic().getSubTopicName())).thenReturn(subTopic);
		Questions question = new Questions(1l, questionDTO.getQuestion(), subTopic);
		assertNotNull(questionDTO, Constants.QUESTION_ADDED_SUCCESSFULLY);
		courseService.addQuestions(questionDTO);
		when(questionRepo.save(question)).thenReturn(question);
	}
	
	@Test
	@Order(5)
	void test_getCourse() {
		List<Course> courseList = new ArrayList<Course>();
		courseList.add(new Course(1l, "Java", 1l, 2l));
		courseList.add(new Course(2l, "Python", 3l, 4l));
		when(courseRepo.findAll()).thenReturn(courseList);
		assertNotNull(courseList);
		courseService.getCourse();
	}
	
	@Test
	@Order(6)
	void test_getTopics() {
		Course course = new Course(1l, "Java", 1l, 2l);
		List<Topics> topicList = new ArrayList<Topics>();
		topicList.add(new Topics(1l, "Java Core", course));
		topicList.add(new Topics(2l, "Java Advance", course));
		when(topicRepo.findAll()).thenReturn(topicList);
		assertNotNull(topicList);
		courseService.getTopics();
	}
	
	@Test
	@Order(7)
	void test_getSubTopics() {
		Course course = new Course(1l, "Java", 1l, 2l);
		Topics topic = new Topics(1l, "Java Core", course);
		List<SubTopic> subTopicList = new ArrayList<SubTopic>();
		subTopicList.add(new SubTopic(1l, "Array", topic));
		subTopicList.add(new SubTopic(2l, "String", topic));
		when(subTopicRepo.findAll()).thenReturn(subTopicList);
		assertNotNull(subTopicList);
		courseService.getSubTopics();
	}
	
	@Test
	@Order(8)
	void test_getQuestions() {
		Course course = new Course(1l, "Java", 1l, 2l);
		Topics topic = new Topics(1l, "Java Core", course);
		SubTopic subTopic = new SubTopic(1l, "Array", topic);
		List<Questions> questionsList = new ArrayList<Questions>();
		questionsList.add(new Questions(1l, "Reverse Array", subTopic));
		questionsList.add(new Questions(1l, "Sort Array", subTopic));
		when(questionRepo.findAll()).thenReturn(questionsList);
		assertNotNull(questionsList);
		courseService.getQuestions();
	}
	
	@Test
	@Order(9)
	void test_getTopicByCourseId() {
		Course course = new Course(1l, "Java", 1l, 2l);
		List<Topics> topicList = new ArrayList<Topics>();
		topicList.add(new Topics(1l, "Java Core", course));
		topicList.add(new Topics(2l, "Java Advance", course));
		Long courseId = 1l;
		when(topicRepo.findTopicByCourseId(courseId)).thenReturn(topicList);
		assertNotNull(topicList);
		courseService.getTopicByCourseId(courseId);
	}
	
	@Test
	@Order(10)
	void test_getSubTopicByTopicId() {
		Course course = new Course(1l, "Java", 1l, 2l);
		Topics topic = new Topics(1l, "Java Core", course);
		List<SubTopic> subTopicList = new ArrayList<SubTopic>();
		subTopicList.add(new SubTopic(1l, "Array", topic));
		subTopicList.add(new SubTopic(2l, "String", topic));
		Long topicId = 1l;
		when(subTopicRepo.findSubTopicByTopicId(topicId)).thenReturn(subTopicList);
		assertNotNull(subTopicList);
		courseService.getSubTopicByTopicId(topicId);
	}
	
	@Test
	@Order(11)
	void test_getQuestionsBySubTopicId() {
		Course course = new Course(1l, "Java", 1l, 2l);
		Topics topic = new Topics(1l, "Java Core", course);
		SubTopic subTopic = new SubTopic(1l, "Array", topic);
		List<Questions> questionsList = new ArrayList<Questions>();
		questionsList.add(new Questions(1l, "Reverse Array", subTopic));
		questionsList.add(new Questions(1l, "Sort Array", subTopic));
		Long subTopicId = 1l;
		when(questionRepo.findQuestionsBySubTopicId(subTopicId)).thenReturn(questionsList);
		assertNotNull(questionsList);
		courseService.getQuestionsBySubTopicId(subTopicId);
	}
}
