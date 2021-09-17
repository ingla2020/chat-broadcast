package com.chat.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
@RequestMapping("/rest/auth/")
public class ChatController {

	/*
	@GetMapping("/process")
	public String process() {
		return "procesado . ....";
	}
	*/
	
//	 @Value("${spring.application.name}")
//	    String appName;
	
	@PreAuthorize("hasAnyRole('USER')")
	@GetMapping(value = "/process")
	public String process() {
		//Model model
	//	model.addAttribute("appName", appName);
		return "index";
	}
	
	  @RequestMapping(value = "/username", method = RequestMethod.GET)
	  @ResponseBody
	  public String currentUserName(Authentication authentication) {
	     return authentication.getName();
	  }

}
