package com.traveluserprofile.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class UserPhoneInbox {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String phoneNumber;
	@OneToMany
	@JoinColumn(name = "message_id", insertable = true, updatable = false)
	private List<Message> receivedMessages;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public List<Message> getReceivedMessages() {
		return receivedMessages;
	}
	public void setReceivedMessages(List<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}
	
	
	
	
	

}
