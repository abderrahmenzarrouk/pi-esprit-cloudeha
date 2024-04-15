package com.example.pi_projet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("twilio")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class twilioConfiguration {
    private String accountSid = "ACec6a34dca20660fa21be49ff92e72ba2";
    private String authToken = "f8f772c2662a7e84c620f9f001e1d95d";
    private String trialNumber = "+13253137489";

}
