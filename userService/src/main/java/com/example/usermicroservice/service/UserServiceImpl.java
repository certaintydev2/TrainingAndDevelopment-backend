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
	
	@Override
	public List<UserData> getAllUsers() {
		return this.userRepo.findAll();
	}
	

	@Override
	public UserData getUserByUserName(String userName) {
		return this.userRepo.getUserByUserName(userName);
	}


	@Override
	public UserData getUserById(Long id) {
		return this.userRepo.getUserById(id);
	}

	
	@Override
	public UserData addUserData(UserDTO user) {
		UserData userData = new UserData();
		userData.setName(user.getName());
		userData.setEmail(user.getEmail());
		userData.setUserName(user.getUserName());
		userData.setProfile(user.getProfile());
		List<RoleModel> roles = roleRepo.findAll();
		List<RoleModel> userRoles = new ArrayList<>();
		for (RoleModel role_user : user.getRoles()) {
			for (RoleModel role : roles) {
				if (role.getId().equals(role_user.getId())) {
					userRoles.add(role);
					userData.setRoles(userRoles);
					userData.setPassword(passwordEncoder.encode(user.getPassword()));
				}
			}
		}
		return this.userRepo.save(userData);
	}

	@Override
	public RoleModel addRoles(RoleDTO roles) {
		RoleModel role = new RoleModel();
		role.setRoleName(roles.getRoleName());
		return this.roleRepo.save(role);
	}

	@Override	
	public Course addCourse(Course course) {
		Course courses = this.restTemplate.postForObject(CourseApiUrl.ADD_COURSE_API_ENDPOINT, course,
				Course.class);
		course.setCourseId(courses.getCourseId());
		course.setCourseName(courses.getCourseName());
		course.setAuthorId(courses.getAuthorId());
		course.setMentorId(courses.getMentorId());
		return course;
	}

	@Override
	public Topics addTopics(Topics topics) {
		Topics topicsData = this.restTemplate.postForObject(CourseApiUrl.ADD_TOPICS_API_ENDPOINT, topics,
				Topics.class);
		topics.setId(topicsData.getId());
		topics.setTopicName(topicsData.getTopicName());
		topics.setCourse(topicsData.getCourse());
		return topics;
	}

	@Override
	public SubTopic addSubTopics(SubTopic subTopic) {
		SubTopic subTopicData = this.restTemplate.postForObject(CourseApiUrl.ADD_SUB_TOPICS_API_ENDPOINT,
				subTopic, SubTopic.class);

		subTopic.setId(subTopicData.getId());
		subTopic.setSubTopicName(subTopicData.getSubTopicName());
		subTopic.setTopic(subTopicData.getTopic());
		return subTopic;
	}

	@Override
	public Questions addQuestions(Questions questions) {
		Questions questionData = this.restTemplate.postForObject(CourseApiUrl.ADD_QUESTIONS_API_ENDPOINT,
				questions, Questions.class);
		questions.setId(questionData.getId());
		questions.setQuestion(questionData.getQuestion());
		questions.setSubTopic(questionData.getSubTopic());
		return questions;
	}

	@Override
	public List<Course> getCourseNames() {
		List<Course> course = new ArrayList<Course>();
		ResponseEntity<List<Course>> claimResponse = restTemplate.exchange(
				CourseApiUrl.COURSES_LIST_API_ENDPOINT, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Course>>() {
				});
		if (claimResponse != null && claimResponse.hasBody()) {
			course = claimResponse.getBody();
		}
		return course;
	}

	@Override
	public List<Topics> getTopics() {
		List<Topics> topic = new ArrayList<Topics>();
		ResponseEntity<List<Topics>> claimResponse = restTemplate.exchange(
				CourseApiUrl.TOPICS_LIST_API_ENDPOINT, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Topics>>() {
				});
		if (claimResponse != null && claimResponse.hasBody()) {
			topic = claimResponse.getBody();
		}
		return topic;
	}

	@Override
	public List<SubTopic> getSubTopics() {
		List<SubTopic> subTopic = new ArrayList<SubTopic>();
		ResponseEntity<List<SubTopic>> claimResponse = restTemplate.exchange(
				CourseApiUrl.SUB_TOPICS_LIST_API_ENDPOINT, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<SubTopic>>() {
				});
		if (claimResponse != null && claimResponse.hasBody()) {
			subTopic = claimResponse.getBody();
		}
		return subTopic;
	}

	@Override
	public List<Questions> getQuestions() {
		List<Questions> question = new ArrayList<Questions>();
		ResponseEntity<List<Questions>> claimResponse = restTemplate.exchange(
				CourseApiUrl.QUESTIONS_LIST_API_ENDPOINT, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Questions>>() {
				});
		if (claimResponse != null && claimResponse.hasBody()) {
			question = claimResponse.getBody();
		}
		return question;
	}

	@Override
	public List<Topics> getTopicByCourseId(Long courseId) {
		List<Topics> topic = new ArrayList<Topics>();
		ResponseEntity<List<Topics>> claimResponse = restTemplate.exchange(
				String.format(CourseApiUrl.TOPICS_BY_COURSE_ID_API_ENDPOINT, courseId), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Topics>>() {
				});
		if (claimResponse != null && claimResponse.hasBody()) {
			topic = claimResponse.getBody();
		}
		return topic;
	}

	@Override
	public List<SubTopic> getSubTopicByTopicId(Long topicId) {
		List<SubTopic> subTopic = new ArrayList<SubTopic>();
		ResponseEntity<List<SubTopic>> claimResponse = restTemplate.exchange(
				String.format(CourseApiUrl.SUB_TOPICS_BY_TOPIC_ID_API_ENDPOINT, topicId), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<SubTopic>>() {
				});
		if (claimResponse != null && claimResponse.hasBody()) {
			subTopic = claimResponse.getBody();
		}
		return subTopic;
	}

	@Override
	public List<Questions> getQuestionsBySubTopicId(Long subTopicId) {
		List<Questions> question = new ArrayList<Questions>();
		ResponseEntity<List<Questions>> claimResponse = restTemplate.exchange(
				String.format(CourseApiUrl.QUESTIONS_BY_SUB_TOPIC_ID_API_ENDPOINT, subTopicId), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Questions>>() {
				});
		if (claimResponse != null && claimResponse.hasBody()) {
			question = claimResponse.getBody();
		}
		return question;
	}

	@Override
	public String deleteUser(Long id) {
		UserData user = this.userRepo.getUserById(id);
		this.userRepo.delete(user);
		return Constants.USER_DELETED_SUCCESSFULLY;
	}

	@Override
	public UserData updateUser(Long id, UserDTO user) {
		UserData userData = this.userRepo.getUserById(id);
		userData.setName(user.getName());
		userData.setProfile(user.getProfile());
		userData.setRoles(user.getRoles());
		return this.userRepo.save(userData);
	}

	@Override
	public String sendEmail(EmailPayload emailPayload) {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(emailPayload.getEmail());
		simpleMailMessage.setSubject(emailPayload.getSubject());
		simpleMailMessage.setText(emailPayload.getText());
		
		javaMailSender.send(simpleMailMessage);
		
		return Constants.MAIL_SEND_MESSAGE;
	}

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

	@Override
	public String changePassword(ForgotPasswordPayload forgotPasswordPayload) {
		
		UserData user = this.userRepo.getUserByEmail(forgotPasswordPayload.getEmail());
		user.setPassword(passwordEncoder.encode(forgotPasswordPayload.getNewPassword()));
		this.userRepo.save(user);
		return Constants.PASSWORD_CHANGED_SUCCESSFULLY;
	}


	@Override
	public List<RoleModel> getAllRoles() { 
		return this.roleRepo.findAll();
	}


	@Override
	public List<String> getUser() {
		return this.userRepo.getUserData();
	}


	@Override
	public List<Questions> getQuestionsByCourse(String course) {
		List<Questions> question = new ArrayList<Questions>();
		ResponseEntity<List<Questions>> claimResponse = restTemplate.exchange(
				CourseApiUrl.QUESTIONS_LIST_API_ENDPOINT, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Questions>>() {
				});
		if (claimResponse != null && claimResponse.hasBody()) {
			question = claimResponse.getBody();
		}
		Course courseData = new Course();
		ResponseEntity<Course> claimResponse1 = restTemplate.exchange(
				String.format(CourseApiUrl.COURSE_BY_COURSE_NAME_API_ENDPOINT, course), HttpMethod.GET, null,
				new ParameterizedTypeReference<Course>() {
				});
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


	@Override
	public Course updateCourse(Long courseId, Course course) {
		Course courseData = new Course();
		ResponseEntity<Course> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.UPDATE_COURSE_API_ENDPOINT, courseId),
                HttpMethod.PUT,
                new HttpEntity<>(course),
                Course.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			courseData = claimResponse.getBody();
		}
		course.setCourseId(courseId);
		course.setCourseName(courseData.getCourseName());
		course.setAuthorId(courseData.getAuthorId());
		course.setMentorId(courseData.getMentorId());
		return course;
	}


	@Override
	public Course getCourseByCourseId(Long courseId) {
		Course courseData = new Course();
		ResponseEntity<Course> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.COURSE_BY_COURSE_ID_API_ENDPOINT, courseId),
                HttpMethod.GET,
                null,
                Course.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			courseData = claimResponse.getBody();
		}
		return courseData;
	}


	@Override
	public Topics updateTopic(Long id, Topics topic) {
		Topics topicData = new Topics();
		ResponseEntity<Topics> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.UPDATE_TOPIC_API_ENDPOINT, id),
                HttpMethod.PUT,
                new HttpEntity<>(topic),
                Topics.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			topicData = claimResponse.getBody();
		}
		topic.setId(id);
		topic.setTopicName(topicData.getTopicName());
		return topic;
	}


	@Override
	public Topics getTopicByTopicId(Long id) {
		Topics topicData = new Topics();
		ResponseEntity<Topics> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.TOPIC_BY_TOPIC_ID_API_ENDPOINT, id),
                HttpMethod.GET,
                null,
                Topics.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			topicData = claimResponse.getBody();
		}
		return topicData;
	}


	@Override
	public String deleteCourse(Long courseId) {
		String message = "";
		ResponseEntity<String> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.DELETE_COURSE_API_ENDPOINT, courseId),
                HttpMethod.DELETE,
                null,
                String.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			message = claimResponse.getBody();
		}
		return message;
	}


	@Override
	public String deleteTopic(Long id) {
		String message = "";
		ResponseEntity<String> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.DELETE_TOPIC_API_ENDPOINT, id),
                HttpMethod.DELETE,
                null,
                String.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			message = claimResponse.getBody();
		}
		return message;
	}


	@Override
	public String deleteQuestion(Long id) {
		String message = "";
		ResponseEntity<String> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.DELETE_QUESTION_API_ENDPOINT, id),
                HttpMethod.DELETE,
                null,
                String.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			message = claimResponse.getBody();
		}
		return message;
	}


	@Override
	public String deleteSubTopic(Long id) {
		String message = "";
		ResponseEntity<String> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.DELETE_SUB_TOPIC_API_ENDPOINT, id),
                HttpMethod.DELETE,
                null,
                String.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			message = claimResponse.getBody();
		}
		return message;
	}


	@Override
	public SubTopic getSubTopicBySubTopicId(Long id) {
		SubTopic subTopicData = new SubTopic();
		ResponseEntity<SubTopic> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.SUB_TOPIC_BY_SUB_TOPIC_ID_API_ENDPOINT, id),
                HttpMethod.GET,
                null,
                SubTopic.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			subTopicData = claimResponse.getBody();
		}
		return subTopicData;
	}


	@Override
	public Questions getQuestionsByQuestionId(Long id) {
		Questions question = new Questions();
		ResponseEntity<Questions> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.QUESTION_BY_QUESTION_ID_API_ENDPOINT, id),
                HttpMethod.GET,
                null,
                Questions.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			question = claimResponse.getBody();
		}
		return question;
	}


	@Override
	public SubTopic updateSubTopic(Long id, SubTopic subTopic) {
		SubTopic subTopicData = new SubTopic();
		ResponseEntity<SubTopic> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.UPDATE_SUB_TOPIC_API_ENDPOINT, id),
                HttpMethod.PUT,
                new HttpEntity<>(subTopic),
                SubTopic.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			subTopicData = claimResponse.getBody();
		}
		subTopic.setId(id);
		subTopic.setSubTopicName(subTopicData.getSubTopicName());
		return subTopic;
	}


	@Override
	public Questions updateQuestion(Long id, Questions question) {
		Questions questionData = new Questions();
		ResponseEntity<Questions> claimResponse= restTemplate.exchange(String.format(CourseApiUrl.UPDATE_QUESTION_API_ENDPOINT, id),
                HttpMethod.PUT,
                new HttpEntity<>(question),
                Questions.class);
		if (claimResponse != null && claimResponse.hasBody()) {
			questionData = claimResponse.getBody();
		}
		question.setId(id);
		question.setQuestion(questionData.getQuestion());
		return question;
	}


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
}
