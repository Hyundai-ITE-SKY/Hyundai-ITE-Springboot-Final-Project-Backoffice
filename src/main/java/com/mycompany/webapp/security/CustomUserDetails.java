package com.mycompany.webapp.security;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {
	public CustomUserDetails(
			String mid, 
			String mpassword, 
			boolean menabled, 
			List<GrantedAuthority> mauthorities) {
		super(mid, mpassword, menabled, true, true, true, mauthorities);
	}
	
}

