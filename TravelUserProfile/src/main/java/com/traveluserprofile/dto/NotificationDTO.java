package com.traveluserprofile.dto;

public class NotificationDTO {
	
	private String notificationType;
	private String sender;
	private String context;
	private String receiver;
	
	
	public NotificationDTO() {
		
	}
	
	
	
	
	public NotificationDTO(String notificationType, String sender, String context, String receiver) {
		this.notificationType = notificationType;
		this.sender = sender;
		this.context = context;
		this.receiver = receiver;
	}
	
	
	
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	

}
