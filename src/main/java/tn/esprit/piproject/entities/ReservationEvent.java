package tn.esprit.piproject.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class ReservationEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRes;

    @PrePersist
    protected void onCreate() {
        dateRes = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        dateRes = new Date();
    }
    @ManyToOne
    @JoinColumn(name = "evenement_id")

    Evenement evenement;
}
