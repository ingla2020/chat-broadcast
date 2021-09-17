package com.chat.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.model.User;
import com.chat.repository.IuserRepository;

@Service
public class UserServices implements IUserServices{

	@Autowired
	public IuserRepository usuariorepo;

	@Autowired
	private BCryptPasswordEncoder encodePWD;
	
	@Override
	public User addUserByAdmin(User user) {

		user.setTransactionId(UUID.randomUUID().toString());
		
		String pwd = user.getPassword();
		String encryptPwd = encodePWD.encode(pwd);
		
		user.setPassword(encryptPwd);
		usuariorepo.save(user);
		return user;
		
	}

	@Override
	public List<User> findAllUser() {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		String username = userDetails.getUsername();		
		
		return usuariorepo.findAll()
				.stream()
				.filter(user -> !user.getUsername().equals(username))
				.collect(Collectors.toList());
	}

}
