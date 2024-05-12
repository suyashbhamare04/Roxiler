package com.roxiler.assignment.entity;

public class PieChartData {

	 private String category;
	 private int numberOfItems;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getNumberOfItems() {
		return numberOfItems;
	}
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}
	public PieChartData(String category, int numberOfItems) {
		super();
		this.category = category;
		this.numberOfItems = numberOfItems;
	}
	
	 
	 
}
