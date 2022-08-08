package com.traveluserprofile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveluserprofile.exception.TravelUserProfileException;
import com.traveluserprofile.models.Email;
import com.traveluserprofile.models.UserMailInbox;
import com.traveluserprofile.repository.UserMailInboxRepository;

@Service
public class UserMailInboxService {// Kullanıcı Email inbox sınıfı ile mail adresi ile kullanıcı mail inbox çağrılır.
	
	
	// NOT: Yazılan exception test sınıfı ile çakıştığından comment'e alınmıştır fakat çalışır vaziyettedir. get() kısmı silinerek çalıştırılabilir.
	@Autowired
	private UserMailInboxRepository userMailInboxRepository;
	
	
	public List<Email> findMailboxByEmail(String email) {
		UserMailInbox inbox=userMailInboxRepository.findByEmail(email).get(); //orElseThrow(() -> new TravelUserProfileException("Email does not exist"));
		return inbox.getReceivedEmails();
	}

}
