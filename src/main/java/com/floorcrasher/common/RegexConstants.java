package com.floorcrasher.common;

public class RegexConstants {
	
	public static final String WORD = "\\b\\w+\\b";
	public static final String INTEGER = "\\d+";
	public static final String DATE_SEPARATOR = "(/|-)+";
	
	public static final String OR = "|";
	public static final String WORD_CHAR = "\\w";
	
	public static final String JANUARY = "\\b(jan|january)\\b";
	public static final String FEBRUARY = "\\b(feb|february)\\b";
	public static final String MARCH = "\\b(mar|march)\\b";
	public static final String APRIL = "\\b(apr|april)\\b";
	public static final String MAY = "\\b(may)\\b";
	public static final String JUNE = "\\b(jun|june)\\b";
	public static final String JULY = "\\b(jul|july)\\b";
	public static final String AUGUST = "\\b(aug|august)\\b";
	public static final String SEPTEMBER = "\\b(sep|sept|september)\\b";
	public static final String OCTOBER = "\\b(oct|october)\\b";
	public static final String NOVEMBER = "\\b(nov|november)\\b";
	public static final String DECEMBER = "\\b(dec|december)";
	public static final String MONTH_NUMBER = "\\b([1-9]|0[1-9]|1[0-2])\\b";
	
	//2015-2029
	public static final String YEAR_4 = "(\\b\\d{4}\\b)";
	public static final String YEAR_2 = "(\\b\\d{2}\\b)";
	public static final String MONTH = "(\\b"
				+ JANUARY + OR
				+ FEBRUARY + OR
				+ MARCH + OR
				+ APRIL + OR
				+ MAY + OR
				+ JUNE + OR
				+ JULY + OR
				+ AUGUST + OR
				+ SEPTEMBER + OR
				+ OCTOBER + OR
				+ NOVEMBER + OR
				+ DECEMBER + "\\b)";
	public static final String DAY = "(\\b([1-2]\\d|0?[1-9]|3[0-1])(rd|nd|th)?\\b)";
	
	//Date formats
	public static final String DATE_MONTH_YEAR = "(\\b" + MONTH + "(\\s)+" + YEAR_4 + "\\b)";
	public static final String DATE_LONG = "(\\b" + MONTH + "(\\s+" + DAY + ")?"  + "((,|\\s)+" + YEAR_4 + ")?" + "\\b)";
	public static final String DATE_DMY = "(\\b" + DAY + "\\s*(/|-)+\\s*" + MONTH + "(\\s*(/|-)+\\s*(" + YEAR_4 + OR + YEAR_2 + "))?" + "\\b)";
	public static final String DATE_224 = "(\\b" + MONTH_NUMBER + "\\s*(/|-)+\\s*" + DAY + "(\\s*(/|-)+\\s*(" + YEAR_4 + OR + YEAR_2 + "))?" + "\\b)";
	public static final String DATE_422 = "(\\b" + YEAR_4 + "\\s*(/|-)+\\s*" + MONTH_NUMBER + "\\s*(/|-)+\\s*" + DAY + "\\b)";
	public static final String DATE_YEAR = "(\\b" + YEAR_4  + "\\b)";
	
	public static final String DATE = "(\\b(" + DATE_DMY + OR + DATE_224 + OR + DATE_422 + OR + DATE_LONG  + OR + DATE_YEAR + ")\\b)";
	
	//Date range formats
	public static final String DATE_RANGE_MONTH_DAY = "(\\b" + MONTH + "\\s+" + DAY + "\\s*-\\s*" + DAY  + "((,|\\s)+" + YEAR_4 + ")?" + "\\b)";
	public static final String DATE_RANGE = "(\\b" + DATE_RANGE_MONTH_DAY + "\\b)";
	
	
}
