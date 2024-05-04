package com.example.pi_projet.service;

import com.example.pi_projet.email.EmailSender;
import com.example.pi_projet.entities.Reclamation;
import com.example.pi_projet.entities.User;
import com.example.pi_projet.repositories.reclamationRepository;
import com.example.pi_projet.repositories.userRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class reclamationService {
    reclamationRepository reclamationRepository;
    userRepository userRepository;
    private final EmailSender emailSender;
    @Transactional
    public Reclamation addReclamation(Reclamation reclamation) {
        Date d = new Date();
        reclamation.setDatereclamation(d);
        User u = userRepository.findById(reclamation.getUser().getId());
        reclamation.setUser(u);
        if(reclamation.getTypeReclamtion().name().equals("Technique")){
            List<User> users = userRepository.findAll();
            List<User> tuteurUsers = users.stream()
                    .filter(user -> user.getUserRole().getRole().name().equals("Tuteur"))
                    .toList();
            Random random = new Random();
            String tuteuremail = tuteurUsers.get(random.nextInt(tuteurUsers.size())).getEmail();
            Optional<User> tuteurchoisit = userRepository.findByEmail(tuteuremail);
            tuteurchoisit.get().setNbrrectec(tuteurchoisit.get().getNbrrectec()+1);
            emailSender.send(tuteuremail, buildEmail(tuteurchoisit.get().getPrenom(),reclamation.getDescription()));
            reclamation.setTuteurchoisit(tuteuremail);

        }
        if(reclamation.getTypeReclamtion().name().equals("Educative") ){
            System.out.println("reclamation --------------------");
            System.out.println(reclamation.getTuteurchoisit());
            String tuteuremail = reclamation.getTuteurchoisit();
            Optional<User> tuteurchoisit = userRepository.findByEmail(tuteuremail);
            tuteurchoisit.get().setNbrrec(tuteurchoisit.get().getNbrrec()+1);
            emailSender.send(tuteuremail, buildEmaileducative(tuteurchoisit.get().getPrenom(),reclamation.getDescription()));

        }
        return reclamationRepository.save(reclamation);
    }





    private String buildEmail(String name,String description) {
       return
               "<table class=\"body-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\">\n" +
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
                       "                                    <a href=\"#\" style=\"font-size:32px;color:#fff;\"> Urgent : Assistance nécessaire pour résoudre un problème technique</a> <br>\n" +
                       "                                    <span style=\"margin-top: 10px;display: block;\">Cher/Chère "+name+"</span>\n" +
                       "                                </td>\n" +
                       "                            </tr>\n" +
                       "                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                       "                                <td class=\"content-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 20px;\" valign=\"top\">\n" +
                       "                                    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                       "                                        <tbody>\n" +
                       "                                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                       "                                                <td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                       "                                                    Nous vous écrivons pour vous informer d'un problème survenu dans notre système. \n" +
                       "                                "+description+"\n" +
                       "                                                </td>\n" +
                       "                                            </tr>\n" +
                       "                                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                       "                                                <td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                       "                                                    En tant que membre précieux de notre équipe et personne possédant une expertise nous sollicitons votre aide pour résoudre cette affaire. Votre savoir-faire est crucial pour aborder le problème de manière rapide et efficace.\n" +
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
                       "                                                    Nous vous remercions pour votre attention à cette affaire, et nous attendons avec impatience votre réponse et votre collaboration rapide dans la résolution du problème.\n" +
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



    private String buildEmaileducative(String name,String description) {
        return
                "<table class=\"body-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\">\n" +
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
                        "                                    <a href=\"#\" style=\"font-size:32px;color:#fff;\"> Urgent : Réclamation émise à votre encontre.</a> <br>\n" +
                        "                                    <span style=\"margin-top: 10px;display: block;\">Cher/Chère "+name+"</span>\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                        "                                <td class=\"content-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 20px;\" valign=\"top\">\n" +
                        "                                    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                        "                                        <tbody>\n" +
                        "                                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                        "                                                <td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                        "                                                    Nous vous écrivons pour vous informer qu'une réclamation a été émise à votre encontre. \n" +
                        "                                "+description+"\n" +
                        "                                                </td>\n" +
                        "                                            </tr>\n" +
                        "                                            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\">\n" +
                        "                                                <td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\">\n" +
                        "                                                    Nous vous informons également que cette réclamation a été enregistrée. En cas de réclamations futures multiples à votre encontre, vous pourriez être interrogé par l'administration. Nous vous encourageons à résoudre les problèmes de manière moderne avec vos étudiants afin de garantir une année éducative réussie pour vous et vos étudiants.\n" +
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
                        "                                                    Nous vous remercions pour votre travail continu avec nous et pour votre engagement à résoudre les problèmes de manière collaborative. Nous sommes convaincus que votre approche moderne contribuera à une expérience éducative enrichissante pour tous. Merci de votre attention à cette question et de votre collaboration.\n" +
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
