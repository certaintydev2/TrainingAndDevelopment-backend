package com.example.usermicroservice.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermicroservice.config.CustomUserDetailsService;
import com.example.usermicroservice.config.JwtUtil;
import com.example.usermicroservice.dto.RoleDTO;
import com.example.usermicroservice.dto.UserDTO;
import com.example.usermicroservice.entity.AssignMentor;
import com.example.usermicroservice.entity.AuthenticationResponse;
import com.example.usermicroservice.entity.Profile;
import com.example.usermicroservice.entity.RoleModel;
import com.example.usermicroservice.entity.TestQuestions;
import com.example.usermicroservice.entity.UserData;
import com.example.usermicroservice.helper.Course;
import com.example.usermicroservice.helper.QuestionStatus;
import com.example.usermicroservice.helper.Questions;
import com.example.usermicroservice.helper.SubTopic;
import com.example.usermicroservice.helper.Topics;
import com.example.usermicroservice.payload.ChangePasswordPayload;
import com.example.usermicroservice.payload.EmailPayload;
import com.example.usermicroservice.payload.ForgotPasswordPayload;
import com.example.usermicroservice.payload.LoginPayload;
import com.example.usermicroservice.payload.OtpPayload;
import com.example.usermicroservice.repository.RoleRepository;
import com.example.usermicroservice.repository.UserRepository;
import com.example.usermicroservice.service.UserService;
import com.example.usermicroservice.util.Constants;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	/*
	 * api to get all users from database
	 */

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserData>> getAllUsers() {
		try {
			List<UserData> users = this.userService.getAllUsers();
			return new ResponseEntity<List<UserData>>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserData>>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/authenticate/addProfile")
	public ResponseEntity<Profile> addProfile(@RequestBody Profile profile) {
		try {
			Profile profileData = this.userService.addProfile(profile);
			return new ResponseEntity<Profile>(profileData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Profile>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api to get all users except admin
	 */

	@GetMapping("/getAllUsersExceptAdmin")
	public ResponseEntity<List<UserData>> getAllUsersExceptAdmin() {
		try {
			List<UserData> users = this.userService.getAllUsersExceptAdmin();
			return new ResponseEntity<List<UserData>>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserData>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAllUsersExceptTraineeAndAdmin")
	public ResponseEntity<List<UserData>> getAllUsersExceptTrainee() {
		try {
			List<UserData> users = this.userService.getAllUsersExceptTrainee();
			return new ResponseEntity<List<UserData>>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserData>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api to get User By Username
	 */

	@GetMapping("/authenticate/getUserByUserName/{userName}")
	public ResponseEntity<UserData> getUserByUserName(@PathVariable("userName") String userName) {
		try {
			UserData user = this.userService.getUserByUserName(userName);
			return new ResponseEntity<UserData>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserData>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api to get User By Id
	 */

	@GetMapping("/getUserById/{id}")
	public ResponseEntity<UserData> getUserById(@PathVariable("id") Long id) {
		try {
			UserData user = this.userService.getUserById(id);
			return new ResponseEntity<UserData>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserData>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/authenticate/add-userData")
	public ResponseEntity<UserData> addUserData(@RequestBody UserDTO userDTO) {
		try {
			UserData user = this.userService.addUserData(userDTO);
			return new ResponseEntity<UserData>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserData>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for delete user by id
	 */

	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<Object>(this.userService.deleteUser(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(Constants.USER_CANNOT_DELETE, HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api for update user by id
	 */

	@PutMapping("/updateUser/{id}")
	public ResponseEntity<UserData> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userData) {
		try {
			UserData user = this.userService.updateUser(id, userData);
			return new ResponseEntity<UserData>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserData>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api to get all roles
	 */

	@GetMapping("/authenticate/getAllRoles")
	public ResponseEntity<List<RoleModel>> getAllRoles() {
		try {
			List<RoleModel> roleList = this.userService.getAllRoles();
			return new ResponseEntity<List<RoleModel>>(roleList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<RoleModel>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api for add roles
	 */

	@PostMapping("/authenticate/add-roles")
	public ResponseEntity<RoleModel> addRoles(@RequestBody RoleDTO roles) {
		try {
			RoleModel role = this.userService.addRoles(roles);
			return new ResponseEntity<RoleModel>(role, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api to get all user in List of string form
	 */

	@GetMapping("/getUser")
	public ResponseEntity<List<String>> getUser() {
		try {
			List<String> users = this.userService.getUser();
			return new ResponseEntity<List<String>>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getUserProfile")
	public ResponseEntity<List<String>> getUserProfile() {
		try {
			List<String> users = this.userService.getUserProfile();
			return new ResponseEntity<List<String>>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api for add course
	 */

	@PostMapping("/add-course")
	public ResponseEntity<Course> addCourse(@RequestBody Course course) {
		try {
			Course courseData = this.userService.addCourse(course);
			if (courseData.getCourseId() != null) {
				return new ResponseEntity<Course>(courseData, HttpStatus.OK);
			} else {
				return new ResponseEntity<Course>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Course>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for add topics
	 */

	@PostMapping("/add-topics")
	public ResponseEntity<Topics> addTopics(@RequestBody Topics topics) {
		Topics topic = null;
		try {
			topic = this.userService.addTopics(topics);
			if (topic.getId() != null) {
				return new ResponseEntity<Topics>(topic, HttpStatus.OK);
			} else {
				return new ResponseEntity<Topics>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Topics>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for add subTopics
	 */

	@PostMapping("/add-sub_topics")
	public ResponseEntity<SubTopic> addSubTopics(@RequestBody SubTopic subTopic) {
		SubTopic subTopicData = null;
		try {
			subTopicData = this.userService.addSubTopics(subTopic);
			if (subTopicData.getId() != null) {
				return new ResponseEntity<SubTopic>(subTopicData, HttpStatus.OK);
			} else {
				return new ResponseEntity<SubTopic>(HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<SubTopic>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for add questions
	 */

	@PostMapping("/add-questions")
	public ResponseEntity<Questions> addQuestions(@RequestBody Questions questions) {
		Questions question = null;
		try {
			question = this.userService.addQuestions(questions);
			if (question.getId() != null) {
				return new ResponseEntity<Questions>(question, HttpStatus.OK);
			} else {
				return new ResponseEntity<Questions>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Questions>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api to get all courses
	 */

	@GetMapping("/authenticate/getCourseNames")
	public ResponseEntity<List<Course>> getCourseNames() {
		try {
			List<Course> courseList = this.userService.getCourseNames();
			return new ResponseEntity<List<Course>>(courseList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Course>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api to get all topics
	 */

	@GetMapping("/getTopics")
	public ResponseEntity<List<Topics>> getTopics() {
		try {
			List<Topics> topicList = this.userService.getTopics();
			return new ResponseEntity<List<Topics>>(topicList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Topics>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api to get all subtopics
	 */

	@GetMapping("/getSubTopics")
	public ResponseEntity<List<SubTopic>> getSubTopics() {
		try {
			List<SubTopic> subTopicList = this.userService.getSubTopics();
			return new ResponseEntity<List<SubTopic>>(subTopicList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<SubTopic>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api to get all questions
	 */

	@GetMapping("/getQuestions")
	public ResponseEntity<List<Questions>> getQuestions() {
		try {
			List<Questions> questionList = this.userService.getQuestions();
			return new ResponseEntity<List<Questions>>(questionList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Questions>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api to get topics by courseId
	 */

	@GetMapping("/getTopicsById/{courseId}")
	public ResponseEntity<List<Topics>> getTopicByCourseId(@PathVariable("courseId") Long courseId) {
		try {
			List<Topics> topicList = this.userService.getTopicByCourseId(courseId);
			return new ResponseEntity<List<Topics>>(topicList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Topics>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api to get subTopics by topicId
	 */

	@GetMapping("/getSubTopicsById/{topicId}")
	public ResponseEntity<List<SubTopic>> getSubTopicByTopicId(@PathVariable("topicId") Long topicId) {
		try {
			List<SubTopic> subTopicList = this.userService.getSubTopicByTopicId(topicId);
			return new ResponseEntity<List<SubTopic>>(subTopicList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<SubTopic>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api to get Questions by subTopicId
	 */

	@GetMapping("/getQuestionsById/{subTopicId}")
	public ResponseEntity<List<Questions>> getQuestionsBySubTopicId(@PathVariable("subTopicId") Long subTopicId) {
		try {
			List<Questions> questionList = this.userService.getQuestionsBySubTopicId(subTopicId);
			return new ResponseEntity<List<Questions>>(questionList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Questions>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api for send email
	 */

	@PostMapping("/sendEmail")
	public ResponseEntity<Object> sendEmail(@RequestBody EmailPayload emailPayload) {
		try {
			return new ResponseEntity<Object>(this.userService.sendEmail(emailPayload), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_GATEWAY);
		}
	}

	/*
	 * api for send otp
	 */

	@PostMapping("/authenticate/send-otp")
	public ResponseEntity<Object> sendOtp(@RequestBody OtpPayload otpPayload) {
		try {
			Integer otp = this.userService.sendOtp(otpPayload);
			return new ResponseEntity<Object>(otp, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_GATEWAY);
		}
	}

	/*
	 * api for forgot password
	 */

	@PostMapping("/authenticate/forgotPassword")
	public ResponseEntity<Object> forgotPassword(@RequestBody ForgotPasswordPayload forgotPasswordPayload) {
		try {
			String passwordData = this.userService.forgotPassword(forgotPasswordPayload);
			return new ResponseEntity<Object>(passwordData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/changePassword/{id}")
	public ResponseEntity<Object> changePassword(@PathVariable("id") Long id,
			@RequestBody ChangePasswordPayload changePasswordPayload) {
		String passwordData = this.userService.changePassword(id, changePasswordPayload);
		return new ResponseEntity<Object>(passwordData, HttpStatus.OK);
	}
	/*
	 * 
	 ******************** LOGIN API **************************
	 * 
	 */

	@PostMapping("/authenticate")
	public ResponseEntity<Object> createAuthenticationToken(@RequestBody LoginPayload loginPayload) throws Exception {
		UserData user = null;
		try {
			user = this.userRepo.getUserByUserName(loginPayload.getUserName());
			if (passwordEncoder.matches(loginPayload.getPassword(), user.getPassword())) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(loginPayload.getUserName());
				String token = jwtUtil.generateToken(userDetails);
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginPayload.getUserName(),
						loginPayload.getPassword()));
				return new ResponseEntity<Object>(new AuthenticationResponse(token), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(Constants.PASSWORD_INVALID, HttpStatus.BAD_REQUEST);
			}

		} catch (DisabledException e) {
			return new ResponseEntity<Object>("User Disabled", HttpStatus.CONFLICT);
		} catch (BadCredentialsException e) {
			return new ResponseEntity<Object>("Invalid Credentials", HttpStatus.CONFLICT);
		} catch (NullPointerException e) {
			return new ResponseEntity<Object>(Constants.USER_INVALID, HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api to get questions by courseName
	 */

	@GetMapping("/getQuestionsByCourse/{course}")
	public ResponseEntity<List<Questions>> getQuestionsByCourse(@PathVariable("course") String course) {
		try {
			List<Questions> questionList = this.userService.getQuestionsByCourse(course);
			return new ResponseEntity<List<Questions>>(questionList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Questions>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api for update course by courseId
	 */

	@PutMapping("/updateCourse/{courseId}")
	public ResponseEntity<Course> updateCourse(@PathVariable("courseId") Long courseId, @RequestBody Course course) {
		try {
			Course courseData = this.userService.updateCourse(courseId, course);
			return new ResponseEntity<Course>(courseData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Course>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api to get course bt courseId
	 */

	@GetMapping("/getCourseByCourseId/{courseId}")
	public ResponseEntity<Course> getCourseByCourseId(@PathVariable("courseId") Long courseId) {
		try {
			Course courseData = this.userService.getCourseByCourseId(courseId);
			return new ResponseEntity<Course>(courseData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Course>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for update Topic by id
	 */

	@PutMapping("/updateTopic/{id}")
	public ResponseEntity<Topics> updateTopic(@PathVariable("id") Long id, @RequestBody Topics topic) {
		try {
			Topics topicData = this.userService.updateTopic(id, topic);
			return new ResponseEntity<Topics>(topicData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Topics>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api to get topic by topicId
	 */

	@GetMapping("/getTopicByTopicId/{id}")
	public ResponseEntity<Topics> getTopicByTopicId(@PathVariable("id") Long id) {
		try {
			Topics topicData = this.userService.getTopicByTopicId(id);
			return new ResponseEntity<Topics>(topicData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Topics>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for delete course by courseId
	 */

	@DeleteMapping("/deleteCourse/{courseId}")
	public ResponseEntity<String> deleteCourse(@PathVariable("courseId") Long courseId) {
		try {
			String message = this.userService.deleteCourse(courseId);
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for delete topic by topicId
	 */

	@DeleteMapping("/deleteTopic/{id}")
	public ResponseEntity<String> deleteTopic(@PathVariable("id") Long id) {
		try {
			String message = this.userService.deleteTopic(id);
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for delete subTopic by subTopicId
	 */

	@DeleteMapping("/deleteSubTopic/{id}")
	public ResponseEntity<String> deleteSubTopic(@PathVariable("id") Long id) {
		try {
			String message = this.userService.deleteSubTopic(id);
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for delete question by questionId
	 */

	@DeleteMapping("/deleteQuestion/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable("id") Long id) {
		try {
			String message = this.userService.deleteQuestion(id);
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api to get subTopic by subTopicId
	 */

	@GetMapping("/getSubTopicBySubTopicId/{id}")
	public ResponseEntity<SubTopic> getSubTopicBySubTopicId(@PathVariable("id") Long id) {
		try {
			SubTopic subTopicData = this.userService.getSubTopicBySubTopicId(id);
			return new ResponseEntity<SubTopic>(subTopicData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<SubTopic>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api to get questions by questionId
	 */

	@GetMapping("/getQuestionsByQuestionId/{id}")
	public ResponseEntity<Questions> getQuestionsByQuestionId(@PathVariable("id") Long id) {
		try {
			Questions question = this.userService.getQuestionsByQuestionId(id);
			return new ResponseEntity<Questions>(question, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Questions>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for udpate subTopic by subTopicId
	 */

	@PutMapping("/updateSubTopic/{id}")
	public ResponseEntity<SubTopic> updateSubTopic(@PathVariable("id") Long id, @RequestBody SubTopic subTopic) {
		try {
			SubTopic subTopicData = this.userService.updateSubTopic(id, subTopic);
			return new ResponseEntity<SubTopic>(subTopicData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<SubTopic>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for update question by questionId
	 */

	@PutMapping("/updateQuestion/{id}")
	public ResponseEntity<Questions> updateQuestion(@PathVariable("id") Long id, @RequestBody Questions question) {
		try {
			Questions questionData = this.userService.updateQuestion(id, question);
			return new ResponseEntity<Questions>(questionData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Questions>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api for solve question by questionId
	 */

	@PostMapping("/solveQuestion/{id}")
	public ResponseEntity<QuestionStatus> solveQuestion(@PathVariable("id") Long id,
			@RequestBody QuestionStatus questionStatus) {
		try {
			QuestionStatus questionData = this.userService.solveQuestion(id, questionStatus);
			return new ResponseEntity<QuestionStatus>(questionData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<QuestionStatus>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api to get status by questionId
	 */

	@GetMapping("/getStatusByQuestionId/{id}")
	public ResponseEntity<QuestionStatus> getStatusByQuestionId(@PathVariable("id") Long id) {
		try {
			QuestionStatus questionData = this.userService.getStatusByQuestionId(id);
			return new ResponseEntity<QuestionStatus>(questionData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<QuestionStatus>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * api to get author list
	 */

	@GetMapping("/getAuthorList")
	public ResponseEntity<List<UserData>> getAuthorList() {
		try {
			List<UserData> authorList = this.userService.getAuthorList();
			return new ResponseEntity<List<UserData>>(authorList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserData>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api to get mentor list
	 */

	@GetMapping("/getMentorList")
	public ResponseEntity<List<UserData>> getMentorList() {
		try {
			List<UserData> mentorList = this.userService.getMentorList();
			return new ResponseEntity<List<UserData>>(mentorList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserData>>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * api to get trainee list
	 */

	@GetMapping("/getTraineeList")
	public ResponseEntity<List<UserData>> getTraineeList() {
		try {
			List<UserData> traineeList = this.userService.getTraineeList();
			return new ResponseEntity<List<UserData>>(traineeList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserData>>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/assignMentorToTrainee")
	public ResponseEntity<AssignMentor> assignMentor(@RequestBody AssignMentor assignMentor) {
		try {
			AssignMentor assignMentorData = this.userService.assignMentor(assignMentor);
			return new ResponseEntity<AssignMentor>(assignMentorData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<AssignMentor>(HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/authenticate/getAllProfile")
	public ResponseEntity<List<Profile>> getAllProfiles() {
		try {
			List<Profile> profileList = this.userService.getAllProfiles();
			return new ResponseEntity<List<Profile>>(profileList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Profile>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getCourseByCourseName/{courseName}")
	public ResponseEntity<Course> getCourseByCourseName(@PathVariable("courseName") String courseName) {
		try {
			Course courseData = this.userService.getCourseByCourseName(courseName);
			return new ResponseEntity<Course>(courseData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getWholeCourseByCourseId/{courseId}")
	public Map<String, Object> getWholeCourseByCourseId(@PathVariable("courseId") Long courseId) {
		return this.userService.getWholeCourseByCourseId(courseId);
	}

	@PostMapping("/addTestQuestions")
	public ResponseEntity<TestQuestions> addTestQuestions(@RequestBody TestQuestions testQuestions) {
		try {
			TestQuestions testQuestionsData = this.userService.addTestQuestions(testQuestions);
			return new ResponseEntity<TestQuestions>(testQuestionsData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<TestQuestions>(HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/getTestByProfile/{profile}")
	public ResponseEntity<List<TestQuestions>> getQuestionsByProfile(@PathVariable("profile") String profile) {
		try {
			List<TestQuestions> questionsList = this.userService.getQuestionsByProfile(profile);
			return new ResponseEntity<List<TestQuestions>>(questionsList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<TestQuestions>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAssignedMentorList")
	public ResponseEntity<List<AssignMentor>> getAssignedMentorList(){
		try {
			List<AssignMentor> assignMentorList = this.userService.getAssignedMentorList();
			return new ResponseEntity<List<AssignMentor>>(assignMentorList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<AssignMentor>>(HttpStatus.NOT_FOUND);
		}
	}

}
