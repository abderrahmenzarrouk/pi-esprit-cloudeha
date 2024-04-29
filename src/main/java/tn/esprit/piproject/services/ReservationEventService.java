package tn.esprit.piproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.piproject.entities.Evenement;
import tn.esprit.piproject.entities.ReservationEvent;
import tn.esprit.piproject.repositories.IReservationEventRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationEventService implements IReservationEventService{
    private IReservationEventRepository iReservationEventRepository;
    @Override
    public List<ReservationEvent> getAllRes() {
        return iReservationEventRepository.findAll();
    }

    @Override
    public ReservationEvent retrieveRes(long id) {
        ReservationEvent reservationEvent=iReservationEventRepository.findById(id).get();
        return reservationEvent;
    }

    @Override
    public ReservationEvent addRes(ReservationEvent reservationEvent) {
        return iReservationEventRepository.save(reservationEvent);
    }

    @Override
    public ReservationEvent updateRes(ReservationEvent reservationEvent) {
        return iReservationEventRepository.save(reservationEvent);
    }

    @Override
    public void deleteRes(long id) {
      iReservationEventRepository.deleteById(id);
    }
}
