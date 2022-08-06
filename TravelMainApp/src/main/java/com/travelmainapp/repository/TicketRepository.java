package com.travelmainapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import com.travelmainapp.models.Ticket;

@Repository
public interface TicketRepository extends  JpaRepository<Ticket, Integer> {

}
