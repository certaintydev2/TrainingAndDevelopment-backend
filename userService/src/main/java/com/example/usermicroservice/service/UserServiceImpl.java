package com.example.usermicroservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.usermicroservice.dto.RoleDTO;
import com.example.usermicroservice.dto.UserDTO;
import com.example.usermicroservice.entity.RoleModel;
import com.example.usermicroservice.entity.UserData;
import com.example.usermicroservice.helper.Course;
import com.example.usermicroservice.helper.QuestionStatus;
import com.example.usermicroservice.helper.Questions;
import com.example.usermicroservice.helper.SubTopic;
import com.example.usermicroservice.helper.Topics;
import com.example.usermicroservice.payload.EmailPayload;
import com.example.usermicroservice.payload.ForgotPasswordPayload;
import com.example.usermicroservice.payload.OtpPayload;
import com.example.usermicroservice.repository.RoleRepository;
import com.example.usermicroservice.repository.UserRepository;
import com.example.usermicroservice.util.Constants;
import com.example.usermicroservice.util.CourseApiUrl;
@Service
public class UserServiceImpl implements UserService {
	
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	Random random = new Random(1000);	
	
	
	/*
	 * get all users from database
	 */
	@Override
	public List<UserData> getAllUsers() {
		return this.userRepo.findAll();
	}
	

	/*
	 * get user by username
	 */
	@Override
	public UserData getUserByUserName(String userName) {
		return this.userRepo.getUserByUserName(userName);
	}

	/*
	 * get user by id
	 */
	@Override
	public UserData getUserById(Long id) {
		return this.userRepo.getUserById(id);
	}

	/*
	 * add user to database
	 */
	@Override
	public UserData addUserData(UserDTO user) {
		UserData userData = new UserData();
		userData.setName(user.getName());
		userData.setEmail(user.getEmail());
		userData.setUserName(user.getUserName());
		userData.setProfile(user.getProfile());
		List<RoleModel> roles = roleRepo.findAll(); //  all roles from database
		List<RoleModel> userRoles = new ArrayList<>();
		for (RoleModel role_user : user.getRoles()) { // iterate roles of user
			for (RoleModel role : roles) { //  iterate rolelist comes from database
				if (role.getId().equals(role_user.getId())) { // check that the user role id and role id of role list is equal
					userRoles.add(role);
					userData.setRoles(userRoles); // set the role of user
					userData.setPassword(passwordEncoder.encode(user.getPassword())); // encode the password
				}
			}
		}
		return this.userRepo.save(userData); // user save to database
	}

	/*
	 * add role to database
	 */
	@Override
	public RoleModel addRoles(RoleDTO roles) {
		RoleModel role = new RoleModel();
		role.setRoleName(roles.getRoleName());
		return this.roleRepo.save(role);
	}

	/*
	 * add course to database
	 */
	@Override	
	public Course addCourse(Course course) {
		Course courses = this.restTemplate.postForObject(CourseApiUrl.ADD_COURSE_API_ENDPOINT, course,
				Course.class); // call add course api of course service by resttemplate
		course.setCourseId(courses.getCourseId());
		course.setCourseName(courses.getCourseName());
		course.setAuthorId(courses.getAuthorId());
		course.setMentorId(courses.getMentorId());
		return course;
	}

	/*
	 * add topic to database
	 */
	@Override
	public Topics addTopics(Topics topics) {
		Topics topicsData = this.restTemplate.postForObject(CourseApiUrl.ADD_TOPICS_API_ENDPOINT, topics,
				Topics.class);// call add topic api of course service by resttemplate
		topics.setId(topicsData.getId());
		topics.setTopicName(topicsData.getTopicName());
		topics.setCourse(topicsData.getCourse());
		return topics;
	}

	/*
	 * add subtopic to database
	 */
	@Override
	public SubTopic addSubTopics(SubTopic subTopic) {
		SubTopic subTopicData = this.restTemplate.postForObject(CourseApiUrl.ADD_SUB_TOPICS_API_ENDPOINT,
				subTopic, SubTopic.class);// call add subtopic api of course service by resttemplate

		subTopic.setId(subTopicData.getId());
		subTopic.setSubTopicName(subTopicData.getSubTopicName());
		subTopic.setTopic(subTopicData.getTopic());
		return subTopic;
	}

	/*
	 * add question to database
	 */
	@Override
	public Questions addQuestions(Questions questions) {
		Questions questionData = this.restTemplate.postForObject(CourseApiUrl.ADD_QUESTIONS_API_ENDPOINT,
				questions, Questions.class);// call add question api of course service by resttemplate
		questions.setId(questionData.getId());
		questions.setQuestion(questionData.getQuestion());
		questions.setSubTopic(questionData.getSubTopic());
		return questions;
	}

	/*
	 * get all courses from database
	 */
	@Override
	public List<Course> getCourseNames() {
		List<Course> course = new ArrayList<Course>();
		ResponseEntity<List<Course>> claimResponse = restTemplate.exchange(
				CourseApiUrl.COURSES_LIST_API_ENDPOINT, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Course>>() {
				}); // call get all course api of course service by resttemplate
		if (claimResponse != null && claimResponse.hasBody()) {
			course = claimResponse.getBody();
		}
		return course;
	}

	/*
	 * get all topics from database
	 */
	@Override
	public List<Topics> getTopics() {
		List<Topics> topic = new ArrayList<Topics>();
		ResponseEntity<List<Topics>> claimResponse = restTemplate.exchange(
				CourseApiUrl.TOPICS_LIST_API_ENDPOINT, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Topics>>() {
				}); // call get all topic api of course service by resttemplate
		if (claimResponse != null && claimResponse.hasBody()) {
			topic = claimResponse.getBody();
		}
		return topic;
	}

	/*
	 * get all subtopics from database
	 */
	@Override
	public List<SubTopic> getSubTopics() {
		List<SubTopic> subTopic = new ArrayList<SubTopic>();
		ResponseEntity<List<SubTopic>> claimResponse = restTemplate.exchange(
				CourseApiUrl.SUB_TOPICS_LIST_API_ENDPOINT, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<SubTopic>>() {
				}); // call get all subtopics api of course service by resttemplate
		if (claimResponse != null && claimResponse.hasBody()) {
			subTopic = claimResponse.getBody();
		}
		return subTopic;
	}

	/*
	 * get all questions from database
	 */
	@Override
	public List<Questions> getQuestions() {
		List<Questions> question = new ArrayList<Questions>();
		ResponseEntity<List<Questions>> claimResponse = restTemplate.exchange(
				CourseApiUrl.QUESTIONS_LIST_API_ENDPOINT, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Questions>>() {
				}); // call get all questions api of course service by resttemplate
		if (claimResponse != null && claimResponse.hasBody()) {
			question = claimResponse.getBody();
		}
		return question;
	}

	/*
	 * get topics by courseId
	 */
	@Override
	public List<Topics> getTopicByCourseId(Long courseId) {
		List<Topics> topic = new ArrayList<Topics>();
		ResponseEntity<List<Topics>> claimResponse = restTemplate.exchange(
				String.format(CourseApiUrl.TOPICS_BY_COURSE_ID_API_ENDPOINT, courseId), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Topics>>() {
				}); //call get topics by courseId api of course service by resttemplate
		if (claimResponse != null && claimResponse.hasBody()) {
			topic = claimResponse.getBody();
		}
		return topic;
	}

	/*
	 * get subtopic by topicId
	 */
	@Override
	public List<SubTopic> getSubTopicByTopicId(Long topicId) {
		List<SubTopic> subTopic = new ArrayList<SubTopic>();
		ResponseEntity<List<SubTopic>> claimResponse = restTemplate.exchange(
				String.format(CourseApiUrl.SUB_TOPICS_BY_TOPIC_ID_API_ENDPOINT, topicId), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<SubTopic>>() {
				});//call get subtopics by topicId api of course service by resttemplate
		if (claimResponse != null && claimResponse.hasBody()) {
			subTopic = claimResponse.getBody();
		}
		return subTopic;
	}

	/*
	 * get question by subtopicId
	 */
	@Override
	public List<Questions> getQuestionsBySubTopicId(Long subTopicId) {
		List<Questions> question = new ArrayList<Questions>();
		ResponseEntity<List<Questions>> claimResponse = restTemplate.exchange(
				String.format(CourseApiUrl.QUESTIONS_BY_SUB_TOPIC_ID_API_ENDPOINT, subTopicId), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Questions>>() {
				});//call get questions by subtopicId api of course service by resttemplate
		if (claimResponse != null && claimResponse.hasBody()) {
			question = claimResponse.getBody();
		}
		return question;
	}

	/*
	 * delete user 
	 */
	@Override
	public String deleteUser(Long id) {
		UserData user = this.userRepo.getUserById(id);
		this.userRepo.delete(user);
		return Constants.USER_DELETED_SUCCESSFULLY;
	}

	/*
	 * update user by id
	 */
	@Override
	public UserData updateUser(Long id, UserDTO user) {
		UserData userData = this.userRepo.getUserById(id); // get user by id
		userData.setName(user.getName());
		userData.setProfile(user.getProfile());
		userData.setRoles(user.getRoles());
		return this.userRepo.save(userData);
	}

	/*
	 * send email
	 */
	@Override
	public String sendEmail(EmailPayload emailPayload) {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(emailPayload.getEmail());
		simpleMailMessage.setSubject(emailPayload.getSubject());
		simpleMailMessage.setText(emailPayload.getText());
		
		javaMailSender.send(simpleMailMessage);
		
		return Constants.MAIL_SEND_MESSAGE;
	}

	/* 
	 * send otp
	 */
	@Override
	public Integer sendOtp(OtpPayload otpPayload) {
		EmailPayload mail = new EmailPayload();
		int otp = random.nextInt(999999);

		String subject = "OTP from Training And Developement";
		String message = "OTP = "+otp;
		mail.setEmail(otpPayload.getEmail());
		mail.setSubject(subject);
		mail.setText(message);
		this.sendEmail(mail);
		return otp;
	}

	/*
	 * change password api
	 */
	@Override
	public String changePassword(ForgotPasswordPayload forgotPasswordPayload) {
		
		UserData user = this.userRepo.getUserByEmail(forgotPasswordPayload.getEmail());
		user.setPassword(passwordEncoder.encode(forgotPasswordPayload.getNewPassword()));
		this.userRepo.save(user);
		return Constants.PASSWORD_CHANGED_SUCCESSFULLY;
	}

	/*
	 * get all roles from database
	 */
	@Override
	public List<RoleModel> getAllRoles() { 
		return this.roleRepo.findAll();
	}

	/*
	 * get All Users
	 */
	@Override
	public List<String> getUser() {
		return this.userRepo.getUserData();
	}

	/*
	 * get questions by course
	 */
	@Override
	public List<Questions> getQuestionsByCourse(String course) {
		List<Questions> question = new ArrayList<Questions>();
		ResponseEntity<List<Questions>> claimResponse = restTemplate.exchange(
				CourseApiUrl.QUESTIONS_LIST_API_ENDPOINT, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Questions>>() {
				}); // call get all questions api from course service by resttemplate
		if (claimResponse != null && claimResponse.hasBody()) {
			question = claimResponse.getBody();
		}
		Course courseData = new Course();
		ResponseEntity<Course> claimResponse1 = restTemplate.exchange(
				String.format(CourseApiUrl.COURSE_BY_COURSE_NAME_API_ENDPOINT, course), HttpMethod.GET, null,
				new ParameterizedTypeReference<Course>() {
				});// call get course by coursename  api from course service by resttemplate
		if (claimResponse1 != null && claimResponse1.hasBody()) {
			courseData = claimResponse1.getBody();
		}
		List<Questions> questionList = new ArrayList<Questions>();
		for(Questions q : question) {
			String courseName = q.getSubTopic().getTopic().getCourse().getCourseName();
			if(courseName.equalsIgnoreCase(courseData.getCourseName())) {
				questionList.add(q);
			}
		}
		return questionList;
	}

	/*
	 * update course
	 */
	@Override
	public Course updateCourse(Long courseId, Course course) {
		Course courseData = new Course();
		ResponseEntity<Course> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.UPDATE_COURSE_API_ENDPOINT, courseId),
                HttpMethod.PUT,
                new HttpEntity<>(course),
                Course.class); // call update course api from course service by resttemplate
		if (claimResponse != null && claimResponse.hasBody()) {
			courseData = claimResponse.getBody();
		}
		course.setCourseId(courseId);
		course.setCourseName(courseData.getCourseName());
		course.setAuthorId(courseData.getAuthorId());
		course.setMentorId(courseData.getMentorId());
		return course;
	}

	/*
	 * get course by courseId
	 */
	@Override
	public Course getCourseByCourseId(Long courseId) {
		Course courseData = new Course();
		ResponseEntity<Course> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.COURSE_BY_COURSE_ID_API_ENDPOINT, courseId),
                HttpMethod.GET,
                null,
                Course.class); // call get course by courseId from course service
		if (claimResponse != null && claimResponse.hasBody()) {
			courseData = claimResponse.getBody();
		}
		return courseData;
	}

	/*
	 * update topic
	 */
	@Override
	public Topics updateTopic(Long id, Topics topic) {
		Topics topicData = new Topics();
		ResponseEntity<Topics> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.UPDATE_TOPIC_API_ENDPOINT, id),
                HttpMethod.PUT,
                new HttpEntity<>(topic),
                Topics.class); // call update topic api from course service
		if (claimResponse != null && claimResponse.hasBody()) {
			topicData = claimResponse.getBody();
		}
		topic.setId(id);
		topic.setTopicName(topicData.getTopicName());
		return topic;
	}

	/*
	 * get topic by topicId
	 */
	@Override
	public Topics getTopicByTopicId(Long id) {
		Topics topicData = new Topics();
		ResponseEntity<Topics> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.TOPIC_BY_TOPIC_ID_API_ENDPOINT, id),
                HttpMethod.GET,
                null,
                Topics.class);// call get topic by topicId api from course service
		if (claimResponse != null && claimResponse.hasBody()) {
			topicData = claimResponse.getBody();
		}
		return topicData;
	}

	/*
	 * delete course 
	 */
	@Override
	public String deleteCourse(Long courseId) {
		String message = "";
		ResponseEntity<String> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.DELETE_COURSE_API_ENDPOINT, courseId),
                HttpMethod.DELETE,
                null,
                String.class);// call delete course api from course service 
		if (claimResponse != null && claimResponse.hasBody()) {
			message = claimResponse.getBody();
		}
		return message;
	}

	/*
	 * delete topic
	 */
	@Override
	public String deleteTopic(Long id) {
		String message = "";
		ResponseEntity<String> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.DELETE_TOPIC_API_ENDPOINT, id),
                HttpMethod.DELETE,
                null,
                String.class); // call delete topic api from course service 
		if (claimResponse != null && claimResponse.hasBody()) {
			message = claimResponse.getBody();
		}
		return message;
	}

	/*
	 * delete question 
	 */
	@Override
	public String deleteQuestion(Long id) {
		String message = "";
		ResponseEntity<String> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.DELETE_QUESTION_API_ENDPOINT, id),
                HttpMethod.DELETE,
                null,
                String.class);// call delete question api from course service 
		if (claimResponse != null && claimResponse.hasBody()) {
			message = claimResponse.getBody();
		}
		return message;
	}

	/*
	 * delete question
	 */
	@Override
	public String deleteSubTopic(Long id) {
		String message = "";
		ResponseEntity<String> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.DELETE_SUB_TOPIC_API_ENDPOINT, id),
                HttpMethod.DELETE,
                null,
                String.class);// call delete subtopic api from course service 
		if (claimResponse != null && claimResponse.hasBody()) {
			message = claimResponse.getBody();
		}
		return message;
	}

	/*
	 * get subtopic by subTopicId
	 */
	@Override
	public SubTopic getSubTopicBySubTopicId(Long id) {
		SubTopic subTopicData = new SubTopic();
		ResponseEntity<SubTopic> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.SUB_TOPIC_BY_SUB_TOPIC_ID_API_ENDPOINT, id),
                HttpMethod.GET,
                null,
                SubTopic.class);// call get subTopic by subTopicId
		if (claimResponse != null && claimResponse.hasBody()) {
			subTopicData = claimResponse.getBody();
		}
		return subTopicData;
	}

	/*
	 * get question by questionId
	 */
	@Override
	public Questions getQuestionsByQuestionId(Long id) {
		Questions question = new Questions();
		ResponseEntity<Questions> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.QUESTION_BY_QUESTION_ID_API_ENDPOINT, id),
                HttpMethod.GET,
                null,
                Questions.class);// call get question by questionId
		if (claimResponse != null && claimResponse.hasBody()) {
			question = claimResponse.getBody();
		}
		return question;
	}

	/*
	 * update subtopic
	 */
	@Override
	public SubTopic updateSubTopic(Long id, SubTopic subTopic) {
		SubTopic subTopicData = new SubTopic();
		ResponseEntity<SubTopic> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.UPDATE_SUB_TOPIC_API_ENDPOINT, id),
                HttpMethod.PUT,
                new HttpEntity<>(subTopic),
                SubTopic.class); // call update subtopic api from course service
		if (claimResponse != null && claimResponse.hasBody()) {
			subTopicData = claimResponse.getBody();
		}
		subTopic.setId(id);
		subTopic.setSubTopicName(subTopicData.getSubTopicName());
		return subTopic;
	}

	/*
	 * update question
	 */
	@Override
	public Questions updateQuestion(Long id, Questions question) {
		Questions questionData = new Questions();
		ResponseEntity<Questions> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.UPDATE_QUESTION_API_ENDPOINT, id),
                HttpMethod.PUT,
                new HttpEntity<>(question),
                Questions.class);// call update subtopic api from course service
		if (claimResponse != null && claimResponse.hasBody()) {
			questionData = claimResponse.getBody();
		}
		question.setId(id);
		question.setQuestion(questionData.getQuestion());
		return question;
	}

	/*
	 * get all user except admin
	 */
	@Override
	public List<UserData> getAllUsersExceptAdmin() {
		List<UserData> userList = this.getAllUsers();
		for(UserData user : this.getAllUsers()) {
			for(RoleModel role : user.getRoles()) {
				if(role.getRoleName().equals(Constants.ROLE_ADMIN)) {
					userList.remove(user);
				}
			}
		}
		return userList;
	}

	/*
	 * solve question
	 */
	@Override
	public QuestionStatus solveQuestion(Long id, QuestionStatus questionStatus) {
		QuestionStatus questionStatusData = new QuestionStatus();
		ResponseEntity<QuestionStatus> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.QUESTION_STATUS_API_ENDPOINT, id),
                HttpMethod.POST,
                new HttpEntity<>(questionStatus),
                QuestionStatus.class);// call solve question api form course service
		if (claimResponse != null && claimResponse.hasBody()) {
			questionStatusData = claimResponse.getBody();
		}
		questionStatus.setId(questionStatusData.getId());
		questionStatus.setQuestionId(questionStatusData.getQuestionId());
		questionStatus.setUserId(questionStatusData.getUserId());
		questionStatus.setStatus(questionStatusData.getStatus());
		return questionStatus;
	}

	/*
	 * get status by questionId
	 */
	@Override
	public QuestionStatus getStatusByQuestionId(Long id) {
		QuestionStatus questionStatus = new QuestionStatus();
		ResponseEntity<QuestionStatus> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.QUESTION_STATUS_BY_QUESTION_ID_API_ENDPOINT, id),
                HttpMethod.GET,
                null,
                QuestionStatus.class);// call get status by questionId from course service
		if (claimResponse != null && claimResponse.hasBody()) {
			questionStatus = claimResponse.getBody();
		}
		return questionStatus;
	}

	// get author list from database
	@Override
	public List<UserData> getAuthorList() {
		List<UserData> authorList = new ArrayList<UserData>();
		for(UserData user : this.getAllUsers()) {
			for(RoleModel role : user.getRoles()) {
				if(role.getRoleName().equals(Constants.ROLE_AUTHOR)) {
					authorList.add(user);
				}
			}
		}
		return authorList;
	}

	// get mentor list from database
	@Override
	public List<UserData> getMentorList() {
		List<UserData> mentorList = new ArrayList<UserData>();
		for(UserData user : this.getAllUsers()) {
			for(RoleModel role : user.getRoles()) {
				if(role.getRoleName().equals(Constants.ROLE_MENTOR)) {
					mentorList.add(user);
				}
			}
		}
		return mentorList;
	}

	// get trainee list from database
	@Override
	public List<UserData> getTraineeList() {
		List<UserData> traineeList = new ArrayList<UserData>();
		for(UserData user : this.getAllUsers()) {
			for(RoleModel role : user.getRoles()) {
				if(role.getRoleName().equals(Constants.ROLE_TRAINEE)) {
					traineeList.add(user);
				}
			}
		}
		return traineeList;
	}
}
