package com.traveladminapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.google.common.hash.Hashing;
import com.traveladminapp.models.AdminUser;
import com.traveladminapp.repository.AdminUserRepository;

@SpringBootTest
class AdminUserServiceTest {// AdminUser test sınıfı. Coverage:100%
	
	@InjectMocks
	private AdminUserService adminUserService;
	
	@Mock
	private AdminUserRepository adminUserRepository;
	
	
	private AdminUser prepare() {
		AdminUser user=new AdminUser();
		user.setEmail("abcd@gmail.com");
		user.setName("Ahmet");
		String sha256hex1 = Hashing.sha256()
				  .hashString("password1", StandardCharsets.UTF_8)
				  .toString();
		user.setPassword(sha256hex1);
		return user;
	}
	
	
	@Test
	void it_shoud_create_user() {
		
		AdminUser user=prepare();
		
		Mockito.when(adminUserRepository.save(Mockito.any())).thenReturn(user);
		
		AdminUser response=adminUserService.create(user.getName(), user.getPassword(), user.getEmail());
		
		verify(adminUserRepository, times(1)).save(Mockito.any());
		
		assertThat(response.getName()).isEqualTo(user.getName());
		assertThat(response.getPassword()).isEqualTo(user.getPassword());
		assertThat(response.getEmail()).isEqualTo(user.getEmail());
	}
	
	@Test
	void it_shoud_login_user() {
		
		AdminUser user=prepare();
		
		String name="Ahmet";
		String sha256hex1 = Hashing.sha256()
				  .hashString("password1", StandardCharsets.UTF_8)
				  .toString();
		String password="password1";
		
		Mockito.when(adminUserRepository.findByNameAndPassword(user.getName(), user.getPassword())).thenReturn(user);
		
		//AdminUser response=adminUserService.create(user.getName(), user.getPassword(), user.getEmail());
		
		AdminUser response=adminUserService.login(name, password);
		
		verify(adminUserRepository, times(1)).findByNameAndPassword(name, sha256hex1);
		
		assertThat(response.getName()).isEqualTo(user.getName());
		assertThat(response.getPassword()).isEqualTo(user.getPassword());
		assertThat(response.getEmail()).isEqualTo(user.getEmail());
	}

}
