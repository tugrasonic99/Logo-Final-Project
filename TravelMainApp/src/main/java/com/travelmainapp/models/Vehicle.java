package com.travelmainapp.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.travelmainapp.enummodels.VehicleType;

@Entity
public class Vehicle {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@Enumerated(EnumType.ORDINAL)
	private VehicleType vehicleType;
	private int capacity;
	private int filled;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public VehicleType getVehicleName() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getFilled() {
		return filled;
	}
	public void setFilled(int filled) {
		this.filled = filled;
	}
	
	
	
	

}
