package com.forage.service;

import com.forage.model.Client;
import com.forage.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServ {

    @Autowired
    private ClientRepo clientRepo;

    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    public Client save(Client client) {
        return clientRepo.save(client);
    }

    public Optional<Client> findById(Long id) {
        return clientRepo.findById(id);
    }

    public Client update(Long id, Client clientDetails) {
        Optional<Client> clientOpt = clientRepo.findById(id);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            client.setNom(clientDetails.getNom());
            client.setContact(clientDetails.getContact());
            return clientRepo.save(client);
        }
        return null;
    }

    public void delete(Long id) {
        clientRepo.deleteById(id);
    }
}