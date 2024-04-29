package com.example.pi_projet.service;

import com.example.pi_projet.controllers.AuthenticationRequest;
import com.example.pi_projet.controllers.AuthenticationResponse;
import com.example.pi_projet.repositories.userRepository;
import com.example.pi_projet.security.config.JwtService;
import com.example.pi_projet.security.config.SmsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final userRepository userRepository;
    private final twilioService twilioService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getMDP()
                    )
            );
            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            user.setTDCEchouees(0);

            userRepository.save(user);
//            String telephone = "+216" + user.getTel();
//            SmsRequest s  = new SmsRequest(telephone,"heelo");
//            twilioService.sendsms(s);

            var Token = JwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(Token)
                    .user(user)
                    .build();
        }catch (AuthenticationException e) {
            var faileduser = userRepository.findByEmail(request.getEmail());
            if (faileduser.isPresent()) {
                var user = faileduser.get();
                user.setTDCEchouees(user.getTDCEchouees() + 1);
                userRepository.save(user);
            }
            if(faileduser.get().getTDCEchouees() == 5){
                var user = faileduser.get();
                String randomPassword = PasswordGenerator.generateRandomPassword(5);
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedrandompassword = passwordEncoder.encode(randomPassword);
                System.out.println(randomPassword);
                user.setMDP(encodedrandompassword);
                userRepository.save(user);
                String telephone = "+216" + user.getTel();
                SmsRequest s  = new SmsRequest(telephone,"Cher utilisateur,\n" +
                        "\n" +
                        "Pour des raisons de sécurité, nous avons détecté plusieurs tentatives de connexion infructueuses à votre compte. Par mesure de précaution, nous avons changé votre mot de passe.\n" +
                        "\n" +
                        "Votre nouveau mot de passe est : " + randomPassword);
                twilioService.sendsms(s);

            }
            e.printStackTrace();
            throw e;
        }
    }
}
