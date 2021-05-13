package com.dev.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dto.ClientDTO;
import com.dev.entities.Client;
import com.dev.repositories.ClientRepositoy;
import com.dev.services.exception.ClientNotFoundException;

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
	
	public ClientDTO findById(Long id) {
		Client client = repository
				.findById(id).orElseThrow( 
						() -> new ClientNotFoundException("Client not found"));
		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client client = repository
				.save(new Client(null, dto.getName(), dto.getCpf(), dto.getIncome(), dto.getBirthDate(), dto.getChildren()));
		
		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			
			Client entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity.setBirthDate(dto.getBirthDate());
			entity.setChildren(dto.getChildren());
			entity.setCpf(dto.getCpf());
			entity.setIncome(dto.getIncome());
			
			entity = repository.save(entity);
			return new ClientDTO(entity);
			
		}catch (EntityNotFoundException e){
			throw new ClientNotFoundException("Id not found");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {
			throw new ClientNotFoundException("Id not found");
		} 
		
	}
	
	
	
	
	
	

}
