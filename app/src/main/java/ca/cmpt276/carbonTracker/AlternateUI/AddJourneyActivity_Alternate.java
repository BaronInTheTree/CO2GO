package ca.cmpt276.carbonTracker.AlternateUI;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import ca.cmpt276.carbonTracker.Internal_Logic.CalendarObserver;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.Route;
import ca.cmpt276.carbonTracker.Internal_Logic.SkytrainDB;
import ca.cmpt276.carbonTracker.Internal_Logic.Transportation;
import ca.cmpt276.carbonTracker.UI.CalendarDialog;

public class AddJourneyActivity_Alternate extends AppCompatActivity {

    private CarbonModel model = CarbonModel.getInstance();
    private Date currentDate = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private Route selectedRoute;
    private Transportation selectedTransportation;
    private enum TransportType{WALKBIKE, CAR, BUS, SKYTRAIN};
    private TransportType transportType = TransportType.CAR;
    private SkytrainDB skytrainDB = new SkytrainDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journey__alternate);
        setupWalkButton();
        setupBikeButton();
        setupCarButton();
        setupBusButton();
        setupSkytrainButton();
        registerAsObserver();
        setupDateText();
        setupTransportModeText("Vehicle");
    }

    private void registerAsObserver() {
        CalendarDialog.addObserver(new CalendarObserver() {
            @Override
            public void updateGraphs() {
                currentDate = CalendarDialog.selectedDate;
                setupDateText();
            }
        });
    }

    private void setupTransportModeText(String mode) {
        TextView modeText = (TextView) findViewById(R.id.textViewDisplayModeSelected);
        modeText.setText(mode);
    }

    private void setupDateText() {
        TextView dateText = (TextView) findViewById(R.id.textViewJourneyDate);
        String date = dateFormat.format(currentDate);
        dateText.setText(date);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                CalendarDialog dialog = new CalendarDialog();
                dialog.show(manager,getResources().getString(R.string.calendarModeDialog));
            }
        });
    }

    private void setupWalkButton() {
        final Button walk = (Button) findViewById(R.id.buttonWalkMode);
        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walk.setBackgroundResource(R.drawable.button_selected_red);

                transportType = TransportType.WALKBIKE;
                setupTransportModeText("Walking");

                Button bike = (Button) findViewById(R.id.buttonBikeMode);
                bike.setBackgroundResource(R.drawable.button_default_brown_round);
                Button car = (Button) findViewById(R.id.buttonCarMode);
                car.setBackgroundResource(R.drawable.button_default_brown_round);
                Button bus = (Button) findViewById(R.id.buttonBusMode);
                bus.setBackgroundResource(R.drawable.button_default_brown_round);
                Button skytrain = (Button) findViewById(R.id.buttonSkytrainMode);
                skytrain.setBackgroundResource(R.drawable.button_default_brown_round);
            }
        });
    }

    private void setupBikeButton() {
        final Button selected = (Button) findViewById(R.id.buttonBikeMode);
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red);

                transportType = TransportType.WALKBIKE;
                setupTransportModeText("Biking");

                Button btn1 = (Button) findViewById(R.id.buttonWalkMode);
                btn1.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn2 = (Button) findViewById(R.id.buttonCarMode);
                btn2.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn3 = (Button) findViewById(R.id.buttonBusMode);
                btn3.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn4 = (Button) findViewById(R.id.buttonSkytrainMode);
                btn4.setBackgroundResource(R.drawable.button_default_brown_round);
            }
        });
    }

    private void setupCarButton() {
        final Button selected = (Button) findViewById(R.id.buttonCarMode);
        selected.setBackgroundResource(R.drawable.button_selected_red);
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red);

                transportType = TransportType.CAR;
                setupTransportModeText("Vehicle");

                Button btn1 = (Button) findViewById(R.id.buttonWalkMode);
                btn1.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn2 = (Button) findViewById(R.id.buttonBikeMode);
                btn2.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn3 = (Button) findViewById(R.id.buttonBusMode);
                btn3.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn4 = (Button) findViewById(R.id.buttonSkytrainMode);
                btn4.setBackgroundResource(R.drawable.button_default_brown_round);
            }
        });
    }

    private void setupBusButton() {
        final Button selected = (Button) findViewById(R.id.buttonBusMode);
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red);

                transportType = TransportType.BUS;
                setupTransportModeText("Bus");

                Button btn1 = (Button) findViewById(R.id.buttonWalkMode);
                btn1.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn2 = (Button) findViewById(R.id.buttonBikeMode);
                btn2.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn3 = (Button) findViewById(R.id.buttonCarMode);
                btn3.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn4 = (Button) findViewById(R.id.buttonSkytrainMode);
                btn4.setBackgroundResource(R.drawable.button_default_brown_round);
            }
        });
    }

    private void setupSkytrainButton() {
        final Button selected = (Button) findViewById(R.id.buttonSkytrainMode);
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red);

                transportType = TransportType.SKYTRAIN;
                setupTransportModeText("Skytrain");

                Button btn1 = (Button) findViewById(R.id.buttonWalkMode);
                btn1.setBackgroundResource(R.drawable.button_default_brown);
                Button btn2 = (Button) findViewById(R.id.buttonBikeMode);
                btn2.setBackgroundResource(R.drawable.button_default_brown);
                Button btn3 = (Button) findViewById(R.id.buttonBusMode);
                btn3.setBackgroundResource(R.drawable.button_default_brown);
                Button btn4 = (Button) findViewById(R.id.buttonBikeMode);
                btn4.setBackgroundResource(R.drawable.button_default_brown);
            }
        });
    }



    private void setupSelectMakeSpinner() {
        final Spinner selectMake = (Spinner) findViewById(R.id.spinnerSelectMake);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getCarData().getMakeList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectMake.setAdapter(spinnerArrayAdapter);
        selectMake.setSelection(0);

        selectMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
