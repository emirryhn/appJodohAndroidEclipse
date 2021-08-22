package com.example.appjodoh.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.appjodoh.config.JwtTokenUtil;
import com.example.appjodoh.entity.UserModel;
import com.example.appjodoh.service.MyUserDetail;
import com.example.appjodoh.service.UserService;
import com.example.appjodoh.utils.FileUtility;
import com.google.gson.Gson;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetail muDetail;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService uService;
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserModel user) throws Exception{
		authenticate(user.getUsername(), user.getPassword());
		
		final UserDetails userDetails = muDetail.loadUserByUsername(user.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails, uService.getUserByUsername(user.getUsername()).getGender());
		
		return ResponseEntity.ok(token);
	}
	
	@PostMapping("/register")
	public String registerUser(@RequestParam(value = "file") MultipartFile images, 
			@ModelAttribute(value="data") String dataJSON) throws IOException {
		String fileName = StringUtils.cleanPath(images.getOriginalFilename());
		
		String uploadDir = "src/main/java/user-photos/";
		FileUtility.saveFile(uploadDir, fileName, images);
		UserModel user = new Gson().fromJson(dataJSON, UserModel.class);
		
		BCryptPasswordEncoder passEconde = new BCryptPasswordEncoder();
		user.setPassword(passEconde.encode(user.getPassword()));
		
		user.setPicture(fileName);
		this.uService.saveUser(user);
		return "User added";
		
	}
	
	@GetMapping("/user/{gender}")
	public List<UserModel> getByGender(@PathVariable String gender){
		return uService.getUserByGender(gender);
	}
	
	@GetMapping(value="/user/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImageWithMediaType(@PathVariable String name) throws IOException {
		final InputStream in = getClass().getResourceAsStream("/user-photos/"+name);
		return IOUtils.toByteArray(in);
	}
	

	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
	}catch(DisabledException e) {
		throw new Exception("USER_DISABLED",e);
	}catch(BadCredentialsException e) {
		throw new Exception("INVALID_CREDENTIAL",e);
	}
		
	}

}
