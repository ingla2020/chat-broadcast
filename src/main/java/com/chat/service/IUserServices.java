package com.chat.service;

import java.util.List;

import com.chat.model.User;

public interface IUserServices {

	User addUserByAdmin(User user);
	
	List<User> findAllUser();
	
}
