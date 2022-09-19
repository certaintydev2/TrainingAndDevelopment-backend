package com.example.course.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course.dto.CourseDTO;
import com.example.course.dto.QuestionDTO;
import com.example.course.dto.QuestionStatusDTO;
import com.example.course.dto.SubTopicDTO;
import com.example.course.dto.TopicDTO;
import com.example.course.entity.Course;
import com.example.course.entity.Questions;
import com.example.course.entity.QuestionsStatus;
import com.example.course.entity.SubTopic;
import com.example.course.entity.Topics;
import com.example.course.repository.CourseRepository;
import com.example.course.repository.QuestionRepository;
import com.example.course.repository.QuestionStatusRepository;
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
	
	@Autowired
	private QuestionStatusRepository questionStatusRepo;

	/*
	 * here course added to database to database
	 */
	@Override
	public Course addCourse(CourseDTO course) {
		Course courseData = new Course();
		courseData.setCourseName(course.getCourseName());
		courseData.setAuthorId(course.getAuthorId());
		courseData.setCourseDescription(course.getCourseDescription());
		return this.courseRepo.save(courseData);
	}

	/*
	 * here topic added to database to database
	 */
	@Override
	public Topics addTopics(TopicDTO topics) {
		Topics topic = new Topics();
		Course course = this.courseRepo.findCourseByCourseName(topics.getCourse().getCourseName());
		topic.setTopicName(topics.getTopicName());
		topic.setCourse(course);
		topic.setTopicDescription(topics.getTopicDescription());
		topic.setDays(topics.getDays());
		topic.setEstimatedTime(topics.getEstimatedTime());
		return this.topicRepo.save(topic);
	}

	/*
	 * here subtopic added to database to database
	 */
	@Override
	public SubTopic addSubTopics(SubTopicDTO subTopic) {
		SubTopic subTopicData = new SubTopic();
		Topics topics = this.topicRepo.findTopicByTopicName(subTopic.getTopic().getTopicName());
		subTopicData.setSubTopicName(subTopic.getSubTopicName());
		subTopicData.setTopic(topics);
		return this.subTopicRepo.save(subTopicData);
	}

	/*
	 * here question added to database to database
	 */
	@Override
	public Questions addQuestions(QuestionDTO questions) {
		Questions questionData = new Questions();
		SubTopic subTopic = this.subTopicRepo.findSubTopicBySubTopicName(questions.getSubTopic().getSubTopicName());
		questionData.setQuestion(questions.getQuestion());
		questionData.setSubTopic(subTopic);
		return this.questionRepo.save(questionData);
	}

	/*
	 * get all courses from database
	 */
	@Override
	public List<Course> getCourse() {
		return this.courseRepo.findAll();
	}

	/*
	 * get all topics from database
	 */
	@Override
	public List<Topics> getTopics() {
		return this.topicRepo.findAll();
	}

	/*
	 * get all subtopics from database
	 */
	@Override
	public List<SubTopic> getSubTopics() {
		return this.subTopicRepo.findAll();
	}

	/*
	 * get all questions from database
	 */
	@Override
	public List<Questions> getQuestions() {
		return this.questionRepo.findAll();
	}

	/*
	 * get topics by courseId
	 */
	@Override
	public List<Topics> getTopicByCourseId(Long courseId) {
		return this.topicRepo.findTopicByCourseId(courseId);
	}

	/*
	 * get subtopic by topicId
	 */
	@Override
	public List<SubTopic> getSubTopicByTopicId(Long topicId) {
		return this.subTopicRepo.findSubTopicByTopicId(topicId);
	}

	/*
	 * get question by subtopicId
	 */
	@Override
	public List<Questions> getQuestionsBySubTopicId(Long subTopicId) {
		return this.questionRepo.findQuestionsBySubTopicId(subTopicId);
	}

	/*
	 * get course by course name
	 */
	@Override
	public Course getCourseByCourseName(String course) {
		return this.courseRepo.findCourseByCourseName(course);
	}

	/*
	 * update course
	 */
	@Override
	public Course updateCourse(Long courseId, CourseDTO courseDTO) {
		Course course = this.courseRepo.getById(courseId);
		course.setCourseName(courseDTO.getCourseName());
		course.setAuthorId(courseDTO.getAuthorId());
		return this.courseRepo.save(course);
	}

	/*
	 * get course by courseId
	 */
	@Override
	public Course getCourseByCourseId(Long courseId) {
		return this.courseRepo.findCourseByCourseId(courseId);
	}

	/*
	 * update topic
	 */
	@Override
	public Topics updateTopic(Long id, TopicDTO topicDTO) {
		Topics topic = this.topicRepo.getById(id);
		topic.setTopicName(topicDTO.getTopicName());
		return this.topicRepo.save(topic);
	}

	/*
	 * get topic by topicId
	 */
	@Override
	public Topics getTopicByTopicId(Long id) {
		return this.topicRepo.findTopicByTopicId(id);
	}

	/*
	 * delete course
	 */
	@Override
	public String deleteCourse(Long courseId) {
		Course course = this.courseRepo.findCourseByCourseId(courseId); // here we get course by courseId
		List<Topics> topics_list = this.topicRepo.findTopicByCourseId(courseId); // here we get topic list by courseId
		
		for(Topics topic : topics_list) {
			this.deleteTopic(topic.getId()); // first we delete topic associated by courseId
		}
		this.courseRepo.delete(course); // and here we delete course
		return Constants.COURSE_DELETED_SUCCESSFULLY;
	}

	/*
	 * delete topic
	 */
	@Override
	public String deleteTopic(Long id) {
		Topics topic = this.topicRepo.findTopicByTopicId(id); // here we get topic by topicId
		List<SubTopic> subTopics_list = this.subTopicRepo.findSubTopicByTopicId(id); // here we get subTopicList by topicId
		for(SubTopic subTopic : subTopics_list) {
			this.deleteSubTopic(subTopic.getId()); // first we delete subtopic associated with topicId
		}
		this.topicRepo.delete(topic); // and here we delete topic
		return Constants.TOPIC_DELETED_SUCCESSFULLY;
	}

	/*
	 * delete subTopic
	 */
	@Override
	public String deleteSubTopic(Long id) {
		SubTopic subTopic = this.subTopicRepo.findSubTopicById(id); // here we get subTopic by subTopicId
		List<Questions> questions_list = this.questionRepo.findQuestionsBySubTopicId(id); // here we get questions with subTopicId 
		for(Questions ques : questions_list) {
			this.deleteQuestion(ques.getId()); // here we delete question associated with subtopic
		}
		this.subTopicRepo.delete(subTopic); // and here we delete subtopic
		return Constants.SUBTOPIC_DELETED_SUCCESSFULLY;
	}
	
	/*
	 * delete question
	 */
	@Override
	public String deleteQuestion(Long id) {
		Questions question = this.questionRepo.findQuestionById(id); //here we get question by questionId
		this.questionRepo.delete(question); // and here delete the question
		return Constants.QUESTION_DELETED_SUCCESSFULLY;
	}

	/*
	 * get subtopic by id
	 */
	@Override
	public SubTopic getSubTopicBySubTopicId(Long id) {
		return this.subTopicRepo.findSubTopicById(id);
	}

	/*
	 * get question by id
	 */
	@Override
	public Questions getQuestionsByQuestionId(Long id) {
		return this.questionRepo.findQuestionById(id);
	}

	/*
	 * update subtopic
	 */
	@Override
	public SubTopic updateSubTopic(Long id, SubTopicDTO subTopicDTO) {
		SubTopic subTopic = this.subTopicRepo.findSubTopicById(id);
		subTopic.setSubTopicName(subTopicDTO.getSubTopicName());
		return this.subTopicRepo.save(subTopic);
	}

	/*
	 * update question
	 */
	@Override
	public Questions updateQuestion(Long id, QuestionDTO questionDTO) {
		Questions question = this.questionRepo.findQuestionById(id);
		question.setQuestion(questionDTO.getQuestion());
		return this.questionRepo.save(question);
	}

	/*
	 * solve question
	 */
	@Override
	public QuestionsStatus solveQuestion(Long id, QuestionStatusDTO questionStatusDTO) {
		QuestionsStatus questionsStatus = new QuestionsStatus();
		questionsStatus.setQuestionId(id);
		questionsStatus.setUserId(questionStatusDTO.getUserId());
		questionsStatus.setStatus(questionStatusDTO.getStatus());
		return this.questionStatusRepo.save(questionsStatus);
	}

	/*
	 * get status by questionId
	 */
	@Override
	public QuestionsStatus getStatusByQuestionId(Long id) {
		return this.questionStatusRepo.findStatusByQuestionId(id);
	}

	@Override
	public Map<String, Object> getWholeCourseByCourseId(Long courseId) {
		Course course = this.courseRepo.findCourseByCourseId(courseId); // here we get course by courseId
		List<Topics> topics_list = this.topicRepo.findTopicByCourseId(courseId); // here we get topic list by courseId
		List<SubTopic> subTopics_list = new ArrayList<SubTopic>();
		List<Questions> questions_list = new ArrayList<Questions>();
		for(Topics topic : topics_list) {
			subTopics_list.addAll(this.subTopicRepo.findSubTopicByTopicId(topic.getId()));
		}
		for(SubTopic subTopic : subTopics_list) {
			questions_list.addAll(this.questionRepo.findQuestionsBySubTopicId(subTopic.getId())); 
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("course", course);
		data.put("topics", topics_list);
		data.put("subTopics", subTopics_list);
		data.put("questions", questions_list);
		return data;
	}

}
