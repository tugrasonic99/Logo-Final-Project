package com.travelmainapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelmainapp.enummodels.VehicleType;
import com.travelmainapp.models.Voyage;

@Repository
public interface VoyageRepository extends  JpaRepository<Voyage, Integer> {
	
    List<Voyage> findByDate(String date);
	
	
	List<Voyage> findByStartAndDestination(String start, String destination);
	
	
	
	
	
	
	




}