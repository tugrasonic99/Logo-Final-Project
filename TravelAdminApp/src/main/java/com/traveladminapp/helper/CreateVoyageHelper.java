package com.traveladminapp.helper;

import com.traveladminapp.models.Vehicle;
import com.traveladminapp.models.Voyage;

public class CreateVoyageHelper {// VoyageController'da Rest üzerinden sefer yaratımına yardımcı olmak için hazırlanan sınıf.
	
	private Voyage voyage;
	private int vehicleType;
	private String name;
	private String password;
	
	
	public Voyage getVoyage() {
		return voyage;
	}
	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}
	public int getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
