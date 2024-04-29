package tn.esprit.piproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Ou spécifiez les origines autorisées
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Autorisez les méthodes que vous utilisez
                .allowedHeaders("*"); // Ou spécifiez les en-têtes autorisés
    }
}
