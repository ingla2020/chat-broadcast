package com.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.model.User;
import com.chat.service.IUserServices;

@RestController
@RequestMapping("/secure/rest")
public class AdminController {

	@Autowired
	public IUserServices userservices;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/add")
	public User addUserByAdmin(@RequestBody User user) {
		return userservices.addUserByAdmin(user);
	}
	
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/admin/test")
	public String test() {
		return "paso prueba";
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	//@PreAuthorize("hasRole('USER')") 
	@GetMapping("/user/all")
	public List<User> allUser() {
		return userservices.findAllUser();
	}
	
}
