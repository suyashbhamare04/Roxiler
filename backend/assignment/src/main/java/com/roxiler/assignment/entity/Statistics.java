package com.roxiler.assignment.entity;

public class Statistics {

	 private int totalSaleAmount;
	 private int totalSoldItems;
	 private int totalNotSoldItems;
	public int getTotalSaleAmount() {
		return totalSaleAmount;
	}
	public void setTotalSaleAmount(int totalSaleAmount) {
		this.totalSaleAmount = totalSaleAmount;
	}
	public int getTotalSoldItems() {
		return totalSoldItems;
	}
	public void setTotalSoldItems(int totalSoldItems) {
		this.totalSoldItems = totalSoldItems;
	}
	public int getTotalNotSoldItems() {
		return totalNotSoldItems;
	}
	public void setTotalNotSoldItems(int totalNotSoldItems) {
		this.totalNotSoldItems = totalNotSoldItems;
	}
	public Statistics(int totalSaleAmount, int totalSoldItems, int totalNotSoldItems) {
		super();
		this.totalSaleAmount = totalSaleAmount;
		this.totalSoldItems = totalSoldItems;
		this.totalNotSoldItems = totalNotSoldItems;
	}
	 
	 
	
}
