package com.jacaranda.entity.comparator;

import java.util.Comparator;

import com.jacaranda.entity.Customer;

public class ComparatorCustomerId implements Comparator<Customer>{

	@Override
	public int compare(Customer c1, Customer c2) {
		return Integer.valueOf(c1.getId()).compareTo(c2.getId());
	}

}
