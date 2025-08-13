package com.parking;

import java.io.Serializable;

public class ParkingSpace implements Serializable {
	private static final long serialVersionUID = 1L;

	private String spaceNumber;
	private String location;
	private double price;
	private boolean available;

	// Default constructor
	public ParkingSpace() {
	}

	// Constructor with parameters
	public ParkingSpace(String spaceNumber, String location, double price, boolean available) {
		this.spaceNumber = spaceNumber;
		this.location = location;
		this.price = price;
		this.available = available;
	}

	// Getters and Setters
	public String getSpaceNumber() {
		return spaceNumber;
	}

	public void setSpaceNumber(String spaceNumber) {
		this.spaceNumber = spaceNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
