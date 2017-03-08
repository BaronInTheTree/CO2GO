package ca.cmpt276.carbonTracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

public class ModifyCarActivity extends AppCompatActivity {

    int selectedCarIndex;
    CarbonModel modelInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_car);

        Intent callingIntent = getIntent();
        selectedCarIndex = callingIntent.getIntExtra("Index", 0);
        modelInstance = CarbonModel.getInstance();

        selectRouteButton();

        editCarButton();

        deleteCarButton();
    }

    public void selectRouteButton() {
        Button btn = (Button) findViewById(R.id.selectRoutebtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModifyCarActivity.this, SelectRouteActivity.class));
            }
        });
    }

    public void editCarButton() {
        Button btn = (Button) findViewById(R.id.editCarBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditCarActivity.makeIntent(ModifyCarActivity.this);
                intent.putExtra("Index", selectedCarIndex);
                startActivity(intent);
                finish();

            }
        });
    }

    public void deleteCarButton() {
        Button btn = (Button) findViewById(R.id.deleteCarBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelInstance.getCarCollection().hideCar(modelInstance.getSelectedCar());
                modelInstance.getCarCollection().removeCar(selectedCarIndex);
                Intent intent = SelectTransportationActivity.makeIntent(ModifyCarActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ModifyCarActivity.class);
    }
}