package com.traveluserprofile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveluserprofile.models.Email;
import com.traveluserprofile.models.UserMailInbox;
import com.traveluserprofile.repository.UserMailInboxRepository;

@Service
public class UserMailInboxService {
	
	@Autowired
	private UserMailInboxRepository userMailInboxRepository;
	
	
	public List<Email> findMailboxByEmail(String email) {
		return userMailInboxRepository.findByEmail(email).get().getReceivedEmails();
	}

}
