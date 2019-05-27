package com.directory.abe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator {
    private static final String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
    private Matcher matcher;
    private Pattern pattern = Pattern.compile(DATE_PATTERN);

    public boolean validateD(String date) {
        this.matcher = this.pattern.matcher(date);
        if (!this.matcher.matches()) {
            return false;
        }
        this.matcher.reset();
        if (!this.matcher.find()) {
            return false;
        }
        String day = this.matcher.group(1);
        String month = this.matcher.group(2);
        int year = Integer.parseInt(this.matcher.group(3));
        if (day.equals("31") && (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11") || month.equals("04") || month.equals("06") || month.equals("09"))) {
            return false;
        }
        if (!month.equals("2") && !month.equals("02")) {
            return true;
        }
        if (year % 4 == 0) {
            if (day.equals("30") || day.equals("31")) {
                return false;
            }
            return true;
        } else if (day.equals("29") || day.equals("30") || day.equals("31")) {
            return false;
        } else {
            return true;
        }
    }
}
