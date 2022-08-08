package com.traveladminapp.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.traveladminapp.enummodels.VehicleType;
import com.traveladminapp.exception.QueryEmptyException;
import com.traveladminapp.models.AdminUser;
import com.traveladminapp.models.Ticket;
import com.traveladminapp.models.Vehicle;
import com.traveladminapp.models.Voyage;
import com.traveladminapp.repository.AdminUserRepository;
import com.traveladminapp.repository.VoyageRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VoyageService {// Admin taraflı sefer servisi.
	
	
	@Autowired
	private VoyageRepository voyageRepository;
	
	@Autowired
	private AdminUserRepository adminUserRepository;
	
	
	public List<Voyage> findAllVoyages(){
		return voyageRepository.findAll();
	}
	// Basit initializer. Eğer ilk veriyi bu fonksiyonla kaydetmek isterseniz buradaki ve controller'daki yorum satırlarını silin.
	/*
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
		
	}*/
	
	public List<Voyage> findVoyageByDate(String date){// Tarihe dayalı sefer bulma.
		return voyageRepository.findByDate(date);
	}
	
	public List<Voyage> findVoyageByLocations(String start, String destination){// Başlangıç-bitiş kullanarak sefer bulma.
		return voyageRepository.findByStartAndDestination(start, destination);
	}
	
	public Voyage createVoyage(Voyage voyage, int vehicleChoice) {// Sefer yaratılışı.
		// Başlangıç-bitiş, tarih ve fiyat JSON ile verilmelidir.
		
		
	   if(vehicleChoice==1) {// Eğer verilen sayı değeri 1 ise, uçak için bir obje hazırlayıp sefer kaydedilir.
			Vehicle v=new Vehicle();
			v.setVehicleType(VehicleType.AIRPLANE);
			v.setCapacity(189);// Uçak max kapasite
			v.setFilled(0);// Yeni yaratım.
			voyage.setVehicle(v);
			voyage.setTickets(new ArrayList<Ticket>());
			return voyageRepository.save(voyage);
		}
		else if(vehicleChoice==2) {// Eğer verilen sayı değeri 2 ise, otobüs için bir obje hazırlayıp sefer kaydedilir.
			Vehicle v=new Vehicle();
			v.setVehicleType(VehicleType.BUS);
			v.setCapacity(45);
			v.setFilled(0);
			voyage.setVehicle(v);
			voyage.setTickets(new ArrayList<Ticket>());
			return voyageRepository.save(voyage);
		}
		log.info("Wrong input");
	   return null;
	}
	
	public void deleteVoyage(int id) {// Sefer silme
		
		voyageRepository.deleteById(id);
	}
	
	public int findTicketAmount(int id) {// Sefere dayalı bilet sayısını bulma(yolcu sayısı dahil değil).
		
		Voyage voyage=voyageRepository.findById(id).orElseThrow(() ->new QueryEmptyException("Empty Result"));
		List<Ticket> ticketList=voyage.getTickets();
		int size=ticketList.size();
		log.info("Total ticket amount is:"+size);
		return size;
	}
	
	public int findTotalCostOfVoyage(int id) {// Seferin toplam masrafını bulma.
		
		Voyage voyage=voyageRepository.findById(id).orElseThrow(() ->new QueryEmptyException("Empty Result"));
		List<Ticket> ticketList=voyage.getTickets();
		int cost=ticketList.stream().mapToInt(x->x.getPrice()).reduce(0,Integer::sum);
		String costString=Integer.toString(cost);
		log.info("Total cost is:"+costString);
		return cost;

}
	
	
}