package com.travelmainapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelmainapp.helper.UserHelper;
import com.travelmainapp.models.Ticket;
import com.travelmainapp.models.User;
import com.travelmainapp.service.UserService;

@RestController
@RequestMapping(value = "/mainuserpage")
public class UserController {// localhost:8080/mainuserpage
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping
	@RequestMapping(value="/createuser")
	public User createUser(@RequestBody User user) {// Kullanıcı yarat.
		return userService.createUser(user);
		
	}
	
	@GetMapping
	@RequestMapping(value="/login")
	public User login(@RequestBody UserHelper helper) {// login işlemi
		return userService.login(helper.getEmail(), helper.getPassword());
		
	}
	
	@GetMapping
	@RequestMapping(value="/usertickets")
	public List<Ticket> userTickets(@RequestBody UserHelper helper) {// Kullanıcıya ait biletler.
		return userService.userTickets(helper.getEmail(), helper.getPassword());
		
	}
	
	@PostMapping
	@RequestMapping(value="/changepayment")
	public User changePaymentPlan(@RequestBody UserHelper helper) {// Ödeme yöntemini değiştir.
		return userService.setPaymentMethod(helper.getEmail(), helper.getPassword(), helper.getPaymentMethod());
	}

}
