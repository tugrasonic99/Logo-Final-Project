package com.traveladminapp.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.traveladminapp.models.AdminUser;
import com.traveladminapp.repository.AdminUserRepository;

@Service
public class AdminUserService {// Admin kullanıcı servisi. Giriş ve yeni üye işlemleri burada yapılıyor.
	// Hash işlemi SHA-256 ile yapıldı. 
	
	@Autowired
	private AdminUserRepository adminUserRepository;
	
	
	// Basit initializer. Eğer ilk veriyi bu fonksiyonla kaydetmek isterseniz buradaki ve controller'daki yorum satırlarını silin.
    /*
	public AdminUser initializeUser() {
		AdminUser ad1=new AdminUser();
		ad1.setEmail("abc@gmail.com");
		String sha256hex1 = Hashing.sha256()
				  .hashString("password1", StandardCharsets.UTF_8)
				  .toString();
		ad1.setPassword(sha256hex1);
		ad1.setName("Admin1");
		AdminUser ad2=new AdminUser();
		ad2.setEmail("abcd@gmail.com");
		String sha256hex2 = Hashing.sha256()
				  .hashString("password2", StandardCharsets.UTF_8)
				  .toString();
		ad2.setPassword(sha256hex2);
		ad2.setName("Admin1");
		adminUserRepository.save(ad1);
		return adminUserRepository.save(ad2);
	}*/
	
	public AdminUser login(String name, String password) {// Giriş işlemi
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		return adminUserRepository.findByNameAndPassword(name, sha256hex);
	}
	
	public AdminUser create(String name, String password, String email) {// Yeni üye işlemi.
		String sha256hex = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
		AdminUser newUser=new AdminUser();
		newUser.setEmail(email);
		newUser.setName(name);
		newUser.setPassword(sha256hex);
		return adminUserRepository.save(newUser);
	}
	

}
