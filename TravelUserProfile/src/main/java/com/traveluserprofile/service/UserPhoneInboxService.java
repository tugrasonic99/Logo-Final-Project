package com.traveluserprofile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveluserprofile.models.Message;
import com.traveluserprofile.models.UserPhoneInbox;
import com.traveluserprofile.repository.UserPhoneInboxRepository;

@Service
public class UserPhoneInboxService {

	
	@Autowired
	private UserPhoneInboxRepository userPhoneInboxRepository;
	
	public List<Message> findPhoneInboxByPhoneNumber(String phoneNumber) {
		return userPhoneInboxRepository.findByPhoneNumber(phoneNumber).get().getReceivedMessages();
	}
	
}
