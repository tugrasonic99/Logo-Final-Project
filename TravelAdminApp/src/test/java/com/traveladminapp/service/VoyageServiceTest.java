package com.traveladminapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.base.Optional;
import com.google.common.hash.Hashing;
import com.traveladminapp.enummodels.VehicleType;
import com.traveladminapp.exception.QueryEmptyException;
import com.traveladminapp.models.AdminUser;
import com.traveladminapp.models.Ticket;
import com.traveladminapp.models.Vehicle;
import com.traveladminapp.models.Voyage;
import com.traveladminapp.repository.AdminUserRepository;
import com.traveladminapp.repository.VoyageRepository;

@SpringBootTest
public class VoyageServiceTest {// VoyageService test sınıfı. Coverage 54%
	
	@InjectMocks
	private VoyageService voyageService;
	
	@Mock
	private VoyageRepository voyageRepository;
	
	@Mock
	private AdminUserRepository adminUserRepository;
	
	
	
	private AdminUser prepareAdmin() {
		AdminUser user=new AdminUser();
		user.setEmail("abc@gmail.com");
		user.setName("Ahmet");
		user.setPassword("password1");
		return user;
	}
	
	private Voyage prepareVoyage() {
		Voyage v=new Voyage();
		v.setCost(5000);
		v.setDestination("Ankara");
		v.setStart("Antalya");
		v.setDate("01-01-2022");
		v.setTickets(new ArrayList<Ticket>());
		Vehicle vehicle=new Vehicle();
		vehicle.setVehicleType(VehicleType.AIRPLANE);
		vehicle.setCapacity(189);
		vehicle.setFilled(0);
		v.setVehicle(vehicle);
		return v;
	}
	
	private List<Voyage> prepareVoyages() {
		Voyage v=new Voyage();
		List<Voyage> list=new ArrayList<Voyage>();
		v.setCost(5000);
		v.setId(5);
		v.setDestination("Ankara");
		v.setStart("Antalya");
		v.setDate("01-01-2022");
		v.setTickets(new ArrayList<Ticket>());
		Vehicle vehicle=new Vehicle();
		vehicle.setVehicleType(VehicleType.AIRPLANE);
		vehicle.setCapacity(189);
		vehicle.setFilled(0);
		v.setVehicle(vehicle);
		list.add(v);
		return list;
	}
	
	@Test
	void it_should_return_all_voyages() {
		
		List<Voyage> list=prepareVoyages();
		
		Mockito.when(voyageRepository.findAll()).thenReturn(list);
		
		List<Voyage> response=voyageService.findAllVoyages();
		
        verify(voyageRepository, times(1)).findAll();
		
		assertThat(response).isEqualTo(list);
		
		
	}
	
	@Test
	void it_should_return_voyage_by_date() {
		
		List<Voyage> list=prepareVoyages();
		
		String date="01-01-2022";
		
		Mockito.when(voyageRepository.findByDate(date)).thenReturn(list);
		
		List<Voyage> response=voyageService.findVoyageByDate(date);
		
        verify(voyageRepository, times(1)).findByDate(date);
		
		assertThat(response).isEqualTo(list);
		
		
	}
	
	@Test
	void it_should_return_voyage_by_location() {
		
		List<Voyage> list=prepareVoyages();
		
		String destination="Ankara";
		
		String start="Antalya";
		
		Mockito.when(voyageRepository.findByStartAndDestination(start, destination)).thenReturn(list);
		
		List<Voyage> response=voyageService.findVoyageByLocations(start, destination);
		
        verify(voyageRepository, times(1)).findByStartAndDestination(start, destination);
		
		assertThat(response).isEqualTo(list);
		
		
	}
	
	@Test
	void it_should_create_voyage_for_airplane() {
		
		
		Voyage voyage=prepareVoyage();
		
		
		
		Mockito.when(voyageRepository.save(Mockito.any())).thenReturn(voyage);
		
		Voyage response=voyageService.createVoyage(voyage, 1);
		
		verify(voyageRepository, times(1)).save(Mockito.any());
		
		
		
	}
	   @Test
       void it_should_create_voyage_for_bus() {
		
		Voyage voyage=prepareVoyage();
		voyage.getVehicle().setVehicleType(VehicleType.BUS);
		voyage.getVehicle().setCapacity(45);
		
		
		
		Mockito.when(voyageRepository.save(Mockito.any())).thenReturn(voyage);
		
		Voyage response=voyageService.createVoyage(voyage, 2);
		
		verify(voyageRepository, times(1)).save(Mockito.any());
		
		
		
	}
	   
	   
	   @Test
       void it_should_delete_voyage() {
		   
		int id=5;
		
		voyageService.deleteVoyage(id);
		
		verify(voyageRepository, times(1)).deleteById(id);
		
		
		
	}
	   
	   // Bilet miktarı ve masraf fonksiyonları, findById'nin optional değerine dokunamadığımdan bitiremedim.
	   
	   /*@Ignore
	   @Test
	   void it_should_find_ticket_sold() {
		   Voyage voyage=prepareVoyage();
		   int id=5;
		   //Mockito.when(voyageRepository.findById(id)).thenReturn(voyage);
		   
		   int response=voyageService.findTicketAmount(id);
		   
		   verify(voyageRepository, times(1)).findById(id).get().getTickets().size();
		   
		   assertThat(response).isEqualTo(voyage.getTickets().size());
		   
		   
		   
		  
	   }
	   
	   @Ignore
	   @Test
	   void it_should_find_ticket_price() {
		   Voyage voyage=prepareVoyage();
		   int id=5;
		   //Mockito.when(voyageRepository.findById(id)).thenReturn(voyage);
		   
		   int response=voyageService.findTotalCostOfVoyage(id);
		   
		 
		   
		   
		   
		  
	   }
	   */
	
	   
	   
	   
	   
	
	

}
