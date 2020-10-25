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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jacaranda.entity.Film;
import com.jacaranda.services.SearchService;
import com.jacaranda.services.SortService;
import com.jacaranda.services.UpdateService;

@RestController
@RequestMapping(path = "/api")
public class FilmController {

	// Inicializamos el array
	List<Film> films = new ArrayList<>();
	
	@Autowired
	private SearchService searchService;
	@Autowired
	private UpdateService updateService;
	@Autowired
	private SortService sortService;

	// GET
	// All films
	@GetMapping(path = "/film")
	public ResponseEntity<?> getFilms() {
		ResponseEntity<?> response;
		if (films.isEmpty()) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no movies");
		} else {
			sortService.orderFilms(films, "");
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body(films);
		}
		return response;
	}
	
	// All films sorted
		@GetMapping(path = "/filmOrdered")
		public ResponseEntity<?> getFilmsOrdered(@RequestParam(required = false) String param) {
			ResponseEntity<?> response;
			if (films.isEmpty()) {
				response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no movies");
			} else {
				sortService.orderFilms(films, param);
				response = ResponseEntity.status(HttpStatus.ACCEPTED).body(films);
			}
			return response;
		}

	// Film by Id
	@GetMapping(path = "/film/{id}")
	public ResponseEntity<?> getFilm(@PathVariable int id) {
		Film f = searchService.buscaFilm(films, id);
		ResponseEntity<?> response;
		if (f == null) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie doesn't exist");
		} else {
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body(f);
		}
		return response;
	}

	// POST
	@PostMapping(path = "/film")
	public ResponseEntity<?> addFilm(@RequestBody Film sent) {
		Film f = searchService.buscaFilm(films, sent.getOriginalTitle());
		ResponseEntity<?> response;
		if (f == null) {
			films.add(sent);
			response = ResponseEntity.status(HttpStatus.CREATED).body("Film created\n" + sent);
		} else {
			response = ResponseEntity.status(HttpStatus.CONFLICT).body("Film already exists");
		}
		return response;
	}

	// PUT
	@PutMapping(path = "/film/{id}")
	public ResponseEntity<?> updateFilm(@PathVariable int id, @RequestBody Film sent) {
		Film f = searchService.buscaFilm(films, id);
		ResponseEntity<?> response;
		if (f == null) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Film doesn't exist");
		} else {
			updateService.updateFilm(f, sent);
			response = ResponseEntity.status(HttpStatus.OK).body("Film updated\n" + f);
		}
		return response;
	}

	// DELETE
	@DeleteMapping(path = "/film/{id}")
	public ResponseEntity<?> deleteFilm(@PathVariable int id) {
		Film f = searchService.buscaFilm(films, id);
		ResponseEntity<?> response;
		if (f == null) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Film doesn't exist");
		} else {
			films.remove(f);
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body("Film deleted");
		}
		return response;
	}

}
