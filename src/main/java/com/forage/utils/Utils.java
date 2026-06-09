package com.forage.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.forage.model.Statut;
import com.forage.model.StatutSigle;
import com.forage.model.Type;
import com.forage.repository.StatutRepository;

@Component
public class Utils {
    @Autowired
    private StatutRepository statutRepository;
    public StatutSigle getSigleFromType(Type type) {
        if(type.getLibelle().equalsIgnoreCase("forage")) {
            return StatutSigle.DEVIS_FORAGE_TERMINE;
        } else if(type.getLibelle().equalsIgnoreCase("etude")) {
            return StatutSigle.DEVIS_ETUDE_TERMINE;
        } 
        return StatutSigle.DEMANDE_CREEE;
    }
    public Integer getIdStatutFromSigle(StatutSigle sigle) {
        Map <StatutSigle, Integer> sigleToId = new HashMap<>();
        List <Statut> statuts = statutRepository.findAll();
        for(Statut statut : statuts) {
            sigleToId.put(statut.getSigle(), statut.getId());
        }
        return sigleToId.get(sigle);
    }
    public Integer getIdStatutFromType(Type type) {
        StatutSigle sigle = getSigleFromType(type);
        return getIdStatutFromSigle(sigle);
    }
    public void currentPageAndContent(Model model, String currentPage, String contentView) {
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("content", "/WEB-INF/views/" + contentView + ".jsp");
    }
    public void addErrorMessage(Model model, String message) {
        model.addAttribute("error", message);
    }    
    public void addSuccessMessage(Model model, String message) {
        model.addAttribute("success", message);
    }
    public void addSuccessMessage(RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute("success", message);
    }
    public long computeDurationWithOnlyWeekDaysAndWorkingHours(LocalDateTime previous, LocalDateTime current) {

        LocalTime debutWorkingHour = LocalTime.of(8, 0);
        LocalTime finWorkingHour   = LocalTime.of(16, 0);

        // ── Clamp PREVIOUS first (matches pseudo code order) ──────────────────

        // Fix #3 : compute helpers after each clamp, not once at the top
        // Fix #6 : use atStartOfDay() instead of _12_am_... naming
        LocalDateTime startOfDayPrevious = previous.toLocalDate().atStartOfDay();

        // before 8am → clamp to 8am same day
        if (previous.isAfter(startOfDayPrevious) && previous.isBefore(previous.with(debutWorkingHour))) {
            previous = previous.with(debutWorkingHour);
        }
        // after 16:00 → clamp to 8am next working day
        else if (previous.isAfter(previous.with(finWorkingHour))) {
            previous = nextWorkingDay(previous.toLocalDate().plusDays(1)).atTime(debutWorkingHour);
        }

        // ── Clamp CURRENT second (matches pseudo code order) ──────────────────

        // Fix #3 : recompute helper for current
        LocalDateTime startOfDayCurrent = current.toLocalDate().atStartOfDay();

        // after 16:00 → clamp to 16:00 same day
        if (current.isAfter(current.with(finWorkingHour))) {
            current = current.with(finWorkingHour);
        }
        // before 8am → clamp to 16:00 of last working day
        // Fix #2 : backtrack to last working day, not just previous calendar day
        else if (current.isAfter(startOfDayCurrent) && current.isBefore(current.with(debutWorkingHour))) {
            LocalDate lastWorking = lastWorkingDay(current.toLocalDate().minusDays(1));
            current = lastWorking.atTime(finWorkingHour);
        }

        // ── Walk day by day ───────────────────────────────────────────────────

        // Fix #5 : use long instead of Integer to avoid overflow and boxing
        long dureeTravaillee = 0;

        while (previous.toLocalDate().isBefore(current.toLocalDate().plusDays(1))) {

            int dayOfWeek = previous.getDayOfWeek().getValue(); // 1=Mon … 7=Sun

            if (dayOfWeek >= 1 && dayOfWeek <= 5) { // weekday only
                if (!previous.toLocalDate().equals(current.toLocalDate())) {
                    // Fix #4 : flipped order → no need for Math.abs
                    dureeTravaillee += Duration.between(previous, previous.with(finWorkingHour)).toMinutes();
                } else {
                    dureeTravaillee += Duration.between(previous, current).toMinutes();
                }
            }

            previous = previous.plusDays(1).with(debutWorkingHour);
        }

        return dureeTravaillee;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Returns the given date if it is a weekday, otherwise the next Monday. */
    public LocalDate nextWorkingDay(LocalDate date) {
        while (date.getDayOfWeek().getValue() > 5) { // Sat=6, Sun=7
            date = date.plusDays(1);
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
