package com.jacaranda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacaranda.entity.Rental;
import com.jacaranda.services.SearchService;

@RestController
@RequestMapping(path = "/api")
public class RentalController {
	
	// Inicializamos el array
	List<Rental> alquileres= new ArrayList<>();
	
	@Autowired
	private SearchService searchService;

	// GET
	// All rentals
	@GetMapping(path = "/rental")
	public ResponseEntity<?> getRentals() {
		ResponseEntity<?> response;
		if (alquileres.isEmpty()) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no rentals");
		} else {
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body(alquileres);
		}
		return response;
	}

	// Rental by Id
	@GetMapping(path = "/rental/{id}")
	public ResponseEntity<?> getRental(@PathVariable int id) {
		Rental r = searchService.buscaRental(alquileres, id);
		ResponseEntity<?> response;
		if (r == null) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental doesn't exist");
		} else {
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body(r);
		}
		return response;
	}

	// POST
	@PostMapping(path = "/rental")
	public ResponseEntity<?> addRental(@RequestBody Rental sent) {
		Rental r = searchService.buscaRental(alquileres, sent.getStartDate(), sent.getCustomer());
		ResponseEntity<?> response;
		if (r == null) {
			alquileres.add(sent);
			response = ResponseEntity.status(HttpStatus.CREATED).body("Rental created\n" + sent);
		} else {
			response = ResponseEntity.status(HttpStatus.CONFLICT).body("Rental already exists");
		}
		return response;
	}

}
