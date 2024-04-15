package com.example.pi_projet.service;

import com.example.pi_projet.security.config.SmsRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class twilioService {
    private final twilioSmsSender twilioSmsSender;
    public void sendsms(SmsRequest smsRequest){
        twilioSmsSender.sendsms(smsRequest);
    }
}
