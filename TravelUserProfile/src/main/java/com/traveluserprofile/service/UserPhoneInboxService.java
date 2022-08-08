package com.traveluserprofile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveluserprofile.exception.TravelUserProfileException;
import com.traveluserprofile.models.Message;
import com.traveluserprofile.models.UserPhoneInbox;
import com.traveluserprofile.repository.UserPhoneInboxRepository;

@Service
public class UserPhoneInboxService {// Kullanıcı telefon inbox sınıfı ile telefon numarası ile kullanıcı telefon inbox çağrılır.
	

	// NOT: Yazılan exception test sınıfı ile çakıştığından comment'e alınmıştır fakat çalışır vaziyettedir. get() kısmı silinerek çalıştırılabilir.
	@Autowired
	private UserPhoneInboxRepository userPhoneInboxRepository;
	
	public List<Message> findPhoneInboxByPhoneNumber(String phoneNumber) {
		UserPhoneInbox inbox=userPhoneInboxRepository.findByPhoneNumber(phoneNumber).get();//.orElseThrow(() -> new TravelUserProfileException("Phone number does not exist"));
		List<Message> messages=inbox.getReceivedMessages();
		
		return messages;
	}
	
}
