package com.travelmainapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelmainapp.helper.VoyageHelper;
import com.travelmainapp.models.Ticket;
import com.travelmainapp.models.Voyage;
import com.travelmainapp.service.VoyageService;

@RestController
@RequestMapping(value="/mainvoyage")
public class VoyageController {
	
	@Autowired
	private VoyageService voyageService;
	
	
	@GetMapping
	public List<Voyage> allVoyages(){
		return voyageService.findAllVoyages();
	}
	
	@GetMapping
	@RequestMapping(value="/voyagesbydate")
	public List<Voyage> findVoyageByDate(@RequestBody VoyageHelper helper){
		return voyageService.findVoyageByDate(helper.getDate());
	}
	
	@GetMapping
	@RequestMapping(value="/voyagesby")
	public List<Voyage> findVoyageByVehicle(@RequestBody VoyageHelper helper){
		return voyageService.findVoyagesByVehicle(helper.getVehicleType());
	}
	
	@GetMapping
	@RequestMapping(value="/voyageslocations")
	public List<Voyage> findVoyageByLocations(@RequestBody VoyageHelper helper){
		return voyageService.findVoyageByLocations(helper.getFrom(), helper.getTo());
	}
	
	@PostMapping
	@RequestMapping(value="/buyticket")
	public Ticket buyTicket(@RequestBody Ticket ticket) {
		return voyageService.buyTicket(ticket);
	}
	
	
	
	
	
	

}
