package com.traveluserprofile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traveluserprofile.models.UserPhoneInbox;


@Repository
public interface UserPhoneInboxRepository extends JpaRepository<UserPhoneInbox, Integer> {
	
	Optional<UserPhoneInbox> findByPhoneNumber(String phoneNumber);
	
	boolean existsByPhoneNumber(String phoneNumber);

}
