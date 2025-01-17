package com.auth.service.kafka.listener;


import com.auth.service.entity.UserCredential;
import com.auth.service.kafka.event.Event;
import com.auth.service.kafka.event.EventType;
import com.auth.service.kafka.message.UserPasswordChangeEvent;
import com.auth.service.kafka.message.UserRegistrationEvent;
import com.auth.service.repository.UserCredRepository;
import com.auth.service.service.UserProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserProfileKafkaEventListener {

    //@Value("${spring.kafka.topic}")
    private final static String topic = "user-profile";
    @Autowired
    UserProfileService  userProfileService;

    @Autowired
    UserCredRepository userCredRepository;


    @KafkaListener(topics = topic,groupId = "auth-service")
    public void processUserProfile(String message) throws JsonProcessingException {
      log.info("Processing User Profile Message");
        ObjectMapper mapper=new ObjectMapper();
        Event event=mapper.readValue(message,Event.class);
        log.info(event.toString());
        if(event.getEvent().equals(EventType.USER_REGISTRATION.getValue()))
        {
            UserRegistrationEvent userRegistrationEvent=mapper.readValue(message, UserRegistrationEvent.class);
            log.info(userRegistrationEvent.toString());
            userProfileService.saveUserProfile(userRegistrationEvent);
        }

        if(event.getEvent().equals(EventType.USER_PASSWORD_CHANGE.getValue()))
        {
            UserPasswordChangeEvent userPasswordChangeEvent=mapper.readValue(message, UserPasswordChangeEvent.class);
            log.info(userPasswordChangeEvent.toString());
            userProfileService.updateUserPassword(userPasswordChangeEvent);
        }

    }
}
