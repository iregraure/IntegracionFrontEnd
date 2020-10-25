package com.jacaranda.entity.comparator;

import java.util.Comparator;

import com.jacaranda.entity.Film;

public class ComparatorFilmOriginalTitle implements Comparator<Film>{

	@Override
	public int compare(Film f1, Film f2) {
		return f1.getOriginalTitle().compareTo(f2.getOriginalTitle());
	}

}
