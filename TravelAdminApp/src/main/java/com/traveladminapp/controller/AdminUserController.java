package com.traveladminapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traveladminapp.helper.AdminUserHelper;
import com.traveladminapp.models.AdminUser;
import com.traveladminapp.service.AdminUserService;

@RestController
@RequestMapping(value = "/adminuser")
public class AdminUserController {// localhost:8080/adminuser
	
	@Autowired
	private AdminUserService adminUserService;
	
	/*@PostMapping
	@RequestMapping(value = "/init")
	public AdminUser initialize() {
		return adminUserService.initializeUser();
	}*/
	
	@PostMapping
	@RequestMapping(value = "/login")
	public AdminUser login(@RequestBody AdminUserHelper helper) {// Admin girişi
		
		return adminUserService.login(helper.getName(),helper.getPassword());
	}
	
	
	@PostMapping
	@RequestMapping(value = "/register")
	public AdminUser createUser(@RequestBody AdminUserHelper helper) {// Admin kayıt.
		return adminUserService.create(helper.getName(), helper.getPassword(), helper.getEmail());
	}

}
