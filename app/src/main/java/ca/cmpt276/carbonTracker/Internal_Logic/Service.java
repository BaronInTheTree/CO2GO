package ca.cmpt276.carbonTracker.Internal_Logic;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import ca.cmpt276.carbonTracker.UI.*;

/**
 * Service class selects which notification should be displayed based on user's actions.
 */

public class Service extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public Service(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.i("Testing", "Test if logs at 9pm.");

// ** extract all strings into strings.xml file** //

        // select which notification to call

        // Check if no journeys added today
        noJourneyNotification();

        // Check if no utility bills have been entered in the last 1.5 months (42) days
        noBillNotification();

        // Once have city average CO2 emission is implemented in app
        // if user's emission is larger display notification warning them they are above the average.

        // if no favourite journey is entered ask if want to set a journey as favourite

        // else display number of journeys entered today and ask to add more
        defaultNotification();
    }

    private void noJourneyNotification() {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainMenuActivity.class);

        // replace with add journey activity if that exists
        Intent intent = new Intent(this, MainMenuActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        String message = "No journeys entered today, would you like to add one?";

        Notification journeyNotification = new Notification(this, message, pendingIntent);
    }

    private void noBillNotification() {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainMenuActivity.class);

        Intent intent = new Intent(this, AddUtilityActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        String message = "No utility bill added in 1.5 months, please add one.";

        Notification utilityNotification = new Notification(this,message, pendingIntent);
    }

    private void defaultNotification() {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainMenuActivity.class);

        Intent intent = new Intent(this, MainMenuActivity.class);
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

        Notification notification = new Notification(this, string1, pendingIntent);
    }
}
