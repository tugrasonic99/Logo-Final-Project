package com.travelmainapp.helper;

import com.travelmainapp.models.PaymentMethod;

public class UserHelper {
	private String email;
	private String password;
	private PaymentMethod paymentMethod;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
	
	

}
