package com.forage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forage.model.Commune;
import com.forage.repository.CommuneRepository;
@Service
public class CommuneService {
    @Autowired
    private CommuneRepository communeRepository;

    public List <Commune> findAll() {
        return communeRepository.findAll();
    }
}
