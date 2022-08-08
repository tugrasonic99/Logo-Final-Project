package com.traveluserprofile.listener;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traveluserprofile.dto.NotificationDTO;
import com.traveluserprofile.models.Email;
import com.traveluserprofile.models.Message;
import com.traveluserprofile.models.UserMailInbox;
import com.traveluserprofile.models.UserPhoneInbox;
import com.traveluserprofile.repository.UserMailInboxRepository;
import com.traveluserprofile.repository.UserPhoneInboxRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationListener {// Bu sınıf TravelMainApp taraflı RabbitMQ'dan gelen mesajları dinlemek adına tasarlandı.
	
	@Autowired
	private UserMailInboxRepository userMailInboxRepository;
	
	@Autowired
	private UserPhoneInboxRepository userPhoneInboxRepository;
	
	@RabbitListener(queues = "travel.notification")
	public void notificationDListener(NotificationDTO notificationDto) throws Exception {// Exception queue'un boş olması durumunda tüm projeyi yakmaması adına koyulmuştur.
		
		
		// Eğer NotificationDTO notificationType Email ise yeni kullanıcı adına bir email hesabı açar ve emaili kayıt eder.
		
		if(notificationDto.getNotificationType().equals("Email")) {
			Email email=new Email();
			email.setReceiver(notificationDto.getReceiver());
			email.setContext(notificationDto.getContext());
			email.setSender(notificationDto.getSender());
			UserMailInbox inbox=new UserMailInbox();
			inbox.setEmail(email.getReceiver());
			List<Email> emails=new ArrayList<Email>();
			emails.add(email);
			inbox.setReceivedEmails(emails);
			userMailInboxRepository.save(inbox);
			log.info("Email saved");
			
		}
		
		
		
		else if(notificationDto.getNotificationType().equals("Message")) {
			Message message=new Message();
			message.setReceiver(notificationDto.getReceiver());
			message.setContext(notificationDto.getContext());
			message.setSender(notificationDto.getSender());
			UserPhoneInbox inbox=new UserPhoneInbox();
			inbox.setPhoneNumber(message.getReceiver());
			List<Message> messages=new ArrayList<Message>();
			messages.add(message);
			inbox.setReceivedMessages(messages);
			userPhoneInboxRepository.save(inbox);
			log.info("Message saved");
		}
		
	
	}
	

}
