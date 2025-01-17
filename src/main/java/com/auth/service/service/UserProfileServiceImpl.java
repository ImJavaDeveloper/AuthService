package com.auth.service.service;

import com.auth.service.entity.Role;
import com.auth.service.entity.UserCredential;
import com.auth.service.kafka.message.UserPasswordChangeEvent;
import com.auth.service.kafka.message.UserRegistrationEvent;
import com.auth.service.repository.RoleRepository;
import com.auth.service.repository.UserCredRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserProfileServiceImpl implements UserProfileService{

    private static final Logger log = LoggerFactory.getLogger(UserProfileServiceImpl.class);
    @Autowired
    UserCredRepository userCredRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public void saveUserProfile(UserRegistrationEvent userRegistrationEvent) {

        UserCredential credential=new UserCredential();
        credential.setUsername(userRegistrationEvent.getUsername());
        credential.setPassword(userRegistrationEvent.getPassword());
        Set<Role> roles=new HashSet<>();
        Role role=roleRepository.findByRoleId(2);
        roles.add(role);
        credential.setRoles(roles);
        userCredRepository.save(credential);

    }

    @Override
    public void updateUserPassword(UserPasswordChangeEvent userPasswordChangeEvent) {
        UserCredential userCredential=new UserCredential();
        userCredential.setUsername(userPasswordChangeEvent.getUsername());
        userCredential.setPassword(userPasswordChangeEvent.getPassword());
        userCredRepository.save(userCredential);
    }
}
