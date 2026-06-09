package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forage.model.Alerte;

public interface AlerteRepository extends JpaRepository <Alerte, Integer> {
    
}
