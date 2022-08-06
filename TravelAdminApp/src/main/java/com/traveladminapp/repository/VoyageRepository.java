package com.traveladminapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.traveladminapp.models.Voyage;


@Repository
public interface VoyageRepository extends  JpaRepository<Voyage, Integer> {
	
	
	List<Voyage> findByDate(String date);
	
	
	List<Voyage> findByStartAndDestination(String start, String destination);

}
