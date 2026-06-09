package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forage.model.Statut;

public interface StatutRepository extends JpaRepository <Statut, Integer> {
    
}
