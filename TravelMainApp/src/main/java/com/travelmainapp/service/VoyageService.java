package com.travelmainapp.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelmainapp.dto.NotificationDTO;
import com.travelmainapp.enummodels.UserType;
import com.travelmainapp.enummodels.VehicleType;
import com.travelmainapp.models.PaymentMethod;
import com.travelmainapp.models.Ticket;
import com.travelmainapp.models.User;
import com.travelmainapp.models.Voyage;
import com.travelmainapp.repository.UserRepository;
import com.travelmainapp.repository.VoyageRepository;

@Service
public class VoyageService {
	
	
	@Autowired
	private VoyageRepository voyageRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	public List<Voyage> findAllVoyages(){
		return voyageRepository.findAll();
	}
	
	
	public List<Voyage> findVoyageByDate(String date){
		List<Voyage> voyageList=voyageRepository.findByDate(date);
		for(int i=0;i<voyageList.size();i++) {
			Voyage v=voyageList.get(i);
			v.setTickets(null);
			voyageList.set(i, v);
		}
		return voyageList;
	}
	
	
	public List<Voyage> findVoyageByLocations(String start, String destination){
		List<Voyage> voyageList=voyageRepository.findByStartAndDestination(start, destination);
		for(int i=0;i<voyageList.size();i++) {
			Voyage v=voyageList.get(i);
			v.setTickets(null);
			voyageList.set(i, v);
		}
		return voyageList;
		
	}
	
	public Ticket buyTicket(Ticket ticket) {
		if(userRepository.existsById(ticket.getUserId())) {
			if(voyageRepository.existsById(ticket.getVoyageId())) {
			    User user=userRepository.findById(ticket.getUserId()).get();
			    if(user.getUserType().equals(UserType.PERSONAL)) {
			    	int passengerCount=ticket.getPassengers().size();
			    	int maleCount=(int) ticket.getPassengers().stream().filter(it->it.getGender().equals("Male")).count();
			    	Voyage voyage=voyageRepository.findById(ticket.getVoyageId()).get();
			    	List<Ticket> userTickets=user.getTickets();
			    	int holderTicketCount=0;
			    	for(int i=0;i<userTickets.size();i++) {
			    		if(voyage.getId()==userTickets.get(i).getVoyageId()) {
			    			holderTicketCount=holderTicketCount+userTickets.get(i).getPassengers().size();
			    		}
			    	}
			    	
			    	if(holderTicketCount>=5 || holderTicketCount+passengerCount>=5) {
			    		return null;
			    		
			    	}
				 
			    	
			
			    	else if(passengerCount<=5 && maleCount<=2 && voyage.getVehicle().getCapacity()-voyage.getVehicle().getFilled()>=passengerCount){
			    		int totalCost=voyage.getCost()*passengerCount;
			    		int finalCost=user.getPaymentMethod().getBalance()-totalCost;
			    		if(finalCost>=0) {
			    			List<Ticket> tickets=voyage.getTickets();
			    			List<Ticket> ticketsUser=user.getTickets();
			    			ticket.setPrice(totalCost);
			    			ticket.setDate(voyage.getDate());
			    			tickets.add(ticket);
			    			ticketsUser.add(ticket);
			    			voyage.setTickets(tickets);
			    			user.setTickets(ticketsUser);
			    			PaymentMethod holderMethod=user.getPaymentMethod();
			    			holderMethod.setBalance(finalCost);
			    			user.setPaymentMethod(holderMethod);
			    			voyageRepository.save(voyage);
			    			userRepository.save(user);
			    			
			    			ticket.setGeneralInfo(voyage.getStart()+"-"+voyage.getDestination()+", "+ticket.getDate()+", "+ticket.getVoyageId()+", "+user.getName()+", "+totalCost);
			    			rabbitTemplate.convertAndSend("travel.notification", new NotificationDTO("Message","09044412",ticket.getGeneralInfo(),user.getPhoneNumber()));
			    			
			    			return ticket;
			    			
			    			
			    			
			    		}
			    		
			    		return null;
			    	}
			    	
			    	return null;
			    }
			    
			    else if(user.getUserType().equals(UserType.CORPORATE)) {
			    	int passengerCount=ticket.getPassengers().size();
			    	Voyage voyage=voyageRepository.findById(ticket.getVoyageId()).get();
			    	List<Ticket> userTickets=user.getTickets();
			    	int holderTicketCount=0;
			    	for(int i=0;i<userTickets.size();i++) {
			    		if(voyage.getId()==userTickets.get(i).getVoyageId()) {
			    			holderTicketCount=holderTicketCount+userTickets.get(i).getPassengers().size();
			    		}
			    	}
			    	
			    	if(holderTicketCount>=20 || holderTicketCount+passengerCount>=20) {
			    		return null;
			    		
			    	}
				 
			    	
			
			    	else if(passengerCount<=20 && voyage.getVehicle().getCapacity()-voyage.getVehicle().getFilled()>=passengerCount){
			    		int totalCost=voyage.getCost()*passengerCount;
			    		int finalCost=user.getPaymentMethod().getBalance()-totalCost;
			    		if(finalCost>=0) {
			    			List<Ticket> tickets=voyage.getTickets();
			    			List<Ticket> ticketsUser=user.getTickets();
			    			ticket.setPrice(totalCost);
			    			ticket.setDate(voyage.getDate());
			    			tickets.add(ticket);
			    			ticketsUser.add(ticket);
			    			voyage.setTickets(tickets);
			    			user.setTickets(ticketsUser);
			    			PaymentMethod holderMethod=user.getPaymentMethod();
			    			holderMethod.setBalance(finalCost);
			    			user.setPaymentMethod(holderMethod);
			    			voyageRepository.save(voyage);
			    			userRepository.save(user);
			    			
			    			ticket.setGeneralInfo(voyage.getStart()+"-"+voyage.getDestination()+", "+ticket.getDate()+", "+ticket.getVoyageId()+", "+user.getName()+", "+totalCost);
			    			rabbitTemplate.convertAndSend("travel.notification", new NotificationDTO("Message","09044412",ticket.getGeneralInfo(),user.getPhoneNumber()));
			    			return ticket;
			    			
			    			
			    			
			    		}
			    		
			    		return null;
			    		
			    	}
			    	
			    	return null;
			    }
			    return null;
			}
			return null;
		}
		return null;
	}
	
	
	public List<Voyage> findVoyagesByVehicle(VehicleType vehicleType){
		List<Voyage> voyages=voyageRepository.findAll();
		List<Voyage> voyageStream=voyages.stream().filter(x->x.getVehicle().getVehicleName().equals(vehicleType)).toList();
		
		return voyageStream;
		
		
		
	}



}
