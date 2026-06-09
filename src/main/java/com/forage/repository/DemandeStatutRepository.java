package com.forage.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forage.model.Demande;
import com.forage.model.DemandeStatut;

public interface DemandeStatutRepository extends JpaRepository <DemandeStatut, Integer>{
    DemandeStatut findTopByDemandeAndDateChangementStatutBeforeOrderByDateChangementStatutDesc(Demande demmande, LocalDateTime dateChangementStatut);

    List <DemandeStatut> findByDemandeAndDateChangementStatutAfter(Demande demande, LocalDateTime dateChangementStatut);
    List <DemandeStatut> findByDemandeAndDateChangementStatutAfterOrderByDateChangementStatutAsc(Demande demande, LocalDateTime dateChangementStatut);
    List <DemandeStatut> findByDemande(Demande demande);
    DemandeStatut findTopByDemandeOrderByDateChangementStatutDesc(Demande demande);
    List <DemandeStatut> findByDemandeOrderByDateChangementStatutAsc(Demande demande);
}
