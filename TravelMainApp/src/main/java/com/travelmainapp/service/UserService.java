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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {// Kullanıcı servisi.
	// Hash işlemi SHA-256 ile yapılır.
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public User createUser(User u){// Kullanıcı yarartımı
		boolean s=false;

		List<User> userList=userRepository.findAll();
		for(int i=0;i<userList.size();i++) {// Eğer girilen email veya telefon numarası db içerisinde var ise bir sonuç dönmeyecek.
			if(userList.get(i).getEmail().equals(u.getEmail())||userList.get(i).getPhoneNumber().equals(u.getPhoneNumber())) {
			    s=true;
			}
		}
		
		if(s==true) {
			log.info("Credentials already exist");
			return null;
		}
		String sha256hex = Hashing.sha256()
				  .hashString(u.getPassword(), StandardCharsets.UTF_8)
				  .toString();
		
		u.setPaymentMethod(new PaymentMethod());
		u.setPassword(sha256hex);
		u.setTickets(new ArrayList<Ticket>());
		
		
		rabbitTemplate.convertAndSend("travel.notification", new NotificationDTO("Email","travelagency@mail.com","New user " + u.getName()+" is created ",
				u.getEmail()));// Yeni kullanıcı yaratıldığında RabbitMQ ile mail gönderilecek.
		
		return userRepository.save(u);
		
		
	}
	
	public User login(String email, String password) {// Login
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		User u=userRepository.findByEmailAndPassword(email, sha256hex);
		
		return u;
	}
	
	public User setPaymentMethod(String email, String password,PaymentMethod paymentMethod) {// Ödeme methodu girişi.
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		User u=userRepository.findByEmailAndPassword(email, sha256hex);
		u.getPaymentMethod().setBalance(paymentMethod.getBalance());
		u.getPaymentMethod().setPaymentType(paymentMethod.getPaymentType());
		userRepository.save(u);
		return u;
		
	}
	
	
	public List<Ticket> userTickets(String email, String password) {// Kullanıcıya ait biletler.
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		User u=userRepository.findByEmailAndPassword(email, sha256hex);
		return u.getTickets();
	}
	

}
