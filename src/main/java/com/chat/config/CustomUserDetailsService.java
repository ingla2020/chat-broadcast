package com.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.chat.model.User;
import com.chat.repository.IuserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private IuserRepository userrepository;
	
	@Autowired
	private CustomUserDetails userDetails;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userrepository.findByUsername(username);
		//CustomUserDetails userDetails = null;
		if (user!=null) {
			//userDetails = new CustomUserDetails();
			userDetails.setUser(user);
		}else {
			throw new UsernameNotFoundException("user not exists with name: " + username);
		}
		return userDetails;
		

	}

}
