package com.example.usermicroservice.dto;

import java.util.List;
import java.util.Set;

import com.example.usermicroservice.entity.Profile;
import com.example.usermicroservice.entity.RoleModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {

	private String name;
	private String email;
	private String userName;
	private String password;
	private List<Profile> profile;
	private List<RoleModel> roles;
}
