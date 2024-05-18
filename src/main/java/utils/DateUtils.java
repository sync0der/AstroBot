package utils;

import logging.TelegramLog;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code DateUtils} class provides utility methods for working with dates and date formatting.
 */
public class DateUtils {
    /**
     * Checks if the input date matches the specified date pattern.
     *
     * @param datePattern the pattern to match against.
     * @param inputDate   the input date string
     * @return {@code true} if the input date matches the pattern, otherwise {@code false}.
     */
    public static boolean isValidDateFormat(String datePattern, String inputDate) {
        Pattern pattern = Pattern.compile("^[12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");
        Matcher matcher = pattern.matcher(inputDate);
        if (!(matcher.find()))
            return false;
        try {
            LocalDate.parse(inputDate, DateTimeFormatter.ofPattern(datePattern));
            return true;
        } catch (Exception e) {
            TelegramLog.logging(e.fillInStackTrace() + "\n" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    /**
     * Formats the input date string into the specified output format.
     *
     * @param inputDate    the input date string.
     * @param inputFormat  the format of the input date string
     * @param outputFormat the desired output format
     * @param locale       the locale for formatting (optional, can be null).
     * @return the formatted date string.
     */
    public static String dateFormatter(String inputDate, String inputFormat, String outputFormat, Locale locale) {
        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern(inputFormat));
        if (locale == null)
            return date.format(DateTimeFormatter.ofPattern(outputFormat));
        return date.format(DateTimeFormatter.ofPattern(outputFormat, locale));
    }

    /**
     * Formats the input date and time string into the specified output format.
     *
     * @param inputDateTime the input date and time string.
     * @param inputFormat   the format of the input date and time string.
     * @param outputFormat  the desired output format.
     * @param locale        the locale for formatting (optional, can be null).
     * @return the formatted date and time string.
     */
    public static String dateTimeFormatter(String inputDateTime, String inputFormat, String outputFormat, Locale locale) {
        LocalDateTime date;
        if (inputFormat.equals(DateTimeFormatter.ISO_DATE_TIME.toString()))
            date = LocalDateTime.parse(inputDateTime, DateTimeFormatter.ISO_DATE_TIME);
        else
            date = LocalDateTime.parse(inputDateTime, DateTimeFormatter.ofPattern(inputFormat));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(outputFormat, locale);
        return date.format(formatter);
    }

    /**
     * Compares two dates to check if the first date is after the second date.
     *
     * @param firstDate  the first date string.
     * @param secondDate the second date string.
     * @return {@code true} if the first date is after the second date, otherwise {@code false}.
     */
    public static boolean compareDates(String firstDate, String secondDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inDate = LocalDate.parse(firstDate, formatter);
        LocalDate constDate = LocalDate.parse(secondDate, formatter);
        return inDate.isAfter(constDate);
    }

    /**
     * Checks if the input date is in the future compared to the current date,
     *
     * @param inputDate the date string to be checked.
     * @return {@code true} if the input date is in the future, otherwise {@code false}.
     */
    public static boolean isFutureDate(String inputDate) {
        return compareDates(inputDate, getCurrentDate());
    }

    /**
     * Gets the current date in the format "yyyy-MM-dd".
     *
     * @return the current date string.
     */
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Calendar.getInstance().getTime();
        return formatter.format(date);
    }
}
