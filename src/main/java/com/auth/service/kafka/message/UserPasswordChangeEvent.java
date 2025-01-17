package com.auth.service.kafka.message;

import lombok.Data;

@Data
public class UserPasswordChangeEvent {

    private String username;
    private String password;
    private String event;
}
