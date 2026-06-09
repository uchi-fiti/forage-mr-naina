package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forage.model.Devis;

public interface DevisRepository extends JpaRepository<Devis, Integer> {
}
