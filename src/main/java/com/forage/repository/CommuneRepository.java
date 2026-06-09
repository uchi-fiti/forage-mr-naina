package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forage.model.Commune;

public interface CommuneRepository extends JpaRepository <Commune, Integer>{
    
}
