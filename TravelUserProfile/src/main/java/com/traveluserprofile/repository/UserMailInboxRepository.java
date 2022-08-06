package com.traveluserprofile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.traveluserprofile.models.UserMailInbox;

@Repository
public interface UserMailInboxRepository  extends JpaRepository<UserMailInbox, Integer> {
	
	
	
	Optional<UserMailInbox> findByEmail(String email);

}
