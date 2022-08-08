package com.traveluserprofile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.traveluserprofile.models.Message;
import com.traveluserprofile.models.UserPhoneInbox;
import com.traveluserprofile.repository.UserPhoneInboxRepository;



@SpringBootTest
public class UserPhoneInboxServiceTest {// UserPhoneInboxService test sınıfı. Coverage:100%
	
	@InjectMocks
	private UserPhoneInboxService userPhoneInboxService;
	
	@Mock
	private UserPhoneInboxRepository userPhoneInboxRepository;
	
	@Test
	void it_should_return_messages_by_number() {
		
		String phoneNumber="12345678";
		
		UserPhoneInbox dummyInbox=prepare();
		List<Message> dummies=dummyInbox.getReceivedMessages();
		
		Mockito.when(userPhoneInboxRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(dummyInbox));
		
		List<Message> response=userPhoneInboxService.findPhoneInboxByPhoneNumber(phoneNumber);
		verify(userPhoneInboxRepository,times(1)).findByPhoneNumber(phoneNumber);
		
		assertThat(response).isNotNull();
		assertThat(response).isEqualTo(dummies);
		
		
	}
	
	private UserPhoneInbox prepare() {
		UserPhoneInbox dummyInbox=new UserPhoneInbox();
		dummyInbox.setPhoneNumber("12345678");
		dummyInbox.setReceivedMessages(new ArrayList<Message>());
		return dummyInbox;
		
	}




}
