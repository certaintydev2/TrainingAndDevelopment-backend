package com.example.usermicroservice.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefrenceLinks {
	
	private Long id;
	private String link;
	private SubTopic subTopic;
}
