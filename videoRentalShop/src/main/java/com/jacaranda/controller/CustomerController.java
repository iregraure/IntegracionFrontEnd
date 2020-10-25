package com.jacaranda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jacaranda.entity.Customer;
import com.jacaranda.services.SearchService;
import com.jacaranda.services.SortService;
import com.jacaranda.services.UpdateService;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*")
public class CustomerController {

	// Inicializamos el array
	private List<Customer> customers = new ArrayList<>();
	
	// Creamos el servicio
	@Autowired
	private SearchService searchService;
	@Autowired
	private UpdateService updateService;
	@Autowired
	private SortService sortService;
	
	// GET
	// All customers
	@GetMapping(path="/customer")
	public ResponseEntity<?> getCustomers(){
		ResponseEntity<?> response;
		if(customers.isEmpty()) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no customers");
		}
		else {
			customers = sortService.orderCustomers(customers, "");
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body(customers);
		}
		return response;
	}
	
	// All customers sorted
		@GetMapping(path="/customerOrdered")
		public ResponseEntity<?> getCustomersOrdered(@RequestParam(required = false) String param){
			ResponseEntity<?> response;
			if(customers.isEmpty()) {
				response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no customers");
			}
			else {
				customers = sortService.orderCustomers(customers, param);
				response = ResponseEntity.status(HttpStatus.ACCEPTED).body(customers);
			}
			return response;
		}
	
	// Customer by Id
	@GetMapping(path="/customer/{id}")
	public ResponseEntity<?> getCustomer(@PathVariable int id){
		Customer c = searchService.buscaCustomer(customers, id);
		ResponseEntity<?> response;
		if(c == null) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer don't exist");
		}
		else {
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body(c);
		}
		return response;
	}
	
	// POST
	@PostMapping(path="/customer")
	public ResponseEntity<?> addCustomer(@RequestBody Customer sent){
		Customer c = searchService.buscaCustomer(customers, sent.getDni());
		ResponseEntity<?> response;
		if(c == null) {
			customers.add(sent);
			response = ResponseEntity.status(HttpStatus.CREATED).body("Customer created\n" + sent);
		}
		else {
			response = ResponseEntity.status(HttpStatus.CONFLICT).body("Customer already exists");
		}
		return response;
	}
	
	// PUT
	@PutMapping(path="/customer/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody Customer sent){
		Customer c = searchService.buscaCustomer(customers, id);
		ResponseEntity<?> response;
		if(c == null) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer doesn't exist");
		}
		else {
			updateService.updateCustomer(c, sent);
			response = ResponseEntity.status(HttpStatus.OK).body("Customer updated\n" + c);
		}
		return response;
	}
	
	// DELETE
	@DeleteMapping(path="/customer/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int id){
		Customer c = searchService.buscaCustomer(customers, id);
		ResponseEntity<?> response;
		if(c == null) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer doesn't exist");
		}
		else {
			customers.remove(c);
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Customer deleted");
		}
		return response;
	}
	
}
