package com.eventicket.Model;

import java.util.UUID;

public class OrderItem {
	private final String id;
	private String idOrder;
	private double price;
	private String idt;
	private String userId;

	public OrderItem() {
		id = null;
	}

	public OrderItem(String userId, String idt, double price) {
		id = (UUID.randomUUID()).toString();
		this.userId = userId;
		this.idt = idt;
		this.price = price;
	}

	public OrderItem(String id, String idOrder, double price, String idt, String userId) {
		this.id = id;
		this.idOrder = idOrder;
		this.price = price;
		this.idt = idt;
		this.userId = userId;
	}

	public String getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public String getIdt() {
		return idt;
	}

	public void setIdt(String idt) {
		this.idt = idt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String toString() {
		return id + " " + idOrder + " " + " " + idt + " " + price;
	}
}