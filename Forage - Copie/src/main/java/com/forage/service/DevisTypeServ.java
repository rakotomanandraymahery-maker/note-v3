package com.forage.service;

import com.forage.model.DevisType;
import com.forage.repository.DevisTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevisTypeServ {

    @Autowired
    private DevisTypeRepo devisTypeRepository;

    public List<DevisType> findAll() {
        return devisTypeRepository.findAll();
    }
    
    public DevisType findById(Long id) {
        return devisTypeRepository.findById(id).orElse(null);
    }
}