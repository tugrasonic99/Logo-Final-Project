package com.travelmainapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.travelmainapp.enummodels.UserType;
import com.travelmainapp.enummodels.VehicleType;
import com.travelmainapp.models.PaymentMethod;
import com.travelmainapp.models.Ticket;
import com.travelmainapp.models.User;
import com.travelmainapp.models.Vehicle;
import com.travelmainapp.models.Voyage;
import com.travelmainapp.repository.UserRepository;
import com.travelmainapp.repository.VoyageRepository;

@SpringBootTest
public class VoyageServiceTest {// VoyageService için Test. Coverage:16%
	//NOT: Maalesef ana fonksiyon test case'lerimde hatalar aldım o yüzden yazamadım.
	
	@InjectMocks
	private VoyageService voyageService;
	
	@Mock
	private VoyageRepository voyageRepository;
	
	@Mock
	private UserRepository userRepository;
	
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
	
	/*private Voyage prepareVoyage() {
		Voyage v=new Voyage();
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
		return v;
	}
	
	private User prepareUser() {
		User u=new User();
		u.setEmail("abc@gmail.com");
		u.setName("Ahmet");
		u.setPassword("password1");
		u.setPaymentMethod(new PaymentMethod());
		u.setTickets(new ArrayList<Ticket>());
		u.setUserType(UserType.PERSONAL);
		u.setPhoneNumber("4125");
		return u;
		
	}
	
	private User prepareUser2() {
		User u=new User();
		u.setEmail("abcd@gmail.com");
		u.setName("Ahmet2");
		u.setPassword("password1");
		u.setPaymentMethod(new PaymentMethod());
		u.setTickets(new ArrayList<Ticket>());
		u.setUserType(UserType.CORPORATE);
		u.setPhoneNumber("41254");
		return u;
		
	}*/
	
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
	void it_should_return_all_voyages() {
		
		List<Voyage> list=prepareVoyages();
		
		Mockito.when(voyageRepository.findAll()).thenReturn(list);
		
		List<Voyage> response=voyageService.findAllVoyages();
		
        verify(voyageRepository, times(1)).findAll();
		
		assertThat(response).isEqualTo(list);
		
	}
	
	@Test
	void it_should_return_voyages_by_vehicle() {
		
		List<Voyage> list=prepareVoyages();
		VehicleType vehicle=VehicleType.AIRPLANE;
		
		Mockito.when(voyageRepository.findAll()).thenReturn(list);
		
		List<Voyage> response=voyageService.findVoyagesByVehicle(vehicle);
		
        verify(voyageRepository, times(1)).findAll();
		
		assertThat(response).isEqualTo(list);
		
	}
	/*
	@Test
	void it_should_create_ticket_for_airplane_user_corporate() {
		
		User user=prepareUser();
		Voyage v=prepareVoyage();
		
		//Mockito.when(voyageRepository.findById(Mockito.anyInt()).get()).thenReturn(v);
		
		
		
		
	}
	
	@Test
	void it_should_create_ticket_for_airplane_user_personal() {
		
		User user=prepareUser();
		Voyage v=prepareVoyage();
		
		//Mockito.when(voyageRepository.findById(Mockito.anyInt()).get()).thenReturn(v);
		
		
		
		
	}
	*/

}
