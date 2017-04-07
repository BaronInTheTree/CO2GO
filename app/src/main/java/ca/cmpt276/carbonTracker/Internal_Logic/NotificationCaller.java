package ca.cmpt276.carbonTracker.Internal_Logic;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import ca.cmpt276.carbonTracker.UI.AddUtilityActivity;
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
                    Notification notification = new Notification(context, "Testing", null);

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

        // No Journeys added today
        noJourneyAdded(context);

        // No Bills added in 1.5 months (42 days)
        noBillAdded(context);

        // No favourite Journey selected
        noFavouriteJourney(context);

        // If none of others are chosen display default notification
        defaultNotification(context);
    }

    private void noJourneyAdded(Context context) {
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainMenuActivity.class);

            // replace with add journey activity if that exists
            Intent intent = new Intent(context, MainMenuActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            String message = "No journeys entered today, would you like to add one?";

            Notification journeyNotification = new Notification(context, message, pendingIntent);
    }

    private void noBillAdded(Context context) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainMenuActivity.class);

        Intent intent = new Intent(context, AddUtilityActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        String message = "No utility bill added in 1.5 months, please add one.";

        Notification utilityNotification = new Notification(context,message, pendingIntent);

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

        String string1 = "Would you like to add another journey?";

/*  This is a possible string to display

        int numJourneysAddedToday = get number of journeys entered
        String string2 = "You have entered " +
                numJourneysAddedToday +
                " today, would you like to add another one?";
*/

        Notification notification = new Notification(context, string1, pendingIntent);

    }
}
