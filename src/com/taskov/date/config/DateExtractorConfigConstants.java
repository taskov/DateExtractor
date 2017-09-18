package com.taskov.date.config;

import java.util.regex.Pattern;

public class DateExtractorConfigConstants {
	//used symbols
	public final static String OR = "|";
	public final static String DASH = "-";
	public final static String SPACE = " ";
	public final static String COMA = ",";
	
	//defining the possible format of days, months and years
	public final static String DAYS_FORMAT = "[0-9]{2}";
	public final static String MONTHS_FORMAT_ALPHABETIC = "(January|February|March|April|May|June|July|August|September|October|November|December)";
	public final static String MONTHS_FORMAT = "(January|February|March|April|May|June|July|August|September|October|November|December|\\d\\d)";
	public final static String YEARS_FORMAT = "\\d\\d\\d\\d";

	//possible day + month + year combinations 
	public final static String DATE_PATTERN_1 = DAYS_FORMAT + SPACE + MONTHS_FORMAT + SPACE	+ YEARS_FORMAT;
	public final static String DATE_PATTERN_2 = YEARS_FORMAT + DASH + MONTHS_FORMAT + DASH + DAYS_FORMAT;
	public final static String DATE_PATTERN_3 = MONTHS_FORMAT + SPACE + DAYS_FORMAT + COMA + SPACE + YEARS_FORMAT;
	
	//all patterns in a single string
	public final static String ALL_PATTERNS = DATE_PATTERN_1 + OR + DATE_PATTERN_2 + OR + DATE_PATTERN_3;
	

	//patterns
	public static Pattern allDatePatterns = Pattern.compile(ALL_PATTERNS);
	
	public static Pattern patternYearFormat = Pattern.compile(YEARS_FORMAT);
	public static Pattern patternMonthFormat = Pattern.compile(MONTHS_FORMAT_ALPHABETIC);
	public static Pattern patternDayFormat = Pattern.compile(DAYS_FORMAT);
	
	public static Pattern patternDateFormat1 = Pattern.compile(DATE_PATTERN_1);
	public static Pattern patternDateFormat2 = Pattern.compile(DATE_PATTERN_2);
	public static Pattern patternDateFormat3 = Pattern.compile(DATE_PATTERN_3);
	
}
