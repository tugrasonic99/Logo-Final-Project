package com.traveluserprofile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traveluserprofile.helper.NotificationHelper;
import com.traveluserprofile.models.Message;
import com.traveluserprofile.repository.UserPhoneInboxRepository;
import com.traveluserprofile.service.UserPhoneInboxService;

@RestController
@RequestMapping(value = "/userphone")
public class UserPhoneInboxController {// localhost:8080/userphone
	
	@Autowired
	private UserPhoneInboxService userPhoneInboxService;
	
	
	@GetMapping
	public List<Message> getMessages(@RequestBody NotificationHelper notificationHelper){// Telefon mesajları
		return userPhoneInboxService.findPhoneInboxByPhoneNumber(notificationHelper.getNotificationAddress());
	}
	

}
