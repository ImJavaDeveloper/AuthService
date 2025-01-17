package com.auth.service.service;

import com.auth.service.kafka.message.UserPasswordChangeEvent;
import com.auth.service.kafka.message.UserRegistrationEvent;

public interface UserProfileService {

    void saveUserProfile(UserRegistrationEvent userRegistrationEvent);
    void updateUserPassword(UserPasswordChangeEvent userPasswordChangeEvent);
}
