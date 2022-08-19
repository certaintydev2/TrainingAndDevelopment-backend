package com.example.course.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.example.course.repository.SubTopicRepository;
import com.example.course.repository.TopicRepository;
import com.example.course.service.CourseService;
import com.example.course.util.Constants;

@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private TopicRepository topicRepo;

	@Autowired
	private SubTopicRepository subTopicRepo;

	@Autowired
	private QuestionRepository questionRepo;

	@PostMapping("/add-course")
	public ResponseEntity<Object> addCourse(@RequestBody CourseDTO course) {
		List<Course> courseList = this.courseRepo.findAll();
		Boolean courseExist=false;
		try {
			if (courseList.isEmpty()) {
				return ResponseEntity.ok(this.courseService.addCourse(course));
			} else {
				for (Course courseData : courseList) {
					courseExist =  courseData.getCourseName().toUpperCase().equals(course.getCourseName().toUpperCase());
					if (Boolean.TRUE.equals(courseExist)) {
						break;
					}
				}
				if (!courseExist) {
					return ResponseEntity.ok(this.courseService.addCourse(course));
				} else {
					return ResponseEntity.ok(new Exception(Constants.COURSE_ALREADY_ADDED));
				}
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new Exception(Constants.COURSE_ALREADY_ADDED));
		}
	}

	@PostMapping("/add-topics")
	public ResponseEntity<Object> addTopics(@RequestBody TopicDTO topics) {
		List<Topics> topicList = this.topicRepo.findAll();
		Boolean topicExist=false;
		try {
			if (topicList.isEmpty()) {
				return ResponseEntity.ok(this.courseService.addTopics(topics));
			} else {
				for (Topics topic : topicList) {
					topicExist = topic.getTopicName().toUpperCase().equals(topics.getTopicName().toUpperCase());
					if (Boolean.TRUE.equals(topicExist)) {
						break;
					}
				}
				if (!topicExist) {
					return ResponseEntity.ok(this.courseService.addTopics(topics));
				} else {
					return ResponseEntity.ok(new Exception(Constants.TOPIC_ALREADY_ADDED));
				}
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new Exception(Constants.TOPIC_ALREADY_ADDED));
		}
	}

	@PostMapping("/add-sub_topics")
	public ResponseEntity<Object> addSubTopics(@RequestBody SubTopicDTO subTopic) {
		List<SubTopic> subTopicList = this.subTopicRepo.findAll();
		Boolean subTopicExist=false;
		try {
			if (subTopicList.isEmpty()) {
				return ResponseEntity.ok(this.courseService.addSubTopics(subTopic));
			} else {
				for (SubTopic subTopicData : subTopicList) {
					subTopicExist = subTopicData.getSubTopicName().toUpperCase().equals(subTopic.getSubTopicName().toUpperCase());
					if (Boolean.TRUE.equals(subTopicExist)) {
						break;
					}
				}
				if (!subTopicExist) {
					return ResponseEntity.ok(this.courseService.addSubTopics(subTopic));
				} else {
					return ResponseEntity.ok(new Exception(Constants.SUBTOPIC_ALREADY_ADDED));
				}
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new Exception(Constants.SUBTOPIC_ALREADY_ADDED));
		}
	}

	@PostMapping("/add-questions")
	public ResponseEntity<Object> addQuestions(@RequestBody QuestionDTO questions) {
		List<Questions> questionList = this.questionRepo.findAll();
		Boolean questionExist=false;
		try {
			if (questionList.isEmpty()) {
				return ResponseEntity.ok(this.courseService.addQuestions(questions));
			} else {
				for (Questions questionData : questionList) {
					questionExist = questionData.getQuestion().toUpperCase().equals(questions.getQuestion().toUpperCase());
					if (Boolean.TRUE.equals(questionExist)) {
						break;
					}
				}
				if (!questionExist) {
					return ResponseEntity.ok(this.courseService.addQuestions(questions));
				} else {
					return ResponseEntity.ok(new Exception(Constants.QUESTION_ALREADY_ADDED));
				}
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new Exception(Constants.QUESTION_ALREADY_ADDED));
		}
	}

	@GetMapping("/getCourseNames")
	public List<Course> getCourse() {
		return this.courseService.getCourse();
	}

	@GetMapping("/getTopics")
	public List<Topics> getTopics() {
		return this.courseService.getTopics();
	}

	@GetMapping("/getSubTopics")
	public List<SubTopic> getSubTopics() {
		return this.courseService.getSubTopics();
	}

	@GetMapping("/getQuestions")
	public List<Questions> getQuestions() {
		return this.courseService.getQuestions();
	}

	@GetMapping("/getTopicsById/{courseId}")
	public List<Topics> getTopicByCourseId(@PathVariable("courseId") Long courseId) {
		return this.courseService.getTopicByCourseId(courseId);
	}

	@GetMapping("/getSubTopicsById/{topicId}")
	public List<SubTopic> getSubTopicByTopicId(@PathVariable("topicId") Long topicId) {
		return this.courseService.getSubTopicByTopicId(topicId);
	}

	@GetMapping("/getQuestionsById/{subTopicId}")
	public List<Questions> getQuestionsBySubTopicId(@PathVariable("subTopicId") Long subTopicId) {
		return this.courseService.getQuestionsBySubTopicId(subTopicId);
	}

	@GetMapping("/getCourseByCourseName/{course}")
	public Course getCourseByCourseName(@PathVariable("course") String course) {
		return this.courseService.getCourseByCourseName(course);
	}

	@PutMapping("/updateCourse/{courseId}")
	public Course updateCourse(@PathVariable("courseId") Long courseId, @RequestBody CourseDTO courseDTO) {
		return this.courseService.updateCourse(courseId, courseDTO);
	}
	
	@GetMapping("/getCourseByCourseId/{courseId}")
	public Course getCourseByCourseId(@PathVariable("courseId") Long courseId) {
		return this.courseService.getCourseByCourseId(courseId);
	}
	
	@DeleteMapping("/deleteCourse/{courseId}")
	public String deleteCourse(@PathVariable("courseId") Long courseId) {
		return this.courseService.deleteCourse(courseId);
	}
	
	@PutMapping("/updateTopic/{id}")
	public Topics updateTopic(@PathVariable("id") Long id, @RequestBody TopicDTO topicDTO) {
		return this.courseService.updateTopic(id, topicDTO);
	}
	
	@GetMapping("/getTopicByTopicId/{id}")
	public Topics getTopicByTopicId(@PathVariable("id") Long id) {
		return this.courseService.getTopicByTopicId(id);
	}
	
	@DeleteMapping("/deleteTopic/{id}")
	public String deleteTopic(@PathVariable("id") Long id) {
		return this.courseService.deleteTopic(id);
	}
	
	@DeleteMapping("/deleteSubTopic/{id}")
	public String deleteSubTopic(@PathVariable("id") Long id) {
		return this.courseService.deleteSubTopic(id);
	}
	
	@DeleteMapping("/deleteQuestion/{id}")
	public String deleteQuestion(@PathVariable("id") Long id) {
		return this.courseService.deleteQuestion(id);
	}
	
	
	@GetMapping("/getSubTopicBySubTopicId/{id}")
	public SubTopic getSubTopicBySubTopicId(@PathVariable("id") Long id) {
		return this.courseService.getSubTopicBySubTopicId(id);
	}
	
	@GetMapping("/getQuestionsByQuestionId/{id}")
	public Questions getQuestionsByQuestionId(@PathVariable("id") Long id) {
		return this.courseService.getQuestionsByQuestionId(id);
	}
	
	@PutMapping("/updateSubTopic/{id}")
	public SubTopic updateSubTopic(@PathVariable("id") Long id, @RequestBody SubTopicDTO subTopicDTO) {
		return this.courseService.updateSubTopic(id, subTopicDTO);
	}
	
	@PutMapping("/updateQuestion/{id}")
	public Questions updateQuestion(@PathVariable("id") Long id, @RequestBody QuestionDTO questionDTO) {
		return this.courseService.updateQuestion(id, questionDTO);
	}
	
	@PostMapping("/solveQuestion/{id}")
	public QuestionsStatus solveQuestion(@PathVariable("id") Long id , @RequestBody QuestionStatusDTO questionStatusDTO) {
		return this.courseService.solveQuestion(id, questionStatusDTO);
	}
	
	
	@GetMapping("/getStatusByQuestionId/{id}")
	public QuestionsStatus getStatusByQuestionId(@PathVariable("id") Long id) {
		return this.courseService.getStatusByQuestionId(id);
	}
}
