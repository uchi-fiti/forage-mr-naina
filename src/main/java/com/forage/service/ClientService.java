package com.forage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forage.model.Client;
import com.forage.repository.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    
    public List <Client> findAll() {
        return clientRepository.findAll();
    }
}
