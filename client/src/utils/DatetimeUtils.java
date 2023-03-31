package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Support date-time features. Take server (date-time in a specified UTC
 * timezone) time as the standard.
 * 
 */
public class DatetimeUtils
{

    /**
     * Get current date-time at Ho Chi Minh city, Viet Nam
     * 
     * @return long: timestamp in miliseconds
     */
    public static Long getCurrentDateTime()
    {
        Date now = new Date();

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        return now.getTime();
    }

    public static String getCurrentDateTime(Long datetime)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

        Date date = new Date(datetime);

        return formatter.format(date);
    }
}
