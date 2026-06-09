package com.forage.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forage.model.Demande;
import com.forage.model.DemandeStatut;
import com.forage.model.Statut;
import com.forage.repository.DemandeStatutRepository;
import com.forage.repository.StatutRepository;
import com.forage.utils.Utils;

@Service
public class DemandeStatutService {
    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private DemandeStatutRepository demandeStatutRepository;
    @Autowired
    private Utils utils;
    public DemandeStatut save(Demande demande, LocalDateTime date) {
        Iteger idStatut = utils.getIdStatutFromSigle(com.forage.model.StatutSigle.DEMANDE_CREEE);
        Statut statut = statutRepository.findById(idStatut).orElseThrow();
        DemandeStatut demandeStatut = new DemandeStatut();
        demandeStatut.setDemande(demande);
        demandeStatut.setDateChangementStatut(date != null ? date : LocalDateTime.now());
        demandeStatut.setStatut(statut);
        
        return demandeStatutRepository.save(demandeStatut);
    }
    public DemandeStatut save(@NonNull DemandeStatut demandeStatut) {
        return demandeStatutRepository.save(demandeStatut);
    }
    public DemandeStatut findById(Integer idDemandeStatut) {
        return demandeStatutRepository.findById(idDemandeStatut).orElseThrow();
    }
    public List<DemandeStatut> findAll() {
        return demandeStatutRepository.findAll();
    }
    public void deleteById(Integer idDemandeStatut) {
        demandeStatutRepository.deleteById(idDemandeStatut);
    }
    public DemandeStatut getCurrentStatut(Demande demande) {
        return demandeStatutRepository.findTopByDemandeOrderByDateChangementStatutDesc(demande);
    }
    public DemandeStatut findPreviousDemandeStatut(DemandeStatut ds) {
        DemandeStatut d = demandeStatutRepository.findTopByDemandeAndDateChangementStatutBeforeOrderByDateChangementStatutDesc(ds.getDemande(), ds.getDateChangementStatut());
        if(d != null && ds.getId() == d.getId()) {
            d = demandeStatutRepository.findTopByDemandeAndDateChangementStatutBeforeOrderByDateChangementStatutDesc(d.getDemande(), d.getDateChangementStatut());
        }
        return d;
    }
    public long calculerDureeTravaillee(DemandeStatut ds) {
        DemandeStatut previous = findPreviousDemandeStatut(ds);
        if(previous == null) {
            return 0;
        }

        return computeDurationWithOnlyWeekDaysAndWorkingHours(previous.getDateChangementStatut(), ds.getDateChangementStatut());
    }
    public List <DemandeStatut[]> getIntervallesDemandeStatutByDemande(Demande demande) {
        List <DemandeStatut[]> retour = new ArrayList<>();
        List <DemandeStatut> demandeStatuts = demandeStatutRepository.findByDemande(demande);
        for (DemandeStatut demandeStatut : demandeStatuts) {
            for(DemandeStatut ds : demandeStatuts) {
                if(demandeStatut.getStatut().getId() < ds.getStatut().getId()) {
                    DemandeStatut [] intervalle = new DemandeStatut[2];
                    intervalle[0] = demandeStatut;
                    intervalle[1] = ds;
                    retour.add(intervalle);
                }
            }
        }
        return retour;
    }
    public long calculerDureeTravaille(DemandeStatut ds1, DemandeStatut ds2) {
        return computeDurationWithOnlyWeekDaysAndWorkingHours(ds1.getDateChangementStatut(), ds2.getDateChangementStatut());
    }
    public long calculerDureeTotaleTravaillee(Demande demande) {
        List<DemandeStatut> list = demandeStatutRepository.findByDemandeOrderByDateChangementStatutAsc(demande);
        long totale = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            totale += calculerDureeTravaille(list.get(i), list.get(i+1));
        }
        return totale;
    }
    public List<DemandeStatut> findFollowingDemandeStatuts(DemandeStatut ds) {
        return demandeStatutRepository.findByDemandeAndDateChangementStatutAfter(ds.getDemande(), ds.getDateChangementStatut());
    }
    @Transactional
    public void saveWithDureeTravailleeUpdate(DemandeStatut ds) {
        long dureeTravaillee = calculerDureeTravaillee(ds);
        ds.setDureeTravaillee(dureeTravaillee);
        save(ds);
        List <DemandeStatut> followingDemandeStatuts = findFollowingDemandeStatuts(ds);
        for(DemandeStatut d : followingDemandeStatuts) {
            long dt = calculerDureeTravaillee(d);
            d.setDureeTravaillee(dt);
            save(d);
        }
    }
    public long computeDurationWithOnlyWeekDaysAndWorkingHours(
        LocalDateTime previous,
        LocalDateTime current) {

    if (previous == null || current == null) {
        return 0;
    }

    if (previous.isAfter(current)) {
        throw new IllegalArgumentException(
                "previous must be before current");
    }

    LocalTime debutWorkingHour = LocalTime.of(8, 0);
    LocalTime finWorkingHour   = LocalTime.of(16, 0);

    LocalDateTime start = previous;
    LocalDateTime end   = current;

    if (start.toLocalTime().isBefore(debutWorkingHour)) {
        start = start.toLocalDate().atTime(debutWorkingHour);
    } else if (start.toLocalTime().isAfter(finWorkingHour)) {
        LocalDate nextWorking =
                nextWorkingDay(start.toLocalDate().plusDays(1));

        start = nextWorking.atTime(debutWorkingHour);
    }

    i (start.getDayOfWeek().getValue() > 5) {
        start = nextWorkingDay(start.toLocalDate())
                .atTime(debutWorkingHour);
    }

    if (end.toLocalTime().isAfter(finWorkingHour)) {
        end = end.toLocalDate().atTime(finWorkingHour);
    } else if (end.toLocalTime().isBefore(debutWorkingHour)) {
        LocalDate lastWorking =
                lastWorkingDay(end.toLocalDate().minusDays(1));

        end = lastWorking.atTime(finWorkingHour);
    }

    i (end.getDayOfWeek().getValue() > 5) {
        end = lastWorkingDay(end.toLocalDate())
                .atTime(finWorkingHour);
    }

    if (start.isAfter(end)) {
        return 0;
    }

    long dureeTravaillee = 0;

    LocalDate currentDay = start.toLocalDate();
    LocalDate endDay     = end.toLocalDate();

    while (!currentDay.isAfter(endDay)) {

        if (currentDay.getDayOfWeek().getValue() <= 5) {

            LocalDateTime dayStart;
            LocalDateTime dayEnd;

            if (currentDay.equals(start.toLocalDate())) {
                dayStart = start;
            } else {
                dayStart = currentDay.atTime(debutWorkingHour);
            }

            if (currentDay.equals(end.toLocalDate())) {
                dayEnd = end;
            } else {
                dayEnd = currentDay.atTime(finWorkingHour);
            }

            if (dayStart.isBefore(dayEnd)) {
                dureeTravaillee += Duration
                        .between(dayStart, dayEnd)
                        .toMinutes();
            }
        }

        currentDay = currentDay.plusDays(1);
    }

    return dureeTravaillee;
}

    /** Returns the given date if it is a weekday, otherwise the next Monday. */
    public LocalDate nextWorkingDay(LocalDate date) {
            date = date.plusDays(1)
        }
        return date;
    }

    /** Returns the given date if it is a weekday, otherwise the previous Friday. */
    public LocalDate lastWorkingDay(LocalDate date) {
        while (date.getDayOfWeek().getValue() > 5) {
            date = date.minusDays(1);
        }
        return date;
    }
}
