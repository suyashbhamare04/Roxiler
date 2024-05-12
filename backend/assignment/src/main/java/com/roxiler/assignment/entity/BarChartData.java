package com.roxiler.assignment.entity;




public class BarChartData {

	 private String priceRange;
	 private int numberOfItems;
	 
	public String getPriceRange() {
		return priceRange;
	}
	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
	public int getNumberOfItems() {
		return numberOfItems;
	}
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}
	public BarChartData(String priceRange, int numberOfItems) {
		super();
		this.priceRange = priceRange;
		this.numberOfItems = numberOfItems;
	}
	 
	 
	 
	
}
