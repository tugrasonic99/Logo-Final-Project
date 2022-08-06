package com.travelmainapp.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.travelmainapp.dto.NotificationDTO;
import com.travelmainapp.models.Passenger;
import com.travelmainapp.models.PaymentMethod;
import com.travelmainapp.models.Ticket;
import com.travelmainapp.models.User;
import com.travelmainapp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public User createUser(User u){
		boolean s=false;

		List<User> userList=userRepository.findAll();
		for(int i=0;i<userList.size();i++) {
			if(userList.get(i).getEmail().equals(u.getEmail())||userList.get(i).getPhoneNumber().equals(u.getPhoneNumber())) {
			    s=true;
			}
		}
		
		if(s==true) {
			return null;
		}
		String sha256hex = Hashing.sha256()
				  .hashString(u.getPassword(), StandardCharsets.UTF_8)
				  .toString();
		
		u.setPaymentMethod(new PaymentMethod());
		u.setPassword(sha256hex);
		u.setTickets(new ArrayList<Ticket>());
		
		
		rabbitTemplate.convertAndSend("travel.notification", new NotificationDTO("Email","travelagency@mail.com","New user" + u.getName()+" is created",
				u.getEmail()));
		
		return userRepository.save(u);
		
		
	}
	
	public User login(String email, String password) {
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		User u=userRepository.findByEmailAndPassword(email, sha256hex);
		
		return u;
	}
	
	public User setPaymentMethod(String email, String password,PaymentMethod paymentMethod) {
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		User u=userRepository.findByEmailAndPassword(email, sha256hex);
		u.getPaymentMethod().setBalance(paymentMethod.getBalance());
		u.getPaymentMethod().setPaymentType(paymentMethod.getPaymentType());
		userRepository.save(u);
		return u;
		
	}
	
	
	public List<Ticket> userTickets(String email, String password) {
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		User u=userRepository.findByEmailAndPassword(email, sha256hex);
		return u.getTickets();
	}
	

}
