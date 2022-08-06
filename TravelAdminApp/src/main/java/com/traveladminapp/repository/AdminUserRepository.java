package com.traveladminapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traveladminapp.models.AdminUser;


@Repository
public interface AdminUserRepository extends  JpaRepository<AdminUser, Integer> {
	
	
	AdminUser findByNameAndPassword(String name, String password);

}
