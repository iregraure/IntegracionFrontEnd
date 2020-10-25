package com.jacaranda.services;

import org.springframework.stereotype.Service;

import com.jacaranda.entity.Customer;
import com.jacaranda.entity.Film;

@Service
public class UpdateService {

	public void updateCustomer (Customer original, Customer sent) {
		original.setFullName((sent.getFullName() == null) ? original.getFullName() : sent.getFullName());
		original.setAddress((sent.getAddress() == null) ? original.getAddress() : sent.getAddress());
		original.setPhoneNumber((sent.getPhoneNumber() == null) ? original.getPhoneNumber() : sent.getPhoneNumber());
		original.setBirthDate((sent.getBirthDate() == null) ? original.getBirthDate() : sent.getBirthDate());
	}
	
	public void updateFilm(Film original, Film sent) {
		original.setSpanishTitle((sent.getSpanishTitle() == null) ? original.getSpanishTitle() : sent.getSpanishTitle());
		original.setYear((sent.getYear() == 0) ? original.getYear() : sent.getYear());
		original.setActors((sent.getActors() == null) ? original.getActors() : sent.getActors());
		original.setDuration((sent.getDuration() == 0) ? original.getDuration() : sent.getDuration());
		original.setCategoryId((sent.getCategoryId() == 0) ? original.getCategoryId() : sent.getCategoryId());
	}
	
}
