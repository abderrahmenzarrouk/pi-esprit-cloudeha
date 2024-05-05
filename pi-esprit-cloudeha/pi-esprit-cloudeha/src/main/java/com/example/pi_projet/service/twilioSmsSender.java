package com.example.pi_projet.service;

import com.example.pi_projet.security.config.SmsRequest;
import com.example.pi_projet.security.config.smsSender;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

@Service
public class twilioSmsSender implements smsSender {
    @Override
    public void sendsms(SmsRequest smsRequest) {
        try {
            PhoneNumber to = new PhoneNumber(smsRequest.getTel());
            PhoneNumber from = new PhoneNumber("+13253137489");
            String message = smsRequest.getSmsmessage();

            MessageCreator messageCreator = Message.creator(
                    to,
                    new PhoneNumber("+13253137489"),
                    message
            );
            messageCreator.create();
        }catch (ApiException e) {
            // Log detailed information about the exception
            System.err.println("Error sending SMS: " + e.getMessage());
            // Consider logging other details like the 'to' and 'from' numbers
        }
    }
}
