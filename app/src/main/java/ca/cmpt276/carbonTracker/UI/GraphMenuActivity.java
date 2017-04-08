package ca.cmpt276.carbonTracker.UI;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sasha.carbontracker.R;


public class GraphMenuActivity extends AppCompatActivity {

    // Need to re-set them back to false after graph shown to user.
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
                CalendarDialogMainMenu caldialog = new CalendarDialogMainMenu();
                caldialog.show(manager,getResources().getString(R.string.calendarModeDialog));
            }
        });

        Button fourWeekBtn = (Button) findViewById(R.id.last4week_btn);
        fourWeekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthMode = true;
                FragmentManager manager = getSupportFragmentManager();
                GraphModeDialog dialog = new GraphModeDialog();
                dialog.show(manager,getResources().getString(R.string.graphModeDialog));
            }
        });

        Button yearBtn = (Button) findViewById(R.id.lastYear_btn);
        yearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearMode = true;
                FragmentManager manager = getSupportFragmentManager();
                GraphModeDialog dialog = new GraphModeDialog();
                dialog.show(manager,getResources().getString(R.string.graphModeDialog));
            }
        });

        Button allJourneysBtn = (Button) findViewById(R.id.allJourney_btn);
        allJourneysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GraphMenuActivity.this, PieGraphAllJourneysActivity.class));
            }
        });

    }
}
