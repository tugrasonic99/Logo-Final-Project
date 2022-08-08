package com.traveluserprofile.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class UserMailInbox {// Kullanıcı Mail kutusu. RabbitMQ tarafından gelecek kayıt mailleri için yaratılan entity.
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String email;
	@OneToMany
	@JoinColumn(name = "email_id", insertable = true, updatable = false)
	private List<Email> receivedEmails;
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Email> getReceivedEmails() {
		return receivedEmails;
	}
	public void setReceivedEmails(List<Email> receivedEmails) {
		this.receivedEmails = receivedEmails;
	}
	
	
	
	
	

}
