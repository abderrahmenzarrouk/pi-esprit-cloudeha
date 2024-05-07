package PiBuddy.SpringBootBackend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ressources")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ressources {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRessource;
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @Enumerated(EnumType.STRING)
    private Typeressource typeressource;
    @ManyToMany(mappedBy = "ressources", fetch = FetchType.EAGER)
    private List<Classe> classes;
}
