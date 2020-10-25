package com.jacaranda.entity;

import java.io.Serializable;
import java.util.List;

public class Film implements Serializable, Comparable<Film> {
	
	// Atributos
	private static int idComun = 1;
	
	private int id;
	private String originalTitle;
	private String spanishTitle;
	private int year;
	private List<String> actors;
	private boolean rented;
	private int duration;
	private int categoryId;
	
	// Constructor
	public Film() {
		super();
		id = idComun++;
		rented = false;
	}

	public Film(String originalTitle, String spanishTitle, int year, List<String> actors, int duration, int categoryId) {
		super();
		this.originalTitle = originalTitle;
		this.spanishTitle = spanishTitle;
		this.year = year;
		this.actors = actors;
		this.duration = duration;
		id = idComun++;
		rented = false;
		this.categoryId = categoryId;
	}

	public String getSpanishTitle() {
		return spanishTitle;
	}

	public void setSpanishTitle(String spanishTitle) {
		this.spanishTitle = spanishTitle;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public boolean isRented() {
		return rented;
	}

	public void setRented(boolean rented) {
		this.rented = rented;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getId() {
		return id;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	@Override
	public int compareTo(Film f1) {
		int resul = this.getOriginalTitle().compareTo(f1.getOriginalTitle());
		if (resul == 0) {
			resul = Integer.valueOf(this.getYear()).compareTo(f1.getYear());
		}
		return resul;
	}

	@Override
	public String toString() {
		return "{\n\tid: " + id + "\n\toriginalTitle: " + originalTitle + "\n\tspanishTitle: " + spanishTitle + "\n\tyear: "
				+ year + "\n\tactors :" + actors + "\n\trented :" + rented + "\n\tduration :" + duration + "\n\tcategoryId :"
				+ categoryId + "\n}";
	}
		
}
