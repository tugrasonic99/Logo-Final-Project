package com.travelmainapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.hash.Hashing;
import com.travelmainapp.enummodels.UserType;
import com.travelmainapp.models.PaymentMethod;
import com.travelmainapp.models.Ticket;
import com.travelmainapp.models.User;
import com.travelmainapp.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {// UserService için test. Coverage:83%
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private AmqpTemplate rabbitTemplate;
	
	private User prepareUser() {
		User u=new User();
		u.setEmail("abc@gmail.com");
		u.setName("Ahmet");
		u.setPassword("password1");
		u.setPaymentMethod(new PaymentMethod());
		u.setTickets(new ArrayList<Ticket>());
		u.setUserType(UserType.PERSONAL);
		u.setPhoneNumber("4125");
		return u;
		
	}
	
	
	
	@Test
	void it_should_create_user() {
		
		User u=prepareUser();
		String password="password1";
		String sha256hex = Hashing.sha256()
				  .hashString(u.getPassword(), StandardCharsets.UTF_8)
				  .toString();
		u.setPassword(sha256hex);
		Mockito.when(userRepository.save(Mockito.any())).thenReturn(u);
		
		User response=userService.createUser(u);
		
		verify(userRepository, times(1)).save(Mockito.any());
		   
		assertThat(response.getEmail()).isEqualTo(u.getEmail());
	}
	
	
	@Test
	void it_should_return_user_tickets() {
		User u=prepareUser();
		String email="abc@gmail.com";
		String password="password1";
		String sha256hex = Hashing.sha256()
				  .hashString(u.getPassword(), StandardCharsets.UTF_8)
				  .toString();
		u.setPassword(sha256hex);
		
		Mockito.when(userRepository.findByEmailAndPassword(email, sha256hex)).thenReturn(u);
		
		List<Ticket> response=userService.userTickets(email, password);
		
		assertThat(response).isEqualTo(u.getTickets());
	}
	
	@Test
	void it_should_set_payment_method() {
		User u=prepareUser();
		String email="abc@gmail.com";
		String password="password1";
		String sha256hex = Hashing.sha256()
				  .hashString(u.getPassword(), StandardCharsets.UTF_8)
				  .toString();
		u.setPassword(sha256hex);
		
		User u2=u;
	
		
		Mockito.when(userRepository.findByEmailAndPassword(email, sha256hex)).thenReturn(u);
		
		PaymentMethod method=new PaymentMethod();
	    method.setBalance(5000);
		User response=userService.setPaymentMethod(email, password,method);
		
		
		assertThat(response.getPaymentMethod().getBalance()).isEqualTo(method.getBalance());
	}
	
	
	@Test
	void it_should_login() {
		User u=prepareUser();
		String email="abc@gmail.com";
		String password="password1";
		String sha256hex = Hashing.sha256()
				  .hashString(u.getPassword(), StandardCharsets.UTF_8)
				  .toString();
		
		
		Mockito.when(userRepository.findByEmailAndPassword(email, sha256hex)).thenReturn(u);
		
		
		User response=userService.login(email,password);
		
		verify(userRepository, times(1)).findByEmailAndPassword(email, sha256hex);
		
		assertThat(response.getEmail()).isEqualTo(u.getEmail());
		
		
	}

}
