package ca.cmpt276.carbonTracker.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.UI.MonthlyEmissionGraphActivity;
import ca.cmpt276.carbonTracker.UI.PieGraphTransportActivity;
import ca.cmpt276.carbonTracker.UI.YearlyEmissionLineGraphActivity;

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
            return new AlertDialog.Builder(getActivity()).setTitle(getResources().getString(R.string.graphModeDialogTitle)).
                    setSingleChoiceItems(singleDayOptions, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        // launch single day Pie graph - journey mode
                        Toast.makeText(getActivity(), "To launch single day pie graph journey mode activity", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                    if (which == 1) {
                        Intent intent = new Intent(getContext(), PieGraphTransportActivity.class);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    }
                    if (which == 2) {
                        Intent intent = new Intent(getContext(), PieGraphRouteActivity.class);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    }
                }
            }).create();

        }
        else if (monthMode){
            return new AlertDialog.Builder(getActivity()).setTitle(getResources().getString(R.string.graphModeDialogTitle)).
                    setSingleChoiceItems(monthYearOptions, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        monthMode = false;  // reset it to original value.
                        Intent intent = new Intent(getContext(), MonthlyEmissionGraphActivity.class);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    }
                    if (which == 1) {
                        Intent intent = new Intent(getContext(), PieGraphTransportActivity.class);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    }
                    if (which == 2) {
                        Intent intent = new Intent(getContext(), PieGraphRouteActivity.class);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    }
                }
            }).create();
        }
        else {
            return new AlertDialog.Builder(getActivity()).setTitle(getResources().getString(R.string.graphModeDialogTitle)).
                    setSingleChoiceItems(monthYearOptions, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        yearMode = false;  // reset it to original value.
                        Intent intent = new Intent(getContext(), YearlyEmissionLineGraphActivity.class);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    }
                    if (which == 1) {
                        Intent intent = new Intent(getContext(), PieGraphTransportActivity.class);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    }
                    if (which == 2) {
                        Intent intent = new Intent(getContext(), PieGraphRouteActivity.class);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    }
                }
            }).create();
        }
    }
}
