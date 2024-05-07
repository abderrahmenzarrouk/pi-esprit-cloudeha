package com.esprit.piproject.services;

import com.esprit.piproject.entities.ReservationEvent;
import com.esprit.piproject.repositories.IReservationEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
