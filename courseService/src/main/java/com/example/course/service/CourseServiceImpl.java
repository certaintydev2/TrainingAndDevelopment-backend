package com.example.course.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.example.course.util.Constants;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private TopicRepository topicRepo;
	
	@Autowired
	private SubTopicRepository subTopicRepo;
	
	@Autowired
	private QuestionRepository questionRepo;

	@Override
	public Course addCourse(CourseDTO course) {
		Course courseData = new Course();
		courseData.setCourseName(course.getCourseName());
		courseData.setAuthorId(course.getAuthorId());
		courseData.setMentorId(course.getMentorId());
		return this.courseRepo.save(courseData);
	}

	@Override
	public Topics addTopics(TopicDTO topics) {
		Topics topic = new Topics();
		Course course = this.courseRepo.findCourseByCourseName(topics.getCourse().getCourseName());
		topic.setTopicName(topics.getTopicName());
		topic.setCourse(course);
		return this.topicRepo.save(topic);
	}

	@Override
	public SubTopic addSubTopics(SubTopicDTO subTopic) {
		SubTopic subTopicData = new SubTopic();
		Topics topics = this.topicRepo.findTopicByTopicName(subTopic.getTopic().getTopicName());
		subTopicData.setSubTopicName(subTopic.getSubTopicName());
		subTopicData.setTopic(topics);
		return this.subTopicRepo.save(subTopicData);
	}

	@Override
	public Questions addQuestions(QuestionDTO questions) {
		Questions questionData = new Questions();
		SubTopic subTopic = this.subTopicRepo.findSubTopicBySubTopicName(questions.getSubTopic().getSubTopicName());
		questionData.setQuestion(questions.getQuestion());
		questionData.setSubTopic(subTopic);
		return this.questionRepo.save(questionData);
	}

	@Override
	public List<Course> getCourse() {
		return this.courseRepo.findAll();
	}

	@Override
	public List<Topics> getTopics() {
		return this.topicRepo.findAll();
	}

	@Override
	public List<SubTopic> getSubTopics() {
		return this.subTopicRepo.findAll();
	}

	@Override
	public List<Questions> getQuestions() {
		return this.questionRepo.findAll();
	}

	@Override
	public List<Topics> getTopicByCourseId(Long courseId) {
		return this.topicRepo.findTopicByCourseId(courseId);
	}

	@Override
	public List<SubTopic> getSubTopicByTopicId(Long topicId) {
		return this.subTopicRepo.findSubTopicByTopicId(topicId);
	}

	@Override
	public List<Questions> getQuestionsBySubTopicId(Long subTopicId) {
		return this.questionRepo.findQuestionsBySubTopicId(subTopicId);
	}

	@Override
	public Course getCourseByCourseName(String course) {
		return this.courseRepo.findCourseByCourseName(course);
	}

	@Override
	public Course updateCourse(Long courseId, CourseDTO courseDTO) {
		Course course = this.courseRepo.getById(courseId);
		course.setCourseName(courseDTO.getCourseName());
		course.setAuthorId(courseDTO.getAuthorId());
		course.setMentorId(courseDTO.getMentorId());
		return this.courseRepo.save(course);
	}

	@Override
	public Course getCourseByCourseId(Long courseId) {
		return this.courseRepo.findCourseByCourseId(courseId);
	}

	@Override
	public Topics updateTopic(Long id, TopicDTO topicDTO) {
		Topics topic = this.topicRepo.getById(id);
		topic.setTopicName(topicDTO.getTopicName());
		return this.topicRepo.save(topic);
	}

	@Override
	public Topics getTopicByTopicId(Long id) {
		return this.topicRepo.findTopicByTopicId(id);
	}

	@Override
	public String deleteCourse(Long courseId) {
		Course course = this.courseRepo.findCourseByCourseId(courseId);
		List<Topics> topics_list = this.topicRepo.findTopicByCourseId(courseId);
		
		for(Topics topic : topics_list) {
			this.deleteTopic(topic.getId());
		}
		this.courseRepo.delete(course);
		return Constants.COURSE_DELETED_SUCCESSFULLY;
	}

	@Override
	public String deleteTopic(Long id) {
		Topics topic = this.topicRepo.findTopicByTopicId(id);
		List<SubTopic> subTopics_list = this.subTopicRepo.findSubTopicByTopicId(id);
		for(SubTopic subTopic : subTopics_list) {
			this.deleteSubTopic(subTopic.getId());
		}
		this.topicRepo.delete(topic);
		return Constants.TOPIC_DELETED_SUCCESSFULLY;
	}

	@Override
	public String deleteSubTopic(Long id) {
		SubTopic subTopic = this.subTopicRepo.findSubTopicById(id);
		List<Questions> questions_list = this.questionRepo.findQuestionsBySubTopicId(id);
		for(Questions ques : questions_list) {
			this.deleteQuestion(ques.getId());
		}
		this.subTopicRepo.delete(subTopic);
		return Constants.SUBTOPIC_DELETED_SUCCESSFULLY;
	}
	
	@Override
	public String deleteQuestion(Long id) {
		Questions question = this.questionRepo.findQuestionById(id);
		this.questionRepo.delete(question);
		return Constants.QUESTION_DELETED_SUCCESSFULLY;
	}

	@Override
	public SubTopic getSubTopicBySubTopicId(Long id) {
		return this.subTopicRepo.findSubTopicById(id);
	}

	@Override
	public Questions getQuestionsByQuestionId(Long id) {
		return this.questionRepo.findQuestionById(id);
	}

	@Override
	public SubTopic updateSubTopic(Long id, SubTopicDTO subTopicDTO) {
		SubTopic subTopic = this.subTopicRepo.findSubTopicById(id);
		subTopic.setSubTopicName(subTopicDTO.getSubTopicName());
		return this.subTopicRepo.save(subTopic);
	}

	@Override
	public Questions updateQuestion(Long id, QuestionDTO questionDTO) {
		Questions question = this.questionRepo.findQuestionById(id);
		question.setQuestion(questionDTO.getQuestion());
		return this.questionRepo.save(question);
	}

}
