package com.forage.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.forage.model.Demande;
public interface DemandeRepository extends JpaRepository <Demande, String> {
    
}
