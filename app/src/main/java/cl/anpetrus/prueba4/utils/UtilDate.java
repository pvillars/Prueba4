package cl.anpetrus.prueba4.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Petrus on 13-09-2017.
 */

public class UtilDate {


    private Date date;
    private String format;
    private static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public Locale locale;

    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static String parseDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        return format.format(date);
    }

    public UtilDate() {
        date = new Date();
    }

    public UtilDate(String format) {
        date = new Date();
        this.format = format;
    }

    public UtilDate(String format, Locale locale) {
        date = new Date();
        this.format = format;
        this.locale = locale;
    }

    public UtilDate(Date date,String format, Locale locale) {
        this.date = date;
        this.format = format;
        this.locale = locale;
    }

    public UtilDate(String dateS,String format, Locale locale) {
        this.date = new UtilDate().toDate(dateS);
        this.format = format;
        this.locale = locale;
    }
    public UtilDate(Date date) {
        this.date = date;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        if (format != null)
            if (locale != null)
                return new SimpleDateFormat(format, locale).format(date);
            else
                return new SimpleDateFormat(format).format(date);
        else if (locale != null)
            return new SimpleDateFormat(FORMAT_DEFAULT, locale).format(date);
        else
            return new SimpleDateFormat(FORMAT_DEFAULT).format(date);
    }

    public  Date toDate(String date) {
        SimpleDateFormat formatter;
        if (format != null)
            formatter = new SimpleDateFormat(format);
        else
            formatter = new SimpleDateFormat(FORMAT_DEFAULT);

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("ParseException", e.toString());
            return null;
        }
    }

    public String toDateStr(String date, String format, Locale locale) {
        return new UtilDate(toDate(date),format,locale).toString();
    }

    public Date toDate(String date, String format, Locale locale) {

        SimpleDateFormat formatter;
        if (format != null)
            formatter = new SimpleDateFormat(format, locale);
        else
            formatter = new SimpleDateFormat(FORMAT_DEFAULT, locale);

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("ParseException", e.toString());
            return null;
        }
    }

}