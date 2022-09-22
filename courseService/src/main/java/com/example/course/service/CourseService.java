package com.example.course.service;

import java.util.List;
import java.util.Map;

import com.example.course.dto.CourseDTO;
import com.example.course.dto.QuestionDTO;
import com.example.course.dto.QuestionStatusDTO;
import com.example.course.dto.SubTopicDTO;
import com.example.course.dto.TopicDTO;
import com.example.course.entity.Course;
import com.example.course.entity.OnlineAssessmentLinks;
import com.example.course.entity.Questions;
import com.example.course.entity.QuestionsStatus;
import com.example.course.entity.RefrenceLinks;
import com.example.course.entity.SubTopic;
import com.example.course.entity.Topics;

public interface CourseService {
	
	public Course addCourse(CourseDTO course);
	
	public Topics addTopics(TopicDTO topics);
	
	public SubTopic addSubTopics(SubTopicDTO subTopic);
	
	public Questions addQuestions(QuestionDTO questions);
	
	public List<Course> getCourse();
	
	public List<Topics> getTopics();
	
	public List<SubTopic> getSubTopics();
	
	public List<Questions> getQuestions();
	
	public List<Topics> getTopicByCourseId(Long courseId);
	
	public List<SubTopic> getSubTopicByTopicId(Long topicId);
	
	public List<Questions> getQuestionsBySubTopicId(Long subTopicId);
	
	public Course getCourseByCourseName(String course);

	public Course updateCourse(Long courseId , CourseDTO courseDTO);
	
	public Course getCourseByCourseId(Long courseId);
	
	public Topics updateTopic(Long id,TopicDTO topicDTO);
	
	public Topics getTopicByTopicId(Long id);
	
	public String deleteCourse(Long courseId);
	
	public String deleteTopic(Long id);
	
	public String deleteQuestion(Long id);
	
	public Questions getQuestionsByQuestionId(Long id);
	
	public SubTopic updateSubTopic(Long id,SubTopicDTO subTopicDTO);
	
	public Questions updateQuestion(Long id , QuestionDTO questionDTO);
	
	public String deleteSubTopic(Long id);
	
	public SubTopic getSubTopicBySubTopicId(Long id);
	
	public QuestionsStatus solveQuestion(Long id , QuestionStatusDTO questionStatusDTO);
	
	public QuestionsStatus getStatusByQuestionId(Long id);
	
	public Map<String, Object> getWholeCourseByCourseId(Long courseId);
	
	public RefrenceLinks addRefrenceLinks(RefrenceLinks refrenceLinks);
	
	public List<RefrenceLinks> getLinksBySubTopicId(Long subTopicId);
	
	public OnlineAssessmentLinks addOnlineAssessmentLinks(OnlineAssessmentLinks onlineAssessmentLinks);
	
	public List<OnlineAssessmentLinks> getAssessmentLinksBySubTopicId(Long subTopicId);
}
