package com.auth.service.service;

import com.auth.service.entity.UserCredential;
import com.auth.service.repository.UserCredRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserCredService implements UserDetailsService {

    private UserCredRepository userCredRepository;
    public UserCredService(UserCredRepository userCredRepository)
    {
       this.userCredRepository=userCredRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential =userCredRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImp.build(userCredential);
    }
}
