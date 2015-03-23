package com.floorcrasher.common;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class ConventionSearchParser {
	
	private static Logger logger = Logger.getLogger(ConventionSearchParser.class);
	
	private static final Pattern YEAR_PATTERN = Pattern.compile(RegexConstants.YEAR_4);
	private static final Pattern MONTH_PATTERN = Pattern.compile(RegexConstants.MONTH);
	private static final Pattern DAY_PATTERN = Pattern.compile(RegexConstants.DAY);
	private static final Pattern WORD_PATTERN = Pattern.compile(RegexConstants.WORD);
	
	private static final Pattern DATE_PATTERN = Pattern.compile(RegexConstants.DATE);
	private static final Pattern DATE_RANGE_PATTERN = Pattern.compile(RegexConstants.DATE_RANGE);
	private static final Pattern DATE_RANGE_MONTH_DAY_PATTERN = Pattern.compile(RegexConstants.DATE_RANGE_MONTH_DAY);
	
	//These will store the various kinds of input. The date parsing methods may refer to these in order to make parsing decisions.
	private Set<String> yearBucket = new LinkedHashSet<String>();
	private Set<String> monthBucket = new LinkedHashSet<String>();
	private Set<String> wordBucket = new LinkedHashSet<String>();
	private Set<String> dateRangeBucket = new LinkedHashSet<String>();
	private Set<String> dateBucket = new LinkedHashSet<String>();
	
	private boolean hasSoloMonth = false;
	private boolean hasSoloYear = false;
	
	//Visible attributes.
	private Date startDate;
	private Date endDate;
	private Set<String> keywords = new LinkedHashSet<String>();

	public ConventionSearchParser(){}
	public ConventionSearchParser(String searchText){
		this.parseSearch(searchText);
	}
	
	public void parse(String searchText){
		this.parseSearch(searchText);
	}
	
	private void parseSearch(String searchText){
		
		this.sortSearchTextIntoBuckets(searchText);
		
		//Parse the first two available dates. Give precedence to the date ranges.
		//If there are no dates or date ranges detected, parse no dates.
		TreeSet<Calendar> parsedDates = this.parseDatesFromBuckets();
		
		//Add all 'words' to the keywords list.
		this.keywords.addAll(wordBucket);
		
		//Declare the earliest date as the start date and the second date as the end date.
		this.setStartDateEndDate(parsedDates);
	}
	
	private TreeSet<Calendar> parseDatesFromBuckets(){
		TreeSet<Calendar> parsedDates = null;
		if(!dateRangeBucket.isEmpty()){
			Iterator<String> i = dateRangeBucket.iterator();
			if(i.hasNext()){
				parsedDates = this.parseDateRange(i.next());
			}
		} else if(!dateBucket.isEmpty()){
			parsedDates = this.parseDateBucket();
		} else{
			parsedDates = new TreeSet<Calendar>();
		}
		
		return parsedDates;
	}
	
	private void sortSearchTextIntoBuckets(String searchText){
		searchText = searchText.trim().toLowerCase();
		
		//Analyze the search text for key patterns.
		Matcher yearMatcher = YEAR_PATTERN.matcher(searchText);
		Matcher monthMatcher = MONTH_PATTERN.matcher(searchText);
		Matcher wordMatcher = WORD_PATTERN.matcher(searchText);
		Matcher dateRangeMatcher = DATE_RANGE_PATTERN.matcher(searchText);
		Matcher dateMatcher = DATE_PATTERN.matcher(searchText);
		while(yearMatcher.find()){
			this.yearBucket.add(yearMatcher.group());
		}
		while(monthMatcher.find()){
			this.monthBucket.add(monthMatcher.group());
		}
		while(wordMatcher.find()){
			this.wordBucket.add(wordMatcher.group());
		}
		while(dateRangeMatcher.find()){
			this.dateRangeBucket.add(dateRangeMatcher.group());
		}
		while(dateMatcher.find()){
			this.dateBucket.add(dateMatcher.group());
		}
	}
	
	private void setStartDateEndDate(TreeSet<Calendar> parsedDates){
		if(parsedDates != null){
			Iterator<Calendar> i = parsedDates.iterator();
			if(i.hasNext()){
				this.setStartDate(i.next());
			}
			if(i.hasNext()){
				this.setEndDate(i.next());
			}
		}
	}
	
	private TreeSet<Calendar> parseDateBucket() {
		
		if(this.dateBucket == null || this.dateBucket.isEmpty()){
			return new TreeSet<Calendar>();
		}
		
		TreeSet<Calendar> parsedDates = this.getTwoDatesFromDateBucket();
		
		if(parsedDates.size() == 2){
			//If one of the parsed dates was simply a year AND there are two parsed dates,
			//Set the SECOND date in the parsed date list as the last day of the given year IF
			//AND ONLY IF it is the solo year given. The first date given should stay untouched
			//because a given year is parsed as the first day of the year.
			if(this.hasSoloYear){
				parsedDates = this.handleSoloYear(parsedDates);
			}
			
			//If one of the parsed dates was simply a month AND there are two parsed dates,
			//Set the SECOND date in the parsed date list as the last day of the given year IF
			//AND ONLY IF it is the solo month given. The first date given should stay untouched
			//because a given month is parsed as the first day of the month.
			if(this.hasSoloMonth){
				parsedDates = this.handleSoloMonth(parsedDates);
			}
		}
		
		return parsedDates;
	}
	
	private TreeSet<Calendar> handleSoloYear(TreeSet<Calendar> parsedDates){
		//Make sure the iterator begins with the latest date.
		Iterator<Calendar> i = parsedDates.descendingIterator();
		Calendar lastDate = null;
		Calendar firstDate = null;
		if(i.hasNext()){
			Calendar calendar = i.next();
			
			//Set the latest date as the last day of the year if and only if the latest date was simply a year.
			//There is a bug here, though. If the first date given was simply a year and the second date is
			//explicitly the first date of a year, then the second date will be overridden as the last date of
			//the year.
			if(calendar.getActualMinimum(Calendar.MONTH) == calendar.get(Calendar.MONTH)){
				if(calendar.getActualMinimum(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)){
					calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
					calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				}
			}
			
			lastDate = calendar;
		}
		
		//Store the earlier date.
		if(i.hasNext()){
			firstDate = i.next();
		}
		
		//Clear the parsed dates. Repopulate the set with the first date and last date.
		parsedDates.clear();
		if(firstDate != null){
			parsedDates.add(firstDate);
		}
		if(lastDate != null){
			parsedDates.add(lastDate);
		}
		
		return parsedDates;
	}
	
	private TreeSet<Calendar> handleSoloMonth(TreeSet<Calendar> parsedDates){
		Iterator<Calendar> i = parsedDates.descendingIterator();
		Calendar lastDate = null;
		Calendar firstDate = null;
		if(i.hasNext()){
			Calendar calendar = i.next();
			
			if(calendar.getActualMinimum(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)){
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
			
			lastDate = calendar;
		}
		
		if(i.hasNext()){
			firstDate = i.next();
		}
		
		parsedDates.clear();
		if(firstDate != null){
			parsedDates.add(firstDate);
		}
		if(lastDate != null){
			parsedDates.add(lastDate);
		}
		
		return parsedDates;
	}
	
	private TreeSet<Calendar> getTwoDatesFromDateBucket(){
		//Create the buckets for the various types of dates.
		Set<String> dateDMYBucket = new LinkedHashSet<String>();
		Set<String> date224Bucket = new LinkedHashSet<String>();
		Set<String> date422Bucket = new LinkedHashSet<String>();
		Set<String> dateLongBucket = new LinkedHashSet<String>();
		Set<String> dateYearBucket = new LinkedHashSet<String>();
		
		//Now, sort all the provided dates into their respective buckets.
		this.dateBucket.forEach((dateStr) ->{
			if(Pattern.matches(RegexConstants.DATE_DMY, dateStr)){
				dateDMYBucket.add(dateStr);
			} else if(Pattern.matches(RegexConstants.DATE_224, dateStr)){
				date224Bucket.add(dateStr);
			} else if(Pattern.matches(RegexConstants.DATE_422, dateStr)){
				date422Bucket.add(dateStr);
			} else if(Pattern.matches(RegexConstants.DATE_LONG, dateStr)){
				dateLongBucket.add(dateStr);
			} else if(Pattern.matches(RegexConstants.DATE_YEAR, dateStr)){
				dateYearBucket.add(dateStr);
			}
		});
		
		//Store the dates, as Calendar objects, in a TreeSet to sort them automatically.
		TreeSet<Calendar> parsedDates = new TreeSet<Calendar>();
		
		//Create a delegate that will parse only two dates total no matter how many times it's called.
		Consumer<String> dateParser = dateStr -> {
			if(parsedDates.size() < 2){
				parsedDates.add(this.parseDateString(dateStr));
			}
		};
		
		//Go through the date buckets and parse them into Calendar objects.
		dateDMYBucket.forEach(dateParser);
		date224Bucket.forEach(dateParser);
		date422Bucket.forEach(dateParser);
		dateLongBucket.forEach(dateParser);
		
		//If there were no parseable dates, look at the year bucket.
		//Use the years given to parse dates instead. The start date will be
		//the beginning of one of the years, and the end of the second year.
		//If there was one parseable date, look at the given years to find a
		//second date.
		if(parsedDates.size() == 0){
			if(dateYearBucket.size() == 1){
				String yearStr = dateYearBucket.iterator().next();
				parsedDates.add(this.parseDateYear(yearStr, false));
				parsedDates.add(this.parseDateYear(yearStr, true));
			} else{
				dateYearBucket.forEach(dateParser);
			}
		} else if(parsedDates.size() == 1){
			dateYearBucket.forEach(dateParser);
		}
		
		return parsedDates;
	}
	
	private TreeSet<Calendar> parseDateRange(String dateRangeTxt) {
		
		TreeSet<Calendar> orderedDates = new TreeSet<Calendar>();
		Matcher dateMatcher = DATE_PATTERN.matcher(dateRangeTxt);
		Matcher dateRangeMatcher = DATE_RANGE_MONTH_DAY_PATTERN.matcher(dateRangeTxt);
		
		Calendar date1 = null;
		Calendar date2 = null;
		
		if(dateRangeMatcher.find()){
			String dateRangeStr = dateRangeMatcher.group();
			orderedDates = this.parseShortenedDateRange(dateRangeStr);
		} else{
			if(dateMatcher.find()){
				date1 = this.parseDateString(dateMatcher.group());
			}
			if(dateMatcher.find()){
				date2 = this.parseDateString(dateMatcher.group());
			}
			
			if(date1 != null){
				orderedDates.add(date1);
			}
			if(date2 != null){
				orderedDates.add(date2);
			}
		}
		
		return orderedDates;
	}
	
	private TreeSet<Calendar> parseShortenedDateRange(String shortenedDateRangeStr) {
		Matcher monthMatcher = MONTH_PATTERN.matcher(shortenedDateRangeStr);
		Matcher dayMatcher = DAY_PATTERN.matcher(shortenedDateRangeStr);
		Matcher yearMatcher = YEAR_PATTERN.matcher(shortenedDateRangeStr);
		
		String yearStr = this.getBucketYear();
		String monthStr = this.getBucketMonth();
		
		if(yearMatcher.find()){
			yearStr = yearMatcher.group();
		}
		if(monthMatcher.find()){
			monthStr = monthMatcher.group();
		}
		
		int year = this.parseYear(yearStr);
		int month = this.parseMonth(monthStr);
		
		Calendar calendar1 = this.getCalendarInstance();
		Calendar calendar2 = this.getCalendarInstance();
		
		calendar1.set(Calendar.YEAR, year);
		calendar2.set(Calendar.YEAR, year);
		
		calendar1.set(Calendar.MONTH, month);
		calendar2.set(Calendar.MONTH, month);
		
		if(dayMatcher.find()){
			calendar1.set(Calendar.DAY_OF_MONTH, this.parseDay(month, year, dayMatcher.group()));
		}
		if(dayMatcher.find()){
			calendar2.set(Calendar.DAY_OF_MONTH, this.parseDay(month, year, dayMatcher.group()));
		} else{
			calendar2.set(Calendar.DAY_OF_MONTH, calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		
		TreeSet<Calendar> orderedDates = new TreeSet<Calendar>();
		orderedDates.add(calendar1);
		orderedDates.add(calendar2);
		
		return orderedDates;
	}
	
	private Calendar parseDateString(String dateString){
		Calendar parsedDate = null;
		
		if(Pattern.matches(RegexConstants.DATE_LONG, dateString)){
			this.hasSoloMonth = Pattern.matches(RegexConstants.MONTH, dateString) || Pattern.matches(RegexConstants.DATE_MONTH_YEAR, dateString);
			parsedDate = this.parseDateLong(dateString);
		} else if(Pattern.matches(RegexConstants.DATE_DMY, dateString)){
			parsedDate = this.parseDateDMY(dateString);
		} else if(Pattern.matches(RegexConstants.DATE_224, dateString)){
			parsedDate = this.parseDate224(dateString);
		} else if(Pattern.matches(RegexConstants.DATE_422, dateString)){
			parsedDate = this.parseDate422(dateString);
		} else if(Pattern.matches(RegexConstants.YEAR_4, dateString)){
			this.hasSoloYear = true;
			parsedDate = this.parseDateYear(dateString, false);
		} else{
			logger.warn("The given date string does not match any of the pre-defined patterns.");
		}
		
		return parsedDate;
	}
	
	private Calendar parseDateDMY(String dateString) {
		String yearStr = null;
		String monthStr = null;
		String dayStr = null;
		
		String[] splitDate = dateString.split(RegexConstants.DATE_SEPARATOR);
		
		if(splitDate.length < 2){
			return null;
		} else{
			monthStr = splitDate[1];
			dayStr = splitDate[0];
		}
		
		if(splitDate.length > 2){
			yearStr = splitDate[2];
		} else{
			yearStr = this.getBucketYear();
		}
		
		if(yearStr.length() != 4){
			yearStr = "20" + yearStr;
		}
		if(yearStr.length() != 4){
			yearStr = this.getBucketYear();
		}
		
		Calendar calendar = this.parseCalendar(yearStr, monthStr, dayStr);
		
		if(Calendar.UNDECIMBER == calendar.get(Calendar.MONTH)){
			logger.warn("The thirteenth month has been chosen. Check the flow. Returning null calendar.");
			return null;
		}
		
		return calendar;
	}
	
	private Calendar parseDateLong(String dateString) {
		String yearStr = null;
		String monthStr = null;
		String dayStr = null;
		
		Matcher yearMatcher = YEAR_PATTERN.matcher(dateString);
		Matcher monthMatcher = MONTH_PATTERN.matcher(dateString);
		Matcher dayMatcher = DAY_PATTERN.matcher(dateString);
		
		if(yearMatcher.find()){
			yearStr = yearMatcher.group();
		}
		if(monthMatcher.find()){
			monthStr = monthMatcher.group();
		}
		if(dayMatcher.find()){
			dayStr = dayMatcher.group();
		}
		
		Calendar calendar = this.parseCalendar(yearStr, monthStr, dayStr);
		
		if(Calendar.UNDECIMBER == calendar.get(Calendar.MONTH)){
			logger.warn("The thirteenth month has been chosen. Check the flow. Returning null calendar.");
			return null;
		}
		
		return calendar;
	}
	
	private Calendar parseDate224(String dateString) {
		String yearStr = null;
		String monthStr = null;
		String dayStr = null;
		
		String[] splitDate = dateString.split(RegexConstants.DATE_SEPARATOR);
		
		if(splitDate.length < 2){
			return null;
		} else{
			monthStr = splitDate[0];
			dayStr = splitDate[1];
		}
		
		if(splitDate.length > 2){
			yearStr = splitDate[2];
		} else{
			yearStr = this.getBucketYear();
		}
			
		
		if(yearStr.length() != 4){
			yearStr = "20" + yearStr;
		}
		if(yearStr.length() != 4){
			yearStr = this.getBucketYear();
		}
		
		Calendar calendar = this.parseCalendar(yearStr, monthStr, dayStr);
		
		if(Calendar.UNDECIMBER == calendar.get(Calendar.MONTH)){
			logger.warn("The thirteenth month has been chosen. Check the flow. Returning null calendar.");
			return null;
		}
		
		return calendar;
	}
	
	private Calendar parseDate422(String dateString) {
		String yearStr = null;
		String monthStr = null;
		String dayStr = null;
		
		String[] splitDate = dateString.split(RegexConstants.DATE_SEPARATOR);
		
		if(splitDate.length < 3){
			return null;
		} else{
			monthStr = splitDate[1];
			dayStr = splitDate[2];
			yearStr = splitDate[0];
		}
		
		Calendar calendar = this.parseCalendar(yearStr, monthStr, dayStr);
		
		if(Calendar.UNDECIMBER == calendar.get(Calendar.MONTH)){
			return null;
		}
		
		return calendar;
	}
	
	private Calendar parseDateYear(String yearStr, boolean isEndOfYear) {
		int year = 0;
		
		Calendar calendar = this.getCalendarInstance();
		
		Matcher integerMatcher = YEAR_PATTERN.matcher(yearStr);
		if(integerMatcher.find()){
			yearStr = integerMatcher.group();
		}
		
		if(yearStr.length() != 4){
			yearStr = "20" + yearStr;
		}
		
		if(yearStr.length() != 4){
			return null;
		}
		
		year = this.parseYear(yearStr);
		calendar.set(Calendar.YEAR, year);
		
		int month = Calendar.JANUARY;
		if(isEndOfYear){
			month = calendar.getActualMaximum(Calendar.MONTH);
		}
		calendar.set(Calendar.MONTH, month);
		
		int day = 1;
		if(isEndOfYear){
			day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		calendar.set(Calendar.DAY_OF_MONTH, day);
		
		return calendar;
	}
	
	private String getBucketYear() {
		
		//Get the first year out of the year bucket.
		Iterator<String> i = this.yearBucket.iterator();
		if(i.hasNext()){
			return i.next();
		}
		
		//Or get the current year.
		return String.valueOf(this.getCalendarInstance().get(Calendar.YEAR));
	}
	
	private String getBucketMonth() {
		Iterator<String> i = this.monthBucket.iterator();
		if(i.hasNext()){
			return i.next();
		}
		
		return String.valueOf(Calendar.JANUARY);
	}
	
	private int parseYear(String yearStr){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		if(StringUtils.isNotBlank(yearStr)){
			try{
				year = Integer.parseInt(yearStr);
			} catch(NumberFormatException e){
				logger.warn("Failed to parse year string. Resolving with bucket value.");
				year = Integer.parseInt(this.getBucketYear());
			}
		}
		
		return year;
	}
	
	private int parseMonth(String monthStr){
		int result = Calendar.UNDECIMBER;
		
		if(StringUtils.isNotBlank(monthStr)){
			if(Pattern.matches(RegexConstants.MONTH_NUMBER, monthStr)){
				try{
					result = Integer.parseInt(monthStr) - 1;
				} catch(NumberFormatException e){
					logger.warn("Parsing error. Defaulting to 13th month.");
					result = Calendar.UNDECIMBER;
				}
			} else{
				if(Pattern.matches(RegexConstants.JANUARY, monthStr)){
					result = Calendar.JANUARY;
				} else if(Pattern.matches(RegexConstants.FEBRUARY, monthStr)){
					result = Calendar.FEBRUARY;
				} else if(Pattern.matches(RegexConstants.MARCH, monthStr)){
					result = Calendar.MARCH;
				} else if(Pattern.matches(RegexConstants.APRIL, monthStr)){
					result = Calendar.APRIL;
				} else if(Pattern.matches(RegexConstants.MAY, monthStr)){
					result = Calendar.MAY;
				} else if(Pattern.matches(RegexConstants.JUNE, monthStr)){
					result = Calendar.JUNE;
				} else if(Pattern.matches(RegexConstants.JULY, monthStr)){
					result = Calendar.JULY;
				} else if(Pattern.matches(RegexConstants.AUGUST, monthStr)){
					result = Calendar.AUGUST;
				} else if(Pattern.matches(RegexConstants.SEPTEMBER, monthStr)){
					result = Calendar.SEPTEMBER;
				} else if(Pattern.matches(RegexConstants.OCTOBER, monthStr)){
					result = Calendar.OCTOBER;
				} else if(Pattern.matches(RegexConstants.NOVEMBER, monthStr)){
					result = Calendar.NOVEMBER;
				} else if(Pattern.matches(RegexConstants.DECEMBER, monthStr)){
					result = Calendar.DECEMBER;
				} else{
					result = Calendar.UNDECIMBER;
				}
			}
		}
		
		return result;
	}
	
	private int parseDay(int givenMonth, int givenYear, String dayStrToParse){
		Calendar partialCalendar = this.getCalendarInstance();
		partialCalendar.set(Calendar.YEAR, givenYear);
		partialCalendar.set(Calendar.MONTH, givenMonth);
		
		int day = 1;
		
		if(StringUtils.isNotBlank(dayStrToParse)){
			Pattern integerPattern = Pattern.compile(RegexConstants.INTEGER);
			Matcher integerMatcher = integerPattern.matcher(dayStrToParse);
			if(integerMatcher.find()){
				dayStrToParse = integerMatcher.group();
				try{
					day = Integer.parseInt(dayStrToParse);
				} catch(NumberFormatException e){
					logger.warn("Error parsing the day. Defaulting to first day.");
					day = 1;
				}
				
				if(day > partialCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
					day = partialCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				}
			}
		}
		
		return day;
	}
	
	private Calendar parseCalendar(String yearStr, String monthStr, String dayStr){
		Calendar calendar = this.getCalendarInstance();
		
		int year = this.parseYear(yearStr);
		int month = this.parseMonth(monthStr);
		int day = this.parseDay(month, year, dayStr);
			
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, day);
		
		return calendar;
	}
	
	private Calendar getCalendarInstance(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
	
		return c;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate.getTime();
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate.getTime();
	}

	public Set<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}
}
