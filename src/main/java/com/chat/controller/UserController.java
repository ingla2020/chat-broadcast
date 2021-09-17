package com.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.model.User;
import com.chat.service.IUserServices;

@RestController
@RequestMapping("/user/rest")
public class UserController {

	@Autowired
	public IUserServices userservices;
	
	
	@PreAuthorize("hasRole('USER')") 
	@GetMapping("/all")
	public List<User> allUser() {
		return userservices.findAllUser();
	}	
	
}
