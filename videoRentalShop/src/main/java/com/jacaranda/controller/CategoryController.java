package com.jacaranda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacaranda.entity.Category;
import com.jacaranda.services.SearchService;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {
	
	// Inicializamos el array
	List<Category> categorias = new ArrayList<>();
	
	@Autowired
	private SearchService searchService;

	// GET
	// All category
	@GetMapping(path = "/category")
	public ResponseEntity<?> getCategories() {
		ResponseEntity<?> response;
		if (categorias.isEmpty()) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no categories");
		} else {
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body(categorias);
		}
		return response;
	}

	// Category by Id
	@GetMapping(path = "/category/{id}")
	public ResponseEntity<?> getFilm(@PathVariable int id) {
		Category c = searchService.buscaCategory(categorias, id);
		ResponseEntity<?> response;
		if (c == null) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category doesn't exist");
		} else {
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body(c);
		}
		return response;
	}

	// POST
	@PostMapping(path = "/category")
	public ResponseEntity<?> addCategory(@RequestBody Category sent) {
		Category c = searchService.buscaCategory(categorias, sent.getType());
		ResponseEntity<?> response;
		if (c == null) {
			categorias.add(sent);
			response = ResponseEntity.status(HttpStatus.CREATED).body("Category created\n" + sent);
		} else {
			response = ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists");
		}
		return response;
	}

	// DELETE
	@DeleteMapping(path = "/category/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable int id) {
		Category c = searchService.buscaCategory(categorias, id);
		ResponseEntity<?> response;
		if (c == null) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category doesn't exist");
		} else {
			categorias.remove(c);
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Category deleted");
		}
		return response;
	}

}
