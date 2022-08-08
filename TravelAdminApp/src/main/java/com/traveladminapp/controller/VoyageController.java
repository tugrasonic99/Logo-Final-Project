package com.traveladminapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class VoyageController {// localhost:8080/voyages
	
	@Autowired
	private VoyageService voyageService;
	
	
	/*@PostMapping
	@RequestMapping(value = "/initialize")
	public Voyage initialize() {
		return voyageService.initializeVoyage();
	}*/
	
	@GetMapping
	@RequestMapping(value = "/allvoyages")
	public List<Voyage> allVoyages() {// Tüm seferler.
		return voyageService.findAllVoyages();
	}
	
	@GetMapping
	@RequestMapping(value = "/findbydate")
	public List<Voyage> findVoyageByDate(@RequestBody VoyageFinderHelper helper) {// Tarihe bağlı seferler.
		return voyageService.findVoyageByDate(helper.getDate());
	}
	
	@GetMapping
	@RequestMapping(value = "/findbylocations")
	public List<Voyage> findVoyageByLocations(@RequestBody VoyageFinderHelper helper) {// Başlangıç bitişe bağlı seferler.
		return voyageService.findVoyageByLocations(helper.getFrom(), helper.getTo());
	}
	
	@PostMapping
	@RequestMapping(value = "/createvoyage")
	public Voyage createVoyage(@RequestBody CreateVoyageHelper helper) {// Sefer yaratımı.
		return voyageService.createVoyage(helper.getVoyage(), helper.getVehicleType());
	}
	
	@DeleteMapping
	@RequestMapping(value = "/deletevoyage")
	public void deleteVoyage(@RequestBody VoyageOperationHelper helper) {// Sefer silinmesi.
		voyageService.deleteVoyage(helper.getId());
	}
	
	@GetMapping
	@RequestMapping(value = "/ticketamount")
	public int findTicketAmount(@RequestBody VoyageOperationHelper helper) {// Sefer bilet miktarı.
		return voyageService.findTicketAmount(helper.getId());
	}
	
	@GetMapping
	@RequestMapping(value = "/totalcost")
	public int findCost(@RequestBody VoyageOperationHelper helper) {// Sefer masrafı.
		return voyageService.findTotalCostOfVoyage(helper.getId());
	}
	

	


}
