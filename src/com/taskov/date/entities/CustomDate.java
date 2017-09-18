package com.taskov.date.entities;

public class CustomDate {
	
	private int year;
	private int month;
	private int day;
	private int occurencies;
	
	public CustomDate(){
		
	}
	
	public CustomDate(int year, int month, int day){
		this.year = year;
		this.month = month;
		this.day = day;
		this.occurencies = 1;
	}
	
	public int getYear(){
		return year;
	}
	
	public void setYear(int year){
		this.year = year;
	}
	
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getOccurencies() {
		return occurencies;
	}

	public void setOccurencies(int occurencies) {
		this.occurencies = occurencies;
	}


	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if(!(o instanceof CustomDate)) {
			return false;
		}
		CustomDate customDate = (CustomDate) o;
		return this.day == customDate.getDay() && this.month == customDate.getMonth(); 
	}
	
	@Override
	public int hashCode(){
		return this.day + this.month;
	}
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		return str.toString();
	}

}
