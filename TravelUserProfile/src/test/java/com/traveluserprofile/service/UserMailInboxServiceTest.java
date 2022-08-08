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

import com.traveluserprofile.models.Email;
import com.traveluserprofile.models.UserMailInbox;
import com.traveluserprofile.repository.UserMailInboxRepository;

@SpringBootTest
class UserMailInboxServiceTest {// UserMailInboxService test sınıfı. Coverage:100%
	
	@InjectMocks
	private UserMailInboxService userMailInboxService;
	
	@Mock
	private UserMailInboxRepository userMailInboxRepository;
	
	@Test
	void it_should_return_mails_by_email() {
		
		String email="abcd@gmail.com";
		
		UserMailInbox dummyInbox=prepare();
		List<Email> dummies=dummyInbox.getReceivedEmails();
		
		Mockito.when(userMailInboxRepository.findByEmail(email)).thenReturn(Optional.of(dummyInbox));
		
		List<Email> response=userMailInboxService.findMailboxByEmail(email);
		verify(userMailInboxRepository,times(1)).findByEmail(email);
		
		assertThat(response).isNotNull();
		assertThat(response).isEqualTo(dummies);
		
		
	}
	
	private UserMailInbox prepare() {
		UserMailInbox dummyInbox=new UserMailInbox();
		dummyInbox.setEmail("abcd@gmail.com");
		dummyInbox.setReceivedEmails(new ArrayList<Email>());
		return dummyInbox;
		
	}

}
