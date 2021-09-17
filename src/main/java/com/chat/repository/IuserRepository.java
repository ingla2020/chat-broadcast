package com.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.model.User;

public interface IuserRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

}
