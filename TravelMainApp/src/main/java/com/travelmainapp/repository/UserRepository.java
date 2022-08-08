package com.travelmainapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelmainapp.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	
	User findByEmailAndPassword(String email, String password);
	
	

}
