package com.jacaranda.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Rental implements Serializable {
	
	// Constantes
	private final int DIAS_ALQUILER = 3;
	
	// Atributos
	private static int idComun = 1;
	
	private int id;
	private double price;
	private String startDate;
	private String dueDate;
	private Customer customer;
	private List<Film> films;
	
	// Constructor
	public Rental() {
		super();
		films = new ArrayList<Film>();
		id = idComun++;
	}

	public Rental(double price, String startDate, String dueDate, Customer customer) throws ParseException {
		super();
		this.price = price;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.customer = customer;
		films = new ArrayList<Film>();
		id = idComun++;
	}

	// Métodos get y set
	public double getPrice() {
		return price;
	}

	public String getStartDate() {
		return startDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getId() {
		return id;
	}

	public String getDueDate() {
		return dueDate;
	}

	public List<Film> getFilms() {
		return films;
	}

	@Override
	public String toString() {
		return "{\n\tid : " + id + "\n\tprice : " + price + "\n\tstartDate : " + startDate
				+ "\n\tdueDate : " + dueDate + "\n\tcustomer : " + customer + "\n\tfilms : " + films + "\n}";
	}
}