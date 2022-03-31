package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.dao.UserRepository;
import com.example.entites.User;

@Configuration
public class UserDetailsServiceImpl  implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.getUserByUserName(username);
		
		if(user == null)
		{
			throw new UsernameNotFoundException("Could not found user !!");
		}
		
		CustomeUserDetails customeUserDetails = new CustomeUserDetails(user);
		
		return customeUserDetails;
	}

}
