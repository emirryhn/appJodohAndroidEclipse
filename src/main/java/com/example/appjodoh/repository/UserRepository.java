package com.example.appjodoh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.appjodoh.entity.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{
	
	UserModel getUserByUsername(String username);
	
	@Query(value = "SELECT * FROM user_table WHERE gender = ?1", nativeQuery = true)
	List<UserModel> getUserByGender(String gender);

}
