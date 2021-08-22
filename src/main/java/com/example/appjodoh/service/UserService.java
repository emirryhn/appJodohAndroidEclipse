package com.example.appjodoh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.appjodoh.entity.UserModel;
import com.example.appjodoh.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	public UserModel getUserByUsername(String username) {
		return userRepo.getUserByUsername(username);
	}
	
	public UserModel saveUser(UserModel user) {
		return userRepo.save(user);
		
	}
	
	public List<UserModel> getUserByGender(String gender) {
		return this.userRepo.getUserByGender(gender);
		
	}

}
