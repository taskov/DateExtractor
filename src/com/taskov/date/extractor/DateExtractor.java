package com.taskov.date.extractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import com.taskov.date.config.DateExtractorConfigConstants;
import com.taskov.date.config.DateFormatIndexConstants;
import com.taskov.date.entities.CustomDate;
import com.taskov.date.entities.CustomDateComparator;
import com.taskov.date.exceptions.WrongFormatException;

public class DateExtractor {

	/* The purpose of this program is to extract 
	 * possible formats: 
	 * 	1) 20 May 2014 
	 *  2) January 27, 2016 
	 *  3) 2016-04-07 
	 */

		//new data structure to keep the dates in
		static HashMap<Integer, HashMap<CustomDate,Integer>> datesCollection = new HashMap<>();
		
		public void extractDates(String text) throws WrongFormatException {
			Matcher allDatesPattern = DateExtractorConfigConstants.allDatePatterns.matcher(text);
			while (allDatesPattern.find()) {
				parseTheDate(allDatesPattern.group(0));
			}
			printDates();
		}
		

		private int getDateFormat(String date){
			int dateFormat = 0;
			Matcher patternDateFormat1Matcher = DateExtractorConfigConstants.patternDateFormat1.matcher(date);
			if(patternDateFormat1Matcher.find()){
				dateFormat = 1;
				return dateFormat;
			}
			
			Matcher patternDateFormat2Matcher = DateExtractorConfigConstants.patternDateFormat2.matcher(date);
			if(patternDateFormat2Matcher.find()){
				dateFormat = 2;
				return dateFormat;
			}
			
			Matcher patternDateFormat3Matcher = DateExtractorConfigConstants.patternDateFormat3.matcher(date);
			if(patternDateFormat3Matcher.find()){
				dateFormat = 3;
				return dateFormat;
			}
			
			return dateFormat;
		}
		
		private CustomDate getDate(int format, String date) throws WrongFormatException {
			CustomDate customDate = new CustomDate();
			Matcher monthMatcher = DateExtractorConfigConstants.patternMonthFormat.matcher(date);
			String current;
			switch(format){
				case 1: 
					Matcher patternDateFormat1Matcher = DateExtractorConfigConstants.patternDateFormat1.matcher(date);
					patternDateFormat1Matcher.find();
					current = patternDateFormat1Matcher.group(0);
					monthMatcher.find();
					customDate.setMonth(convertMonthToInteger(monthMatcher.group(0)));
					customDate.setYear(Integer.parseInt(current.substring(current.length() - 4, current.length())));
					customDate.setDay(Integer.parseInt(current.substring(DateFormatIndexConstants.DATE_1_FORMAT_DAYS_START_INDEX,DateFormatIndexConstants.DATE_1_FORMAT_DAYS_END_INDEX)));
				break;
				case 2:
					Matcher patternDateFormat2Matcher = DateExtractorConfigConstants.patternDateFormat2.matcher(date);
					patternDateFormat2Matcher.find();
					current = patternDateFormat2Matcher.group(0);
					customDate.setYear(Integer.parseInt(current.substring(DateFormatIndexConstants.DATE_2_FORMAT_YEAR_START_INDEX, DateFormatIndexConstants.DATE_2_FORMAT_YEAR_END_INDEX)));
					customDate.setMonth(Integer.parseInt(current.substring(DateFormatIndexConstants.DATE_2_FORMAT_MONTH_START_INDEX, DateFormatIndexConstants.DATE_2_FORMAT_MONTH_END_INDEX)));
					customDate.setDay(Integer.parseInt(current.substring(DateFormatIndexConstants.DATE_2_FORMAT_DAYS_START_INDEX,DateFormatIndexConstants.DATE_2_FORMAT_DAYS_END_INDEX)));
				break;
				case 3:
					Matcher patternDateFormat3Matcher = DateExtractorConfigConstants.patternDateFormat3.matcher(date);
					patternDateFormat3Matcher.find();
					current = patternDateFormat3Matcher.group(0);
					monthMatcher.find();
					customDate.setMonth(convertMonthToInteger(monthMatcher.group(0)));
					customDate.setYear(Integer.parseInt(current.substring(current.length() - 4, current.length())));
					String dayPart = current.split(",")[0];
					customDate.setDay(Integer.parseInt(dayPart.substring(dayPart.length() - 2, dayPart.length())));
				break;
				default:
					throw new WrongFormatException("Unknown format");
			}
			return customDate;
		}
		
		private void parseTheDate (String date) throws WrongFormatException {
			int dateFormat = getDateFormat(date);
			CustomDate customDate = getDate(dateFormat,date);
			addDate(customDate);
		}
		
		
		private void addDate(CustomDate date){
			int occurencies = 1;
			int year = date.getYear();
			HashMap<CustomDate, Integer> datesMap = new HashMap<>();
			if(!datesCollection.containsKey(year)){
				datesMap.put(date,occurencies);
				datesCollection.put(year, datesMap);
			} else {
				datesMap = datesCollection.get(year);	
				if(!datesMap.containsKey(date)){
					datesMap.put(date, occurencies);
					datesCollection.put(year, datesMap);
				} else {
					occurencies = datesMap.get(date);
					occurencies += 1;
					datesMap.put(date, occurencies);
				}
			}
		}
		
		private Integer convertMonthToInteger(String month){
			switch(month){
				case "January": return 1;
				case "February": return 2;
				case "March": return 3;
				case "April": return 4;
				case "May": return 5;
				case "June": return 6;
				case "July": return 7;
				case "August": return 8;
				case "September": return 9;
				case "October": return 10;
				case "November": return 11;
				case "December": return 12;
			}
			return Integer.parseInt(month);
		}
		
		private void printDates(){
			List<Integer> sortedYears = new ArrayList<Integer>();
			Set<Integer> years = datesCollection.keySet();
			List<CustomDate> sortedDates = new ArrayList<CustomDate>();
			sortedYears.addAll(years);
			Collections.sort(sortedYears);
			HashMap<CustomDate, Integer> datesInAnYear = new HashMap<CustomDate, Integer>();
			for(int i = 0; i < sortedYears.size(); i++){
				datesInAnYear = datesCollection.get(sortedYears.get(i));
				sortedDates = new ArrayList<CustomDate>();
				sortedDates.addAll(datesInAnYear.keySet());
				Collections.sort(sortedDates, new CustomDateComparator());
				for(int j = 0; j < sortedDates.size(); j++){
					System.out.println("Year: " + sortedYears.get(i) + " Month: " + sortedDates.get(j).getMonth() + " Day: "  + sortedDates.get(j).getDay() + " Occurencies in the text: " + datesInAnYear.get(sortedDates.get(j)));
				}
			}
		}
		
		public static void main(String[] args) throws WrongFormatException {
			String text = "Marvin Lee Minsky at the Mathematics Genealogy Project; 2013-07-20 Marvin Lee Minsky at the AI Genealogy Project. {reprint 18 September 2011) "
					+ "Personal page for Marvin Minsky\". web.media.mit.edu. Retrieved 23 June 2016."
					+ "Admin (January 27, 2016). \"Official Alcor Statement Concerning Marvin Minsky\". "
					+ "Alcor Life Extension Foundation. Retrieved 2016-04-07. "
					+ "IEEE Computer Society Magazine Honors Artificial Intelligence Leaders\"."
					+ "DigitalJournal.com. August 24, 2011. Retrieved September 18, 2011. "
					+ "Press release source: PRWeb (Vocus). "
					+ "Dan David prize 2014 winners\". May 15, 2014. Retrieved May 20, 2014.\"";
			DateExtractor de = new DateExtractor();
			de.extractDates(text);
		}
	}

	

