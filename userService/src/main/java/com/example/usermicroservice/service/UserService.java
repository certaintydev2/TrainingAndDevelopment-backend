package com.example.usermicroservice.service;

import java.util.List;

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

public interface UserService {
	
	public List<UserData> getAllUsers();
	
	public List<UserData> getAllUsersExceptAdmin();
	
	public UserData getUserByUserName(String userName);
	
	public UserData getUserById(Long id);
	
	public List<RoleModel> getAllRoles();
	
	public UserData addUserData(UserDTO user);
	
	public String deleteUser(Long id);
	
	public List<String> getUser();
	
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
	
	public String changePassword(ForgotPasswordPayload forgotPasswordPayload);
	
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
}
