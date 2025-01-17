package com.auth.service.kafka.event;

import lombok.Getter;

@Getter
public enum EventType {

    USER_REGISTRATION("USER_REGISTRATION"),
    USER_PASSWORD_CHANGE("USER_PASSWORD_CHANGE");
    final String value;
    EventType(String value)
    {
        this.value=value;
    }

}
