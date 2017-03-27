package ca.cmpt276.carbonTracker.UI;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.Internal_Logic.CalendarDialog;
import ca.cmpt276.carbonTracker.Internal_Logic.GraphModeDialog;


public class GraphMenuActivity extends AppCompatActivity {

    // NOTE: May need to re-set them back to false after graph shown to user.
    public static boolean yearMode = false;
    public static boolean monthMode = false;
    public static boolean dayMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_menu);

        setUpButtons();
    }

    private void setUpButtons() {

        Button singleDayBtn = (Button) findViewById(R.id.singleDay_btn);
        singleDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayMode = true;
                FragmentManager manager = getSupportFragmentManager();
                CalendarDialog caldialog = new CalendarDialog();
                caldialog.show(manager,"calendarDialog");
            }
        });

        Button fourWeekBtn = (Button) findViewById(R.id.last4week_btn);
        fourWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthMode = true;
                FragmentManager manager = getSupportFragmentManager();
                GraphModeDialog dialog = new GraphModeDialog();
                dialog.show(manager,"graphModeDialog");
            }
        });

        Button yearBtn = (Button) findViewById(R.id.lastYear_btn);
        yearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearMode = true;
                FragmentManager manager = getSupportFragmentManager();
                GraphModeDialog dialog = new GraphModeDialog();
                dialog.show(manager,"graphModeDialog");
            }
        });

        Button allJourneysBtn = (Button) findViewById(R.id.allJourney_btn);
        allJourneysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GraphMenuActivity.this, AllJourneysGraphActivity.class));
            }
        });

    }
}
