package ca.cmpt276.carbonTracker.Internal_Logic;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by song on 2017-03-26.
 */

public class CalendarDialog extends AppCompatDialogFragment {
    private int year_x, month_x, day_x;

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
            month_x = month;
            day_x = dayOfMonth;

            // pass the above info into the graph activity.


            // call the next dialog
            FragmentManager manager = getActivity().getSupportFragmentManager();
            GraphModeDialog dialog = new GraphModeDialog();
            dialog.show(manager,"graphModeDialog");

        }
    };




}
