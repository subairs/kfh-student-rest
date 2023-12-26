package com.kfh.education.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kfh.education.entity.Role;
import com.kfh.education.entity.User;
import com.kfh.education.repository.UserRepository;

@Service
public class CustomUserService  implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
//		return org.springframework.security.core.userdetails.User.builder()
//				.username(user.getUsername())
//				.password(user.getPassword())
//				.roles("USER").build();
//		
		
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				getAuthorities(user.getRoles()));
	}
	
	private Set<GrantedAuthority> getAuthorities(Set<Role> roles){
		Set<GrantedAuthority> authorities =new HashSet<>();
		for(Role role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		}
		return authorities;
	}

}