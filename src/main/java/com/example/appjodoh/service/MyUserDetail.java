package com.example.appjodoh.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.appjodoh.entity.UserModel;
import com.example.appjodoh.repository.UserRepository;

@Service
public class MyUserDetail implements UserDetailsService{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<SimpleGrantedAuthority> gender = null;
		UserModel user =  userRepo.getUserByUsername(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Username "+username+" cannot be found! ");
		}
		
		gender = Arrays.asList(new SimpleGrantedAuthority(user.getGender()));
		
		return new User(user.getUsername(), user.getPassword(), gender);
	}

}
