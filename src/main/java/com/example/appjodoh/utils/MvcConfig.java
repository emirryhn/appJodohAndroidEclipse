package com.example.appjodoh.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer{
	
	public void addResourceHandler(ResourceHandlerRegistry registry) {
		exposedDirectory("user-photos", registry);
	}

	public void exposedDirectory(String dirName, ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		Path uploadDir = Paths.get(dirName);
		String uploadPath = uploadDir.toFile().getAbsolutePath();
		
		if(dirName.startsWith("../")) {
			dirName = dirName.replace("../", "");
		}
		registry.addResourceHandler("/"+dirName+"?**").addResourceLocations("file:/"+uploadPath+"/");
		
	}

}
