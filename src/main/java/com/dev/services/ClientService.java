package com.dev.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.entities.Client;
import com.dev.repositories.ClientRepositoy;

@Service
public class ClientService {
	
	@Autowired
	public ClientRepositoy repository;

	public List<Client> findAll() {
		
		return repository.findAll();
	}
	
	
	
	

}
