package com.dev.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dev.dto.ClientDTO;
import com.dev.entities.Client;
import com.dev.repositories.ClientRepositoy;

@Service
public class ClientService {
	
	@Autowired
	public ClientRepositoy repository;

	public List<ClientDTO> findAll() {
		List<ClientDTO> dtos = new ArrayList<>();
		List<Client> clients = repository.findAll();
		clients.forEach(x -> dtos.add(new ClientDTO(x)));
		return dtos;
	}

	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
		
	}
	
	
	
	
	
	

}
