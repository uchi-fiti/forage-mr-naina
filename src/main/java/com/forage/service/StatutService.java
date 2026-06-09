package com.forage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.forage.model.Statut;
import com.forage.repository.StatutRepository;

@Service
public class StatutService {
    @Autowired
    private StatutRepository statutRepository;
    public Statut findById(@NonNull Integer idStatut) {
        return statutRepository.findById(idStatut).orElseThrow();
    }
    public List <Statut> findAll() {
        return statutRepository.findAll();
    }
}
