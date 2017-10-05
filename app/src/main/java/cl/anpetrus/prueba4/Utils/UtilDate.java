package cl.anpetrus.prueba4.Utils;

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
    private static final String formatDefault = "yyyy-MM-dd HH:mm:ss";
    public Locale locale;

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
            return new SimpleDateFormat(formatDefault, locale).format(date);
        else
            return new SimpleDateFormat(formatDefault).format(date);
    }

    public  Date toDate(String date) {
        SimpleDateFormat formatter;
        if (format != null)
            formatter = new SimpleDateFormat(format);
        else
            formatter = new SimpleDateFormat(formatDefault);

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
            formatter = new SimpleDateFormat(formatDefault, locale);

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("ParseException", e.toString());
            return null;
        }
    }

}