package com.example.course.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserData {

	private Long id;
	private String name;
	private String email;
	private String userName;
	private String password;
	private String profile;
	private RoleModel roles;

}
