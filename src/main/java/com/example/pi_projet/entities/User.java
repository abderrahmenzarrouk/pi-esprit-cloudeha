package com.example.pi_projet.entities;




import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.*;


//import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "Nom")
    private String nom;
    @Column(name = "Prenom")
    private String prenom;
    @Column(name = "Email")
    private String email;
    @Column(name = "Age")
    private String age;
    @Column(name = "MDP")
    private String MDP;
    @Column(name = "TDCEchouées")
    private int TDCEchouees;
    @Column(name = "NombreRéclamationEducative",columnDefinition = "int default 0")
    private int nbrrec;
    @Column(name = "NombreRéclamationTechnique",columnDefinition = "int default 0")
    private int nbrrectec;

    @Column(name = "mdpoubliée", columnDefinition = "int default 0")
    private int mdpoubliée ;
    @Column(name = "telephone")
    private String tel;
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate derniercnx ;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate dateregistration ;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Groupe> groupeSet;

    @Enumerated(EnumType.STRING)
    private Specialite typespecialite;
    @OneToOne
    private Roles userRole;
    private boolean locked = false;
    private boolean enabled = false;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<items> item;


    public User(String nom, String prenom, String email, String age, String MDP,  Roles userRole, String tel,String specialite) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.age = age;
        this.MDP = MDP;
        this.userRole = userRole;
        this.tel = tel;

        this.typespecialite = Specialite.valueOf(specialite);


    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole().name());
//        return Collections.singletonList(authority);
        return List.of(new SimpleGrantedAuthority(userRole.getRole().name()));
    }

    @Override
    public String getPassword() {
        return MDP;
    }

    @Override
    public String getUsername() {
        return nom;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}