package com.auth.service.service;

import com.auth.service.entity.UserCredential;
import com.auth.service.repository.UserCredRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredService implements UserDetailsService {

    private final UserCredRepository userCredRepository;
    public UserCredService(UserCredRepository userCredRepository)
    {
       this.userCredRepository=userCredRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = Optional.of(userCredRepository.findByUsername(username))
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImp.build(userCredential);
    }
}
