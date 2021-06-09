package com.eventicket.Model;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

public class Ticket {
	@NotNull
	private String id;
	private final String idt;
	@NotNull
	@Size(min = 2, max = 20)
	private String typet;
	@NotNull
	@NumberFormat
	private double price;
	@NotNull
	@NumberFormat
	private int quantity;

	public Ticket() {
		idt = null;
	}

	public Ticket(String idt, String eventID, String type, double price, int quantity) {
		this.idt = idt;
		this.id = eventID;
		this.price = price;
		this.typet = type;
		this.quantity = quantity;
	}

	public Ticket(String eventID, String type, double price, int quantity) {
		this.idt = UUID.randomUUID().toString();
		this.id = eventID;
		this.price = price;
		this.typet = type;
		this.quantity = quantity;
	}

	public Ticket(double price, String type, String eventID) {
		this.idt = UUID.randomUUID().toString();
		this.id = eventID;
		this.price = price;
		this.typet = type;
	}

	public String getIdt() {
		return idt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getTypet() {
		return typet;
	}

	public void setTypet(String type) {
		this.typet = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String toString() {
		return this.id + " " + this.typet + " " + this.price + " " + quantity + " " + idt;
	}
}
