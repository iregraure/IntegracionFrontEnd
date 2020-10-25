package com.jacaranda.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jacaranda.entity.Category;
import com.jacaranda.entity.Customer;
import com.jacaranda.entity.Film;
import com.jacaranda.entity.Rental;
import com.jacaranda.entity.enums.CategoryType;

@Service
public class SearchService {
	
	public Customer buscaCustomer(List<Customer> customers, int id) {
		Customer c = null;
		Iterator<Customer> iterador = customers.iterator();
		boolean encontrado = false;
		while(iterador.hasNext() && !encontrado) {
			Customer cus = iterador.next();
			if(cus.getId() == id) {
				c = cus;
				encontrado = true;
			}
		}
		return c;
	}
	
	public Customer buscaCustomer(List<Customer> customers, String dni) {
		Customer c = null;
		Iterator<Customer> iterador = customers.iterator();
		boolean encontrado = false;
		while(iterador.hasNext() && !encontrado) {
			Customer cus = iterador.next();
			if(cus.getDni().equalsIgnoreCase(dni)) {
				c = cus;
				encontrado = true;
			}
		}
		return c;
	}
	
	public Film buscaFilm(List<Film> films, int id) {
		Film f = null;
		Iterator<Film> iterador = films.iterator();
		boolean encontrado = false;
		while (iterador.hasNext() && !encontrado) {
			Film film = iterador.next();
			if (film.getId() == id) {
				f = film;
				encontrado = true;
			}
		}
		return f;
	}
	
	public Film buscaFilm(List<Film> films, String titulo) {
		Film f = null;
		Iterator<Film> iterador = films.iterator();
		boolean encontrado = false;
		while (iterador.hasNext() && !encontrado) {
			Film film = iterador.next();
			if (film.getOriginalTitle().equalsIgnoreCase(titulo)) {
				f = film;
				encontrado = true;
			}
		}
		return f;
	}
	
	public Category buscaCategory(List<Category> categorias, int id) {
		Category c = null;
		Iterator<Category> iterador = categorias.iterator();
		boolean encontrado = false;
		while (iterador.hasNext() && !encontrado) {
			Category category = iterador.next();
			if (category.getId() == id) {
				c = category;
				encontrado = true;
			}
		}
		return c;
	}
	
	public Category buscaCategory(List<Category> categorias, CategoryType categoria) {
		Category c = null;
		Iterator<Category> iterador = categorias.iterator();
		boolean encontrado = false;
		while (iterador.hasNext() && !encontrado) {
			Category category = iterador.next();
			if (category.getType() == categoria) {
				c = category;
				encontrado = true;
			}
		}
		return c;
	}
	
	public Rental buscaRental(List<Rental> alquileres, int id) {
		Rental r = null;
		Iterator<Rental> iterador = alquileres.iterator();
		boolean encontrado = false;
		while (iterador.hasNext() && !encontrado) {
			Rental rental = iterador.next();
			if (rental.getId() == id) {
				r = rental;
				encontrado = true;
			}
		}
		return r;
	}
	
	public Rental buscaRental(List<Rental> alquileres, String fecha, Customer customer) {
		Rental r = null;
		Iterator<Rental> iterador = alquileres.iterator();
		boolean encontrado = false;
		while (iterador.hasNext() && !encontrado) {
			Rental rental= iterador.next();
			if (rental.getStartDate().equals(fecha) && rental.getCustomer() == customer) {
				r = rental;
				encontrado = true;
			}
		}
		return r;
	}
	
}
