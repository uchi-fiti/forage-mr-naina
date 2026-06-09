package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forage.model.Client;

public interface ClientRepository extends JpaRepository <Client, Integer> {
    
}
