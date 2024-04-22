package com.example.pi_projet.service;

import com.example.pi_projet.entities.User;
import com.example.pi_projet.registration.token.ConfirmationToken;
import com.example.pi_projet.registration.token.ConfirmationTokenService;
import com.example.pi_projet.repositories.userRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@AllArgsConstructor
public class userService implements UserDetailsService {

    private final userRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final static String user_not_found_MSG = "utilisateur avec l email %s introuvable ";
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(user_not_found_MSG, email)));
    }
    public String signUpUser(User user, MultipartFile file){
        System.out.println("user");
        System.out.println(user);
       boolean userExists =  userRepository.findByEmail(user.getEmail()).isPresent();
       if(userExists){
           throw new IllegalStateException("utilisateur deja existant ");
       }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       System.out.println(fileName);

        if(fileName.contains(".."))
        {
            System.out.println("le fichier n'est pas valide");
        }
        try {
            user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedpassword = passwordEncoder.encode(user.getPassword());
      // String encodedpassword = bCryptPasswordEncoder.encode(user.getPassword());
       user.setMDP(encodedpassword);
       user.setTDCEchouees(0);
       userRepository.save(user);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
    return token;
    }
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }


    public void updateuser(User user, MultipartFile file){
        User userExists =  userRepository.findById(user.getId());
        if (!file.isEmpty()){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            System.out.println(fileName);

            if(fileName.contains(".."))
            {
                System.out.println("le fichier n'est pas valide");
            }
            try {
                user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(user.getTel()==null){
            user.setTel(userExists.getTel());
        }
        if(user.getNom()==null){user.setNom(userExists.getNom());}
        if(user.getPrenom()==null) {
            user.setPrenom(userExists.getPrenom());
        }
        if(user.getEmail()==null){user.setEmail(userExists.getEmail());}
        if(user.getAge()==null){user.setAge(userExists.getAge());}
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedpassword = passwordEncoder.encode(userExists.getPassword());
        user.setMDP(encodedpassword);
        user.setTDCEchouees(0);

        userRepository.save(user);
    }
    public void registerUserFromOAuth2(OAuth2AuthenticationToken authenticationToken) {
        OAuth2User oauth2User = authenticationToken.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String username = oauth2User.getAttribute("login"); // or any other unique identifier
        // Check if user already exists in your database

            // Create a new user entity
            User newUser = new User();
            newUser.setNom(username);
            newUser.setEmail(email);
            // You might set other user details here
            // For instance, assign roles or generate a random password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            newUser.setMDP(passwordEncoder.encode("123"));
            // Save the new user to the database
            userRepository.save(newUser);

        // User is now registered or updated in the database
    }




}
