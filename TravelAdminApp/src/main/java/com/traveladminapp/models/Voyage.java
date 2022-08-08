package com.traveladminapp.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class Voyage {// Sefer genel yolculuk bilgilerinin yanında kullanılan araç ve sefere bağlı bilet listesini içerir.
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String start;
	private String destination;
	@OneToOne(cascade = {CascadeType.ALL})
	private Vehicle vehicle;
	private String date;
	private int cost;
	@OneToMany
	@JoinColumn(name = "ticket_id", insertable = true, updatable = false)
	private List<Ticket> tickets;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public List<Ticket> getTickets() {
		return tickets;
	}
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	
	
	

}
