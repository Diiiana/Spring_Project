package com.eventicket.Model;

import java.util.*;

public class Orderc {
	private final String id;
	private String userId;
	private String orderItemID;
	private List<OrderItem> myTickets;
	private double total;

	public Orderc() {
		id = null;
	}

	public Orderc(String userID, double price) {
		UUID iUuid = UUID.randomUUID();
		this.id = iUuid.toString();
		this.userId = userID;
		this.total = 0;
		this.myTickets = new ArrayList<OrderItem>();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderItemID() {
		return orderItemID;
	}

	public void setOrderItemID(String orderItemID) {
		this.orderItemID = orderItemID;
	}

	public String getId() {
		return id;
	}

	public List<OrderItem> getMyTickets() {
		return myTickets;
	}

	public void setMyTickets(List<OrderItem> myTickets) {
		this.myTickets = myTickets;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String toString() {
		return "id ='" + this.id + "', userId='" + userId + "', orderItemId='" + orderItemID + "', total="
				+ this.getTotal();
	}
}
