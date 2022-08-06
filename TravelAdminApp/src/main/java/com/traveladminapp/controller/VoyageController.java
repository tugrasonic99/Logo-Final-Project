package com.traveladminapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traveladminapp.helper.CreateVoyageHelper;
import com.traveladminapp.helper.VoyageFinderHelper;
import com.traveladminapp.helper.VoyageOperationHelper;
import com.traveladminapp.models.Voyage;
import com.traveladminapp.service.VoyageService;

@RestController
@RequestMapping(value = "/voyages")
public class VoyageController {
	
	@Autowired
	private VoyageService voyageService;
	
	
	@PostMapping
	@RequestMapping(value = "/initialize")
	public Voyage initialize() {
		return voyageService.initializeVoyage();
	}
	
	@GetMapping
	@RequestMapping(value = "/allvoyages")
	public List<Voyage> allVoyages() {
		return voyageService.findAllVoyages();
	}
	
	@GetMapping
	@RequestMapping(value = "/findbydate")
	public List<Voyage> findVoyageByDate(@RequestBody VoyageFinderHelper helper) {
		return voyageService.findVoyageByDate(helper.getDate());
	}
	
	@GetMapping
	@RequestMapping(value = "/findbylocations")
	public List<Voyage> findVoyageByLocations(@RequestBody VoyageFinderHelper helper) {
		return voyageService.findVoyageByLocations(helper.getFrom(), helper.getTo());
	}
	
	@PostMapping
	@RequestMapping(value = "/createvoyage")
	public Voyage createVoyage(@RequestBody CreateVoyageHelper helper) {
		return voyageService.createVoyage(helper.getVoyage(), helper.getVehicleType(), helper.getName(), helper.getPassword());
	}
	
	@PostMapping
	@RequestMapping(value = "/deletevoyage")
	public void deleteVoyage(@RequestBody VoyageOperationHelper helper) {
		voyageService.deleteVoyage(helper.getId(), helper.getName(), helper.getPassword());
	}
	
	@PostMapping
	@RequestMapping(value = "/ticketamount")
	public void findTicketAmount(@RequestBody VoyageOperationHelper helper) {
		voyageService.findTicketAmount(helper.getId(), helper.getName(), helper.getPassword());
	}
	
	@PostMapping
	@RequestMapping(value = "/totalcost")
	public void findCost(@RequestBody VoyageOperationHelper helper) {
		voyageService.findTotalCostOfVoyage(helper.getId(), helper.getName(), helper.getPassword());
	}
	

	


}
