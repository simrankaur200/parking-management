package com.parking;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

@ManagedBean(name = "searchParkingBean")
@ViewScoped
public class SearchParkingBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Search criteria fields
	private String locationInfo;
	private String parkingName;
	private String priceRange;

	// Search results
	private ArrayList<ParkingSpace> searchResults;
	private boolean searchPerformed = false;

	// Inject the existing parking space bean
	@ManagedProperty(value = "#{parkingSpaceBean}")
	private ParkingSpaceBean parkingSpaceBean;

	// Constructors
	public SearchParkingBean() {
		this.searchResults = new ArrayList<>();
	}

	// Search method
	public String searchParkingSpaces() {
		try {
			ArrayList<ParkingSpace> allSpaces = parkingSpaceBean.getParkingSpaces();

			if (allSpaces == null || allSpaces.isEmpty()) {
				addFacesMessage(FacesMessage.SEVERITY_INFO, "No Data", "No parking spaces available to search.");
				this.searchResults = new ArrayList<>();
				this.searchPerformed = true;
				return null;
			}

			// Start with all spaces
			ArrayList<ParkingSpace> filteredSpaces = new ArrayList<>(allSpaces);

			// Filter by location if provided
			if (locationInfo != null && !locationInfo.trim().isEmpty()) {
				filteredSpaces = filteredSpaces.stream()
						.filter(space -> space.getLocation() != null
								&& space.getLocation().toLowerCase().contains(locationInfo.toLowerCase().trim()))
						.collect(Collectors.toCollection(ArrayList::new));
			}

			// Filter by parking space name if provided
			if (parkingName != null && !parkingName.trim().isEmpty()) {
				filteredSpaces = filteredSpaces.stream()
						.filter(space -> space.getSpaceNumber() != null
								&& space.getSpaceNumber().toLowerCase().contains(parkingName.toLowerCase().trim()))
						.collect(Collectors.toCollection(ArrayList::new));
			}

			// Filter by price range if provided
			if (priceRange != null && !priceRange.trim().isEmpty()) {
				try {
					double maxPrice = Double.parseDouble(priceRange.trim());
					filteredSpaces = filteredSpaces.stream().filter(space -> space.getPrice() <= maxPrice)
							.collect(Collectors.toCollection(ArrayList::new));
				} catch (NumberFormatException e) {
					addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"Invalid price format. Please enter a valid number.");
					return null;
				}
			}

			this.searchResults = filteredSpaces;
			this.searchPerformed = true;

			if (searchResults.isEmpty()) {
				addFacesMessage(FacesMessage.SEVERITY_INFO, "No Results",
						"No parking spaces found matching your search criteria.");
			} else {
				addFacesMessage(FacesMessage.SEVERITY_INFO, "Search Complete",
						"Found " + searchResults.size() + " parking space(s) matching your criteria.");
			}

		} catch (Exception e) {
			addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"An error occurred while searching: " + e.getMessage());
			this.searchResults = new ArrayList<>();
		}

		return null; // Stay on the same page
	}

	// Clear search results and form
	public String clearSearch() {
		this.locationInfo = null;
		this.parkingName = null;
		this.priceRange = null;
		this.searchResults = new ArrayList<>();
		this.searchPerformed = false;
		return null;
	}

	// Helper method to add faces message (matches your existing pattern)
	private void addFacesMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			context.addMessage(null, new FacesMessage(severity, summary, detail));
		}
	}

	// Check if any search criteria is provided
	public boolean hasSearchCriteria() {
	    return (locationInfo != null && !locationInfo.trim().isEmpty())
	        || (parkingName != null && !parkingName.trim().isEmpty())
	        || (priceRange != null && !priceRange.trim().isEmpty());
	}
	
	// Get available spaces from search results
	public ArrayList<ParkingSpace> getAvailableSearchResults() {
		return searchResults.stream().filter(ParkingSpace::isAvailable)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	// Get occupied spaces from search results
	public ArrayList<ParkingSpace> getOccupiedSearchResults() {
		return searchResults.stream().filter(space -> !space.isAvailable())
				.collect(Collectors.toCollection(ArrayList::new));
	}


	// Existing Getters and Setters
	public String getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(String locationInfo) {
		this.locationInfo = locationInfo;
	}

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public ArrayList<ParkingSpace> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(ArrayList<ParkingSpace> searchResults) {
		this.searchResults = searchResults;
	}

	public boolean isSearchPerformed() {
		return searchPerformed;
	}

	public void setSearchPerformed(boolean searchPerformed) {
		this.searchPerformed = searchPerformed;
	}

	public ParkingSpaceBean getParkingSpaceBean() {
		return parkingSpaceBean;
	}

	public void setParkingSpaceBean(ParkingSpaceBean parkingSpaceBean) {
		this.parkingSpaceBean = parkingSpaceBean;
	}
}