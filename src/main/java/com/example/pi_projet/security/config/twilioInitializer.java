package com.example.pi_projet.security.config;

import com.example.pi_projet.twilioConfiguration;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class twilioInitializer {


    public static final String ACCOUNT_SID = "ACec6a34dca20660fa21be49ff92e72ba2";
    public static final String AUTH_TOKEN = "f8f772c2662a7e84c620f9f001e1d95d"; // Replace with your actual Auth Token

    @Autowired
    public twilioInitializer() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
}
