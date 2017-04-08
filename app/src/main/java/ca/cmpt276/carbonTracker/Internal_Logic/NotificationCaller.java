package ca.cmpt276.carbonTracker.Internal_Logic;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import com.example.sasha.carbontracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.cmpt276.carbonTracker.UI.MainMenuActivity;
import ca.cmpt276.carbonTracker.UI.Notification;

/**
 * NotificationCaller class waits until system clock reaches desired time then creates notification.
 * Needs to be run in own thread to not hold up the program.
 */

public class NotificationCaller {

    private static final int DESIRED_HOUR = 21;
    private static final int DESIRED_MINUTE = 0;
    private static final int BUFFER_ONE_MINUTE = 60 * 1000;
    public static final int SIX_WEEKS = 42;

    private static NotificationCaller instance;

    private NotificationCaller() {
    }

    public static NotificationCaller getInstance() {
        if (instance == null) {
            instance = new NotificationCaller();
        }
        return instance;
    }

    public void run(Context context) {
        Calendar calendar = Calendar.getInstance();

        int currentTimeHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentTimeMinute = calendar.get(Calendar.MINUTE);
        int currentTmeSecond = calendar.get(Calendar.SECOND);

        // Checks if user opens app between 21:00:00 and 21:01:00 (before first minute of 9pm)
        if (currentTimeHour == DESIRED_HOUR && currentTimeMinute == DESIRED_MINUTE) {
            createNotification(context);
        }

        while (true) {
            currentTimeHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentTimeMinute = calendar.get(Calendar.MINUTE);
            currentTmeSecond = calendar.get(Calendar.SECOND);

            if (currentTmeSecond == 0) {

                // Check for 21:00:00
                if (currentTimeHour == DESIRED_HOUR &&
                        currentTimeMinute == DESIRED_MINUTE) {

                    createNotification(context);

                    System.out.println("HOORAY!! IT WORKS!!!");
                    System.out.println(currentTimeHour + ":" + currentTimeMinute + ":" + currentTmeSecond);
                }

                // Wait time for a minute
                try {
                    Thread.sleep(BUFFER_ONE_MINUTE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createNotification(Context context) {
        Date today = DateHandler.createDate(
                Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        DayData dayData = DayData.getDayDataAtDate(today);

        // No journeys added today
        if (dayData.getJourneyList().size() == 0) {     // this if statement is never triggering
            noJourneyAdded(context);
        }

/*
        // No Bills added in 1.5 months (42 days)
        if (noBillAddedIn6Weeks())
            noBillAdded(context);
*/

        // No favourite Journey selected
//        noFavouriteJourney(context);

        // If none of others are chosen display default notification
        defaultNotification(context);
    }

    private boolean noBillAddedIn6Weeks() {

        Calendar calendar = Calendar.getInstance();
        Date current = DateHandler.createDate(calendar.YEAR, calendar.MONTH, calendar.DAY_OF_MONTH);
        DayData dayData;

        // Loop through 6 weeks checking if for each day the list is not empty (added utility that day)
        for (int i = 0; i < SIX_WEEKS; i++) {
            // Get current day
            dayData = DayData.getDayDataAtDate(current);

            if (!(dayData.getUtilityList().size() == 0)) {
                return true;
            }

            // Get to previous day
            calendar.add(Calendar.DATE, -1);
            // Update current date to previous day
            current = calendar.getTime();
        }
        return false;
    }

    private void noJourneyAdded(Context context) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainMenuActivity.class);

        // replace with add journey activity if that exists
        Intent intent = new Intent(context, MainMenuActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        String message = context.getString(R.string.no_journeys_added_today_notification);

        Notification journeyNotification = new Notification(context, message, pendingIntent);
    }

    private void noBillAdded(Context context) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainMenuActivity.class);

        // change to adding bill activity
        Intent intent = new Intent(context, MainMenuActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        String message = context.getString(R.string.no_bill_added_notification);

        Notification utilityNotification = new Notification(context, message, pendingIntent);

    }

    private void noFavouriteJourney(Context context) {

    }

    private void defaultNotification(Context context) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainMenuActivity.class);

        Intent intent = new Intent(context, MainMenuActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        String message = context.getString(R.string.default_notification_message);

        Notification notification = new Notification(context, message, pendingIntent);
    }
}
