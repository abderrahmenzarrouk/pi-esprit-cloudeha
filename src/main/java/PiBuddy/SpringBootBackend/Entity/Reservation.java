package PiBuddy.SpringBootBackend.Entity;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    private LocalDateTime debutReservation;

    private LocalDateTime finReservation;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EtatReservation etat;

    @ManyToOne
    private Groupe groupe;

    @ManyToOne
    private Classe classe;


}