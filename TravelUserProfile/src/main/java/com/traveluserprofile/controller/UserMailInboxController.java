package com.traveluserprofile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traveluserprofile.helper.NotificationHelper;
import com.traveluserprofile.models.Email;
import com.traveluserprofile.service.UserMailInboxService;

@RestController
@RequestMapping(value = "/usermail")
public class UserMailInboxController {// localhost:8080/usermail
	
	@Autowired
	private UserMailInboxService userMailInboxService;
	
	
	@GetMapping
	public List<Email> getMails(@RequestBody NotificationHelper notificationHelper){// Kullanıcı mailleri
		return userMailInboxService.findMailboxByEmail(notificationHelper.getNotificationAddress());
		
	}

}
