package com.sygec.sygec.serviceImpl;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sygec.sygec.mapper.Mapper;
import com.sygec.sygec.model.Admin;
import com.sygec.sygec.repository.AdminRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	  Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

	    @Autowired
	    AdminRepository adminRepository;
	    

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Optional<Admin> findByEmail = adminRepository.findByEmail(username);
	        if (findByEmail.isPresent()) {
	            logger.info("user found {}", findByEmail.get().getMotDePass());
	            return (Mapper.toUserDetails(findByEmail.get()));
	        }
	        throw new UsernameNotFoundException("User not found with username: " + username);
	    }
}
