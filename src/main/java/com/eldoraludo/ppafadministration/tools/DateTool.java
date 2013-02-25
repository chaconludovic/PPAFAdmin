package com.eldoraludo.ppafadministration.tools;

import java.text.SimpleDateFormat;

import org.joda.time.DateMidnight;

public class DateTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(DateMidnight.now().toString("YYYYMMdd"));
	}
}
