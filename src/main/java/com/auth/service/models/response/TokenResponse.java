package com.auth.service.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

   private String token;
   private boolean isAuthenticated;
   private String username;
}
