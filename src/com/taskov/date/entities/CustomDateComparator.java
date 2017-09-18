package com.taskov.date.entities;

import java.util.Comparator;

public class CustomDateComparator implements Comparator<CustomDate>{
	
	@Override
	public int compare(CustomDate cd1, CustomDate cd2){
		if(cd1.getMonth() == cd2.getMonth()){
			if(cd1.getDay() > cd2.getDay()){
				return 1;
			} else {
				return -1;
			}
		}
		
		if(cd1.getMonth() > cd2.getMonth()){
			return 1;
		}
		return -1;
	}
}
