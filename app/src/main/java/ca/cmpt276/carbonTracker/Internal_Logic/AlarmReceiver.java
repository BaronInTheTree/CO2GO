package ca.cmpt276.carbonTracker.Internal_Logic;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * AlarmReceiver class listens to the system until it is 9pm
 * and then creates notification and reschedules another one.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarm(context);
    }

    public static void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Intent intent = new Intent(context, Service.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        // Create new calendar date for next 9pm
        Calendar calendar = Calendar.getInstance();
        Calendar selectedTime = new GregorianCalendar(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                21, 0, 0);                             // Set to 21:00 (9pm)

/*
        // trying to set alarm
        Calendar now = Calendar.getInstance();
//        long timDiff = Math.abs(now.getTimeInMillis() - selectedTime.getTimeInMillis());
        long timDiff = 5000;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, timDiff, pendingIntent);
*/

        // Set alarm for next day at 21:00
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                selectedTime.getTimeInMillis(),alarmManager.INTERVAL_DAY, pendingIntent);
    }
}
