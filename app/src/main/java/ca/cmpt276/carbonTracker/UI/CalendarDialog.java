package ca.cmpt276.carbonTracker.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.cmpt276.carbonTracker.AlternateUI.MainMenuActivity_Alternate;
import ca.cmpt276.carbonTracker.Internal_Logic.CalendarObserver;
import ca.cmpt276.carbonTracker.Internal_Logic.DateHandler;

/**
 * Created by song on 2017-03-26.
 */

public class CalendarDialog extends AppCompatDialogFragment {
    private static int year_x, month_x, day_x;
    public static Date selectedDate;
    public static ArrayList<CalendarObserver> observers = new ArrayList<>();

    public static void addObserver(CalendarObserver observer) {
        observers.add(observer);
    }

    private void updateObservers() {
        for (CalendarObserver observer : observers) {
            observer.updateGraphs();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar Cal = Calendar.getInstance();
        int year_x = Cal.get(Calendar.YEAR);
        int month_x = Cal.get(Calendar.MONTH);
        int day_x = Cal.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),listener, year_x, month_x, day_x);

    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            /* Note: creatDate() uses month index from 1-12, while the calendar indexes from 0-11. That's why
               month_x needs to be incremented by 1.*/
            day_x = dayOfMonth;
            month_x = month+1;

            selectedDate = DateHandler.createDate(year_x,month_x,day_x);
            updateObservers();

            // call the next dialog
            /*FragmentManager manager = getActivity().getSupportFragmentManager();
            GraphModeDialog dialog = new GraphModeDialog();
            dialog.show(manager, getResources().getString(R.string.graphModeDialog));*/

        }
    };

}
