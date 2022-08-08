package com.travelmainapp.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelmainapp.dto.NotificationDTO;
import com.travelmainapp.enummodels.UserType;
import com.travelmainapp.enummodels.VehicleType;
import com.travelmainapp.exception.QueryEmptyException;
import com.travelmainapp.models.PaymentMethod;
import com.travelmainapp.models.Ticket;
import com.travelmainapp.models.User;
import com.travelmainapp.models.Voyage;
import com.travelmainapp.repository.UserRepository;
import com.travelmainapp.repository.VoyageRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VoyageService {// Kullanıcı taraflı sefer servisi.
	
	
	@Autowired
	private VoyageRepository voyageRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	public List<Voyage> findAllVoyages(){// Tüm seferler
		return voyageRepository.findAll();
	}
	
	
	public List<Voyage> findVoyageByDate(String date){// Tarihe dayalı sefer arayışı.
		List<Voyage> voyageList=voyageRepository.findByDate(date);
		for(int i=0;i<voyageList.size();i++) {
			Voyage v=voyageList.get(i);
			v.setTickets(null);
			voyageList.set(i, v);
		}
		return voyageList;
	}
	
	
	public List<Voyage> findVoyageByLocations(String start, String destination){// Başlangıç-bitiş'e dayalı sefer arayışı.
		List<Voyage> voyageList=voyageRepository.findByStartAndDestination(start, destination);
		for(int i=0;i<voyageList.size();i++) {
			Voyage v=voyageList.get(i);
			v.setTickets(null);
			voyageList.set(i, v);
		}
		return voyageList;
		
	}
	
	public Ticket buyTicket(Ticket ticket) {// Bilet alım işlemi
		if(userRepository.existsById(ticket.getUserId())) {// Kullanıcı sorgusu
			if(voyageRepository.existsById(ticket.getVoyageId())) {// Sefer sorgusu
			    User user=userRepository.findById(ticket.getUserId()).orElseThrow(() ->new QueryEmptyException("Empty Result"));
			    if(user.getUserType().equals(UserType.PERSONAL)) {// Kullanıcı eğer kişisel ise
			    	int passengerCount=ticket.getPassengers().size();
			    	int maleCount=(int) ticket.getPassengers().stream().filter(it->it.getGender().equals("Male")).count();// Yolcu erkek cinsiyet sayısı
			    	Voyage voyage=voyageRepository.findById(ticket.getVoyageId()).orElseThrow(() ->new QueryEmptyException("Empty Result"));
			    	List<Ticket> userTickets=user.getTickets();
			    	int holderTicketCount=0;
			    	for(int i=0;i<userTickets.size();i++) {
			    		if(voyage.getId()==userTickets.get(i).getVoyageId()) {// İstenilen biletteki kişi sayısı.
			    			holderTicketCount=holderTicketCount+userTickets.get(i).getPassengers().size();
			    		}
			    	}
			    	
			    	if(holderTicketCount>=5 || holderTicketCount+passengerCount>=5) {
			    		// Eğer kişi sayısı 5'ten fazlaysa ya da kullanıcı daha önceden alğıgı bilet sayısı ile 5'ten fazlaysa.
			    		log.info("User has exceeded passenger limit for voyage.");
			    		return null;
			    		
			    	}
				 
			    	
			
			    	else if(passengerCount<=5 && maleCount<=2 && voyage.getVehicle().getCapacity()-voyage.getVehicle().getFilled()>=passengerCount){
			    		// Eğer kullanıcı limitinde bir sorun yoksa, erkek sayısı 2'den azsa ve seferde hala yer varsa.
			    		int totalCost=voyage.getCost()*passengerCount;
			    		int finalCost=user.getPaymentMethod().getBalance()-totalCost;// Ödeme methodundan toplam değer çıkarılır.
			    		if(finalCost>=0) {// Sonuç pozitif ise bilet işlemi gerçekleşir.
			    			List<Ticket> tickets=voyage.getTickets();
			    			List<Ticket> ticketsUser=user.getTickets();
			    			Ticket t=new Ticket();
			    			t.setDate(voyage.getDate());
			    			t.setPassengers(ticket.getPassengers());
			    			t.setPrice(totalCost);
			    			t.setUserId(ticket.getUserId());
			    			t.setVoyageId(ticket.getUserId());
			    			tickets.add(t);
			    			ticketsUser.add(t);
			    			voyage.setTickets(tickets);
			    		    int cap=voyage.getVehicle().getFilled();
			    		    cap=cap+passengerCount;
			    		    voyage.getVehicle().setFilled(cap);
			    			user.setTickets(ticketsUser);
			    			PaymentMethod holderMethod=user.getPaymentMethod();
			    			holderMethod.setBalance(finalCost);
			    			user.setPaymentMethod(holderMethod);
			    			voyageRepository.save(voyage);
			    			userRepository.save(user);
			    			
			    			ticket.setGeneralInfo(voyage.getStart()+"-"+voyage.getDestination()+", "+ticket.getDate()+", "+ticket.getVoyageId()+", "+user.getName()+", "+totalCost);
			    			rabbitTemplate.convertAndSend("travel.notification", new NotificationDTO("Message","09044412",ticket.getGeneralInfo(),user.getPhoneNumber()));
			    			// Bilet alındığı zaman kullanıcıya işlemin başarısıyla ilgili mesaj gönderilir.
			    			return ticket;
			    			
			    			
			    			
			    		}
			    		log.info("User do not meet the requirements.");
			    		return null;
			    	}
			    	log.info("User do not meet the requirements.");
			    	return null;
			    }
			    
			    else if(user.getUserType().equals(UserType.CORPORATE)) {// Eğer kullanıcı Şirketsel ise.
			    	int passengerCount=ticket.getPassengers().size();
			    	Voyage voyage=voyageRepository.findById(ticket.getVoyageId()).orElseThrow(() ->new QueryEmptyException("Empty Result"));
			    	List<Ticket> userTickets=user.getTickets();
			    	int holderTicketCount=0;
			    	for(int i=0;i<userTickets.size();i++) {// İstenilen biletteki kişi sayısı.
			    		if(voyage.getId()==userTickets.get(i).getVoyageId()) {
			    			holderTicketCount=holderTicketCount+userTickets.get(i).getPassengers().size();
			    		}
			    	}
			    	
			    	if(holderTicketCount>=20 || holderTicketCount+passengerCount>=20) {
			    		// Eğer kişi sayısı 20'ten fazlaysa ya da kullanıcı daha önceden alğıgı bilet sayısı ile 20'ten fazlaysa.
			    		log.info("User has exceeded passenger limit for voyage.");
			    		return null;
			    		
			    	}
				 
			    	
			
			    	else if(passengerCount<=20 && voyage.getVehicle().getCapacity()-voyage.getVehicle().getFilled()>=passengerCount){
			    		// Eğer kullanıcı limitinde sorun yoksa ve seferde hala yer varsa
			    		int totalCost=voyage.getCost()*passengerCount;
			    		int finalCost=user.getPaymentMethod().getBalance()-totalCost;// Ödeme methodundan toplam değer çıkarılır.
			    		if(finalCost>=0) {// Sonuç pozitif ise bilet işlemi tamamlanır.
			    			List<Ticket> tickets=voyage.getTickets();
			    			List<Ticket> ticketsUser=user.getTickets();
			    			Ticket t=new Ticket();
			    			t.setDate(voyage.getDate());
			    			t.setPassengers(ticket.getPassengers());
			    			t.setPrice(totalCost);
			    			t.setUserId(ticket.getUserId());
			    			t.setVoyageId(ticket.getUserId());
			    			tickets.add(t);
			    			ticketsUser.add(t);
			    			tickets.add(ticket);
			    			ticketsUser.add(ticket);
			    			voyage.setTickets(tickets);
			    			user.setTickets(ticketsUser);
			    			int cap=voyage.getVehicle().getFilled();
			    		    cap=cap+passengerCount;
			    		    voyage.getVehicle().setFilled(cap);
			    			PaymentMethod holderMethod=user.getPaymentMethod();
			    			holderMethod.setBalance(finalCost);
			    			user.setPaymentMethod(holderMethod);
			    			voyageRepository.save(voyage);
			    			userRepository.save(user);
			    			
			    			ticket.setGeneralInfo(voyage.getStart()+"-"+voyage.getDestination()+", "+ticket.getDate()+", "+ticket.getVoyageId()+", "+user.getName()+", "+totalCost);
			    			rabbitTemplate.convertAndSend("travel.notification", new NotificationDTO("Message","09044412",ticket.getGeneralInfo(),user.getPhoneNumber()));
			    			// Bilet alındığı zaman kullanıcıya işlemin başarısıyla ilgili mesaj gönderilir.
			    			return ticket;
			    			
			    			
			    			
			    		}
			    		log.info("User do not meet the requirements.");
			    		return null;
			    		
			    	}
			    	log.info("User do not meet the requirements.");
			    	return null;
			    }
			    log.info("User do not meet the requirements.");
			    return null;
			}
			log.info("User do not meet the requirements.");
			return null;
		}
		log.info("User do not meet the requirements.");
		return null;
	}
	
	
	public List<Voyage> findVoyagesByVehicle(VehicleType vehicleType){// Araç bazlı sefer arama.
		List<Voyage> voyages=voyageRepository.findAll();
		List<Voyage> voyageStream=voyages.stream().filter(x->x.getVehicle().getVehicleName().equals(vehicleType)).toList();
		
		return voyageStream;
		
		
		
	}



}
