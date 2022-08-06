package com.traveladminapp.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.traveladminapp.enummodels.VehicleType;
import com.traveladminapp.models.AdminUser;
import com.traveladminapp.models.Ticket;
import com.traveladminapp.models.Vehicle;
import com.traveladminapp.models.Voyage;
import com.traveladminapp.repository.AdminUserRepository;
import com.traveladminapp.repository.VoyageRepository;

@Service
public class VoyageService {
	
	
	@Autowired
	private VoyageRepository voyageRepository;
	
	@Autowired
	private AdminUserRepository adminUserRepository;
	
	
	public List<Voyage> findAllVoyages(){
		return voyageRepository.findAll();
	}
	
	public Voyage initializeVoyage() {
		Voyage newVoyage=new Voyage();
		newVoyage.setDate("01-12-2022");
		newVoyage.setCost(2120);
		newVoyage.setStart("Antalya");
		newVoyage.setDestination("Istanbul");
		newVoyage.setTickets(new ArrayList<Ticket>());
		Vehicle v=new Vehicle();
		v.setVehicleType(VehicleType.AIRPLANE);
		v.setCapacity(189);
		v.setFilled(0);
		newVoyage.setVehicle(v);
		return voyageRepository.save(newVoyage);
		
	}
	
	public List<Voyage> findVoyageByDate(String date){
		return voyageRepository.findByDate(date);
	}
	
	public List<Voyage> findVoyageByLocations(String start, String destination){
		return voyageRepository.findByStartAndDestination(start, destination);
	}
	
	public Voyage createVoyage(Voyage voyage, int vehicleChoice,String name, String password) {
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
	   if(adminUserRepository.findByNameAndPassword(name, sha256hex)==null) {
		   return null;
	   }
		
		
	   else if(vehicleChoice==1) {
			Vehicle v=new Vehicle();
			v.setVehicleType(VehicleType.AIRPLANE);
			v.setCapacity(189);
			v.setFilled(0);
			voyage.setVehicle(v);
			voyage.setTickets(new ArrayList<Ticket>());
			return voyageRepository.save(voyage);
		}
		else if(vehicleChoice==2) {
			Vehicle v=new Vehicle();
			v.setVehicleType(VehicleType.BUS);
			v.setCapacity(45);
			v.setFilled(0);
			voyage.setVehicle(v);
			voyage.setTickets(new ArrayList<Ticket>());
			return voyageRepository.save(voyage);
		}
		
	   return null;
	}
	
	public void deleteVoyage(int id, String name, String password) {
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
	   if(adminUserRepository.findByNameAndPassword(name, sha256hex)==null) {
		   
	   }
		voyageRepository.deleteById(id);
	}
	
	public void findTicketAmount(int id, String name, String password) {
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
	   if(adminUserRepository.findByNameAndPassword(name, sha256hex)==null) {
		   
	   }
		Voyage voyage=voyageRepository.findById(id).get();
		List<Ticket> ticketList=voyage.getTickets();
		int size=ticketList.size();
		System.out.println(size);
	}
	
	public void findTotalCostOfVoyage(int id, String name, String password) {
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
	   if(adminUserRepository.findByNameAndPassword(name, sha256hex)==null) {
		   
	   }
		Voyage voyage=voyageRepository.findById(id).get();
		List<Ticket> ticketList=voyage.getTickets();
		System.out.println(ticketList.stream().mapToInt(x->x.getPrice()).reduce(0,Integer::sum));

}
	
	
}