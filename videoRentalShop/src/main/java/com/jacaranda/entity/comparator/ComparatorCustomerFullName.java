package com.jacaranda.entity.comparator;

import java.util.Comparator;

import com.jacaranda.entity.Customer;

public class ComparatorCustomerFullName implements Comparator<Customer>{

	@Override
	public int compare(Customer c1, Customer c2) {
		return c1.getFullName().compareTo(c2.getFullName());
	}

}
