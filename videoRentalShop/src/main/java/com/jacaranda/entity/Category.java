package com.jacaranda.entity;

import java.io.Serializable;

import com.jacaranda.entity.enums.CategoryType;

public class Category implements Serializable {
	
	// Atributos
	private static int idComun = 1;
	
	private int id;
	private CategoryType type;
	private String description;
	
	// Constructor
	public Category() {
		super();
		id = idComun++;
	}

	public Category(CategoryType type, String description) {
		super();
		this.type = type;
		this.description = description;
		id = idComun++;
	}

	// Métodos get y set
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public CategoryType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "{\n\tid : " + id + "\t\ntype : " + type + "\n\tdescription : " + description + "\n}";
	}
	
}
