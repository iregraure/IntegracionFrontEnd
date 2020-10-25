package com.jacaranda.entity.comparator;

import java.util.Comparator;

import com.jacaranda.entity.Film;

public class ComparatorFilmYear implements Comparator<Film>{
	
	@Override
	public int compare(Film f1, Film f2) {
		return Integer.valueOf(f1.getYear()).compareTo(f2.getYear());
	}
	
}
