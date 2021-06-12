package com.dev.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dev.dto.ClientDTO;
import com.dev.entities.Client;
import com.dev.services.ClientService;
import com.dev.util.GeradorPDF;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {
	
	@Autowired
	public ClientService service;
	
	@GetMapping(value = "/print-report",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity customersReport() throws IOException {
		
        List<ClientDTO> customers =  service.findAll();
        
        

        ByteArrayInputStream bis = GeradorPDF.customerPDFReport(customers);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=customers.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
	

	@GetMapping
	public ResponseEntity<Page<ClientDTO>>findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction) ,orderBy);
		
		Page<ClientDTO> list = service.findAllPaged(pageRequest);
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "{id}")
	public ResponseEntity<ClientDTO>findById(@PathVariable Long id){
		ClientDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	
	
	
	
	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO dto) {
			dto = service.update(id, dto);
			return ResponseEntity.ok().body(dto);

	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id) {
			service.delete(id);
			return ResponseEntity.noContent().build();

	}


	
			
			
			
			
	
}
