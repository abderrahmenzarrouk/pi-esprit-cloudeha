package com.example.pi_projet.service;

import com.example.pi_projet.email.EmailSender;
import com.example.pi_projet.entities.Groupe;
import com.example.pi_projet.entities.User;
import com.example.pi_projet.entities.items;
import com.example.pi_projet.registration.token.ConfirmationToken;
import com.example.pi_projet.registration.token.ConfirmationTokenService;
import com.example.pi_projet.repositories.GroupeRepository;
import com.example.pi_projet.repositories.ItemRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class userService implements UserDetailsService {

    private final userRepository userRepository;
    private final GroupeRepository groupeRepository;
    private final ItemRepository itemRepository;
    private final EmailSender emailSender;
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
       user.setNbrrec(0);
       user.setDerniercnx(LocalDate.now());
       user.setDateregistration(LocalDate.now());
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


    public User forgotpassword(String email){
        var user = userRepository.findByEmail(email).get();
        String randomPassword = PasswordGenerator.generateRandomPassword(5);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedrandompassword = passwordEncoder.encode(randomPassword);
        System.out.println(randomPassword);
        user.setMDP(encodedrandompassword);
        user.setMdpoubliée(1);
        userRepository.save(user);
        emailSender.send(email, buildEmail(user.getPrenom(), randomPassword));
        return user;
    }
    public User changepassword(String email , String newpass , String oldpass){
        var user = userRepository.findByEmail(email).get();
        String motdepasse = user.getMDP();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println(motdepasse);

            if(passwordEncoder.matches(oldpass, motdepasse) ){

                String encodedrandompassword = passwordEncoder.encode(newpass);
                user.setMDP(encodedrandompassword);
                user.setMdpoubliée(0);
                userRepository.save(user);
                return user;
            } else {
                throw new IllegalArgumentException("Incorrect old password");
            }

    }

    private String buildEmail(String name, String MDP) {
        return  "<table class=\"body-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\">\n" +
                "    <tbody>\n" +
                "        <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "            <td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\n" +
                "            <td class=\"container\" width=\"600\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; display: block !important; max-width: 600px !important; clear: both !important; margin: 0 auto;\"\n" +
                "                valign=\"top\">\n" +
                "                <div class=\"content\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; max-width: 600px; display: block; margin: 0 auto; padding: 20px;\">\n" +
                "                    <table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; border-radius: 3px; background-color: #fff; margin: 0; border: 1px solid #e9e9e9;\"\n" +
                "                        bgcolor=\"#fff\">\n" +
                "                        <tbody>\n" +
                "                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "                                <td class=\"\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 16px; vertical-align: top; color: #fff; font-weight: 500; text-align: center; border-radius: 3px 3px 0 0; background-color: #38414a; margin: 0; padding: 20px;\"\n" +
                "                                    align=\"center\" bgcolor=\"#71b6f9\" valign=\"top\">\n" +
                "                                    <a href=\"#\" style=\"font-size:32px;color:#fff;\"> Mot de passe oubliée ?</a> <br>\n" +
                "                                    <span style=\"margin-top: 10px;display: block;\">Cher/Chère "+name+"</span>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "                                <td class=\"content-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 20px;\" valign=\"top\">\n" +
                "                                    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "                                        <tbody>\n" +
                "                                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "                                                <td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                "                                                   Votre nouveaux mot de passe est : "+MDP+" \n" +
                "                                \n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "                                                <td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                "                                                   N'oubliée pas de le changer dès votre primère connection\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "                                                <td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                "                                                   " +
                "                                    </a>\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "                                                <td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                "                                                    \n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>\n" +
                "                    <div class=\"footer\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; clear: both; color: #999; margin: 0; padding: 20px;\">\n" +
                "                        <table width=\"100%\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "                            <tbody>\n" +
                "                                <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                "                                    <td class=\"aligncenter content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; vertical-align: top; color: #999; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\"><a href=\"#\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; color: #999; text-decoration: underline; margin: 0;\">Unsubscribe</a> from these alerts.\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "            <td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>";
    }








}
