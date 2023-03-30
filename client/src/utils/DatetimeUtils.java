package utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Support date-time features. Take server (date-time in a specified UTC
 * timezone) time as the standard.
 * 
 */
public class DatetimeUtils {

    /**
     * Get current date-time at Ho Chi Minh city, Viet Nam
     * @return String: formatted in #ISO_DATE_TIME
     */
    public static String getCurrentDateTime() {
        // Create formatter for ISO date-time
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        // Get the current date-time in the ICT timezone
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime dateTime = ZonedDateTime.now(zoneId);

        // Format the date-time using the formatter
        String formattedDateTime = formatter.format(dateTime);

        return formattedDateTime;
    }
}
