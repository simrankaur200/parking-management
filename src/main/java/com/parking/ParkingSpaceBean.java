package com.parking;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ParkingSpaceBean implements Serializable {
	private static final long serialVersionUID = 1L;

	// Properties for the new parking space
	private String spaceNumber;
	private String location;
	private double price;

	// List to store all parking spaces
	private ArrayList<ParkingSpace> parkingSpaces;

	public ParkingSpaceBean() {
		parkingSpaces = new ArrayList<>();
		price = 0.0; // Default price
	}

	// Helper method to add faces message (handles null FacesContext when running
	// test)
	private void addFacesMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			context.addMessage(null, new FacesMessage(severity, summary, detail));
		}
	}

	// Method to add a new parking space
	public String addParkingSpace() {
		try {
			// Validate required fields
			if (spaceNumber == null || spaceNumber.trim().isEmpty()) {
				addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Space Number is required");
				return null;
			}

			if (location == null || location.trim().isEmpty()) {
				addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Location is required");
				return null;
			}

			if (price < 0.0) {
				addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Price must be a valid positive number");
				return null;
			}

			// Check if space number already exists
			for (ParkingSpace space : parkingSpaces) {
				if (space.getSpaceNumber().equals(spaceNumber.trim())) {
					addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Space Number already exists");
					return null;
				}
			}

			// Create new parking space using constructor
			ParkingSpace newSpace = new ParkingSpace(spaceNumber.trim(), location.trim(), price, true);

			// Add to list
			parkingSpaces.add(newSpace);

			// Clear form fields after successful addition
			spaceNumber = null;
			location = null;
			price = 0.0;

			// Success message
			addFacesMessage(FacesMessage.SEVERITY_INFO, "Parking Space Added Successfully",
					"Parking space added successfully");

			return "parking-list"; // Navigate to list page

		} catch (Exception e) {
			addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to add parking space: " + e.getMessage());
			return null;
		}
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

	public ArrayList<ParkingSpace> getParkingSpaces() {
		return parkingSpaces;
	}

	public void setParkingSpaces(ArrayList<ParkingSpace> parkingSpaces) {
		this.parkingSpaces = parkingSpaces;
	}
}