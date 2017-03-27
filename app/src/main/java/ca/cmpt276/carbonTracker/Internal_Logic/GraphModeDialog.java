package ca.cmpt276.carbonTracker.Internal_Logic;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;

import static ca.cmpt276.carbonTracker.UI.GraphMenuActivity.dayMode;
import static ca.cmpt276.carbonTracker.UI.GraphMenuActivity.monthMode;
import static ca.cmpt276.carbonTracker.UI.GraphMenuActivity.yearMode;

/**
 * Created by song on 2017-03-26.
 */

public class GraphModeDialog extends AppCompatDialogFragment {
    private static final String[] monthYearOptions = {"Bar Graph", "Pie Graph - Transportation Mode", "Pie Graph - Route Mode"};
    private static final String[] singleDayOptions = {"Journey Mode", "Transportation Mode", "Route Mode"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ;
            }
        };

        if (dayMode){
            dayMode = false;   // reset it to original value.
            return new AlertDialog.Builder(getActivity()).setTitle("Graph Mode Selector").setSingleChoiceItems(singleDayOptions, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        // launch single day Pie graph - journey mode
                        Toast.makeText(getActivity(), "To launch single day pie graph journey mode activity", Toast.LENGTH_SHORT).show();
                    }
                    if (which == 1) {
                        // Launch single day Pie graph - transportation mode
                        Toast.makeText(getActivity(), "To launch single day pie graph transportation mode activity", Toast.LENGTH_SHORT).show();
                    }
                    if (which == 2) {
                        // Launch single day Pie graph- route mode
                        Toast.makeText(getActivity(), "To launch single day pie graph route mode activity", Toast.LENGTH_SHORT).show();
                    }

                }
            }).create();

        }
        else if (monthMode){
            monthMode=false;   // reset it to original value.
            return new AlertDialog.Builder(getActivity()).setTitle("Select Graph Mode").setSingleChoiceItems(monthYearOptions, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        // Launch 4 week Bar graph activity
                        Toast.makeText(getActivity(), "To launch 4 week Bar graph activity", Toast.LENGTH_SHORT).show();
                    }
                    if (which == 1) {
                        //Launch 4 week Pie graph- transportation mode
                        Toast.makeText(getActivity(), "To launch 4 week pie graph transportation mode Activity", Toast.LENGTH_SHORT).show();
                    }
                    if (which == 2) {
                        //Launch 4 week Pie graph- transportation mode
                        Toast.makeText(getActivity(), "To launch 4 week pie graph route mode Activity", Toast.LENGTH_SHORT).show();
                    }
                }
            }).create();
        }
        else {
            yearMode=false;   // reset it to original value.
            return new AlertDialog.Builder(getActivity()).setTitle("Select Graph Mode").setSingleChoiceItems(monthYearOptions, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        // Launch year Bar graph activity
                        Toast.makeText(getActivity(), "To launch year Bar graph activity", Toast.LENGTH_SHORT).show();
                    }
                    if (which == 1) {
                        //Launch year Pie graph- transportation mode
                        Toast.makeText(getActivity(), "To launch year pie graph transportation mode Activity", Toast.LENGTH_SHORT).show();
                    }
                    if (which == 2) {
                        //Launch year Pie graph- transportation mode
                        Toast.makeText(getActivity(), "To launch year pie graph route mode Activity", Toast.LENGTH_SHORT).show();
                    }
                }
            }).create();
        }
    }

}
