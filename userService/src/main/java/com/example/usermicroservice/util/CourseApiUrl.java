package com.example.usermicroservice.util;

public class CourseApiUrl {
	
	
	private CourseApiUrl() {
		throw new IllegalStateException("Constants utility class");
	}
	
	/*
	*
		********** COURSE-SERVICE API *************
	*
	*/
	

	public static final String ADD_COURSE_API_ENDPOINT = "http://COURSE-SERVICE//courses/add-course";
	public static final String ADD_TOPICS_API_ENDPOINT = "http://COURSE-SERVICE//courses/add-topics";
	public static final String ADD_SUB_TOPICS_API_ENDPOINT = "http://COURSE-SERVICE//courses/add-sub_topics";
	public static final String ADD_QUESTIONS_API_ENDPOINT = "http://COURSE-SERVICE//courses/add-questions";
	public static final String COURSES_LIST_API_ENDPOINT = "http://COURSE-SERVICE//courses/getCourseNames";
	public static final String TOPICS_LIST_API_ENDPOINT = "http://COURSE-SERVICE//courses/getTopics";
	public static final String SUB_TOPICS_LIST_API_ENDPOINT = "http://COURSE-SERVICE//courses/getSubTopics";
	public static final String QUESTIONS_LIST_API_ENDPOINT = "http://COURSE-SERVICE//courses/getQuestions";
	public static final String TOPICS_BY_COURSE_ID_API_ENDPOINT = "http://COURSE-SERVICE//courses/getTopicsById/%d";
	public static final String SUB_TOPICS_BY_TOPIC_ID_API_ENDPOINT = "http://COURSE-SERVICE//courses/getSubTopicsById/%d";
	public static final String QUESTIONS_BY_SUB_TOPIC_ID_API_ENDPOINT = "http://COURSE-SERVICE//courses/getQuestionsById/%d";
	public static final String COURSE_BY_COURSE_NAME_API_ENDPOINT = "http://COURSE-SERVICE//courses/getCourseByCourseName/%s";
	public static final String UPDATE_COURSE_API_ENDPOINT = "http://COURSE-SERVICE//courses/updateCourse/%d";
	public static final String COURSE_BY_COURSE_ID_API_ENDPOINT = "http://COURSE-SERVICE//courses/getCourseByCourseId/%d";
	public static final String UPDATE_TOPIC_API_ENDPOINT = "http://COURSE-SERVICE//courses/updateTopic/%d";
	public static final String TOPIC_BY_TOPIC_ID_API_ENDPOINT = "http://COURSE-SERVICE//courses/getTopicByTopicId/%d";
	public static final String DELETE_COURSE_API_ENDPOINT = "http://COURSE-SERVICE//courses/deleteCourse/%d";
	public static final String DELETE_TOPIC_API_ENDPOINT = "http://COURSE-SERVICE//courses/deleteTopic/%d";
	public static final String DELETE_SUB_TOPIC_API_ENDPOINT = "http://COURSE-SERVICE//courses/deleteSubTopic/%d";
	public static final String DELETE_QUESTION_API_ENDPOINT = "http://COURSE-SERVICE//courses/deleteQuestion/%d";
	public static final String SUB_TOPIC_BY_SUB_TOPIC_ID_API_ENDPOINT = "http://COURSE-SERVICE//courses/getSubTopicBySubTopicId/%d";
	public static final String QUESTION_BY_QUESTION_ID_API_ENDPOINT = "http://COURSE-SERVICE//courses/getQuestionsByQuestionId/%d";
	public static final String UPDATE_SUB_TOPIC_API_ENDPOINT = "http://COURSE-SERVICE//courses/updateSubTopic/%d";
	public static final String UPDATE_QUESTION_API_ENDPOINT = "http://COURSE-SERVICE//courses/updateQuestion/%d";
}
