package com.eventicket.Model;

import java.util.*;

public class Event {
	private String id;
	private String name;
	private String details;
	private int numberOfTickets;
	private List<Ticket> tickets;

	public Event(int idEvent, String name, String details) {
		this.name = name;
		this.details = details;
		this.numberOfTickets = 0;
		tickets = new ArrayList<Ticket>();
	}

	public Event(int idEvent, String name, String details, int numberOfTickets) {
		this.name = name;
		this.details = details;
		this.numberOfTickets = numberOfTickets;
		tickets = new ArrayList<Ticket>();
	}

	public Event() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public String toString() {
		return this.name + " " + this.details + ". Tickets left: " + this.numberOfTickets;
	}
}
