package com.eventicket.Model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {
	private String id;
	@NotNull
	@Size(min = 3, max = 30)
	private String username;
	@NotNull
	@Size(min = 3, max = 30)
	private String firstName;
	@NotNull
	@Size(min = 3, max = 30)
	private String lastName;
	@NotNull
	private String phoneNumber;
	@NotNull
	@Size(min = 5, max = 30)
	private String password;
	@NotNull
	private String email;
	@NotNull
	private String address;

	public User() {
		id = null;
	}

	public User(String name, String passsword) {
		id = null;
		this.username = name;
		this.password = passsword;
	}

	public User(String id, String name, String password, String firstName, String lastName, String email,
			String address, String phoneNumber) {
		this.id = id;
		this.username = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.email = email;
		this.address = address;
	}

	public User(String id, String name, String password, String firstName, String lastName, String email,
			String address) {
		this.id = id;
		this.username = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return this.id + " " + this.username + " " + this.password + " " + this.firstName + " " + this.lastName + " "
				+ this.email + " " + this.address;
	}

	public String toInsert() {
		return "'" + this.username + "', '" + this.firstName + "', '" + this.lastName + "', '" + this.phoneNumber
				+ "', '" + this.password + "', '" + this.email + "', '" + this.address + "'";
	}
}
