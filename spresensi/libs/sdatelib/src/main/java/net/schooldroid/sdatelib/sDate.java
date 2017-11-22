package net.schooldroid.sdatelib;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jekky on 30/10/2017.
 */

public class sDate {

    public static String getNow (String format){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        String date = dateFormat.format(calendar.getTime());

        Log.d("NOW",date);
        return date;
    }

    public static long difference (String format, String value1, String value2){

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        try {
            Date date1 = dateFormat.parse(value1);
            Date date2 = dateFormat.parse(value2);

            long millis = Math.abs(date1.getTime() - date2.getTime());
            Log.d("Different",""+(millis/1000));
            return millis/1000;

        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
