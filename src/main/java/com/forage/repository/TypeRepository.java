package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forage.model.Type;

public interface TypeRepository extends JpaRepository<Type, Integer> {
    
}
