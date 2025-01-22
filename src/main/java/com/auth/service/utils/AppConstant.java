package com.auth.service.utils;

import java.util.Arrays;
import java.util.List;

public class AppConstant {

    public final static List<String> allowedOrigins= List.of(
            "http://127.0.0.1:4200"
    );

    public final static List<String> allowedHeaders=List.of(

                    "Origin",
                    "Access-Control-Allow-Origin",
                    "Content-Type",
                    "Accept", "Authorization",
                    "X-Requested-With",
                    "Access-Control-Request-Method",
                    "Access-Control-Request-Headers",
                    "Access-Control-Allow-Headers"
    );

    public final static List<String> allowedMethods=List.of(
                    "GET", "POST", "PUT", "DELETE", "OPTIONS"
    );

    public final static List<String> allowedExposedHeaders=List.of(
            "Origin", "Content-Type", "Accept",
                    "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Access-Control-Allow-Credentials"
    );
}
