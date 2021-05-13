package com.dev.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.entities.Client;
import com.dev.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {
	
	@Autowired
	public ClientService service;

	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		List<Client> clients = service.findAll();
		return ResponseEntity.ok(clients);
	}
	
}
