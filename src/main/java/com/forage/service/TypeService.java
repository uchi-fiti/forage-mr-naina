package com.forage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.forage.model.Type;
import com.forage.repository.TypeRepository;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;
    public List<Type> findAll() {
        return typeRepository.findAll();
    }
    public Type findById(@NonNull Integer idType) {
        return typeRepository.findById(idType).orElse(null);
    }
}
