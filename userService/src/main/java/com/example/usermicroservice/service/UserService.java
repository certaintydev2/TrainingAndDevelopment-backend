package com.example.usermicroservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;

import com.example.usermicroservice.dto.RoleDTO;
import com.example.usermicroservice.dto.UserDTO;
import com.example.usermicroservice.entity.AssignMentor;
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
import com.example.usermicroservice.payload.CheckPasswordPayload;
import com.example.usermicroservice.payload.EmailPayload;
import com.example.usermicroservice.payload.ForgotPasswordPayload;
import com.example.usermicroservice.payload.OtpPayload;

public interface UserService {
	
	public List<UserData> getAllUsers();
	
	public List<UserData> getAllUsersExceptAdmin();
	
	public List<UserData> getAllUsersExceptTrainee();
	
	public UserData getUserByUserName(String userName);
	
	public UserData getUserById(Long id);
	
	public List<RoleModel> getAllRoles();
	
	public UserData addUserData(UserDTO user);
	
	public Profile addProfile(Profile profile);
	
	public String deleteUser(Long id);
	
	public List<String> getUser();
	
	public List<String> getUserProfile();
	
	public UserData updateUser(Long id , UserDTO user);
	
	public RoleModel addRoles(RoleDTO roles);
	
	public Course addCourse(Course course);
	
	public Topics addTopics(Topics topics);
	
	public SubTopic addSubTopics(SubTopic subTopic);
	
	public Questions addQuestions(Questions questions);
	
	public List<Course> getCourseNames();
	
	public List<Topics> getTopics();
	
	public List<SubTopic> getSubTopics();
	
	public List<Questions> getQuestions();
	
	public List<Topics> getTopicByCourseId(Long courseId);
	
	public List<SubTopic> getSubTopicByTopicId(Long topicId);
	
	public List<Questions> getQuestionsBySubTopicId(Long subTopicId);
	
	public String sendEmail(EmailPayload emailPayload);
	
	public Integer sendOtp(OtpPayload otpPayload);
	
	public String forgotPassword(ForgotPasswordPayload forgotPasswordPayload);
	
	public String changePassword(Long id , ChangePasswordPayload changePasswordPayload);
	
	public List<Questions> getQuestionsByCourse(String course);
	
	public Course updateCourse(Long courseId, Course course);
	
	public Course getCourseByCourseId(Long courseId);
	
	public Topics updateTopic(Long id, Topics topic);
	
	public Topics getTopicByTopicId(Long id);
	
	public String deleteCourse(Long courseId);
	
	public String deleteTopic(Long id);
	
	public String deleteQuestion(Long id);
	
	public String deleteSubTopic(Long id);
	
	public SubTopic getSubTopicBySubTopicId(Long id);
	
	public Questions getQuestionsByQuestionId(Long id);
	
	public SubTopic updateSubTopic(Long id, SubTopic subTopic);
	
	public Questions updateQuestion(Long id, Questions question);
	
	public QuestionStatus solveQuestion(Long id,QuestionStatus questionStatus);
	
	public QuestionStatus getStatusByQuestionId(Long id);
	
	public List<UserData> getAuthorList();
	
	public List<UserData> getMentorList();
	
	public List<UserData> getTraineeList();
	
	public AssignMentor assignMentor(AssignMentor assignMentor);
	
	public List<Profile> getAllProfiles();
	
	public Course getCourseByCourseName(String courseName);
	
	public Map<String, Object> getWholeCourseByCourseId(Long courseId);
	
	public TestQuestions addTestQuestions(TestQuestions testQuestions);
	
	public List<TestQuestions> getQuestionsByProfile(String profile);
}
