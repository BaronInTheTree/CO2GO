package ca.cmpt276.carbonTracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import org.w3c.dom.Text;

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
        setupDisplayCarDetails();
        setupBackButton();
    }

    private void selectRouteButton() {
        Button btn = (Button) findViewById(R.id.selectRoutebtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModifyCarActivity.this, SelectRouteActivity.class));
                finish();
            }
        });
    }

    private void editCarButton() {
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

    private void setupBackButton(){
        Button backButton = (Button) findViewById(R.id.buttonBackModCar);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectTransportationActivity.makeIntent(ModifyCarActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    private void deleteCarButton() {
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

    private void setupDisplayCarDetails(){
        TextView displayNickname = (TextView) findViewById(R.id.textViewDisplayNickname);
        displayNickname.setText("" + modelInstance.getSelectedCar().getNickname());

        TextView displayMake = (TextView) findViewById(R.id.textViewDisplayMake);
        displayMake.setText("" + modelInstance.getSelectedCar().getMake());

        TextView displayModel = (TextView) findViewById(R.id.textViewDisplayModel);
        displayModel.setText("" + modelInstance.getSelectedCar().getModel());

        TextView displayYear = (TextView) findViewById(R.id.textViewDisplayYear);
        displayYear.setText("" + modelInstance.getSelectedCar().getYear());

        TextView displayTransmission = (TextView) findViewById(R.id.textViewDisplayTransmission);
        displayTransmission.setText("" + modelInstance.getSelectedCar().getTrany());

        TextView displayCylinders = (TextView) findViewById(R.id.textViewDisplayCylinders);
        displayCylinders.setText("" + modelInstance.getSelectedCar().getCylinders());

        TextView displayDisplacement = (TextView) findViewById(R.id.textViewDisplayDisplacement);
        displayDisplacement.setText("" + modelInstance.getSelectedCar().getDisplacement() + "L");

        TextView displayFuelType = (TextView) findViewById(R.id.textViewDisplayFuelType);
        displayFuelType.setText("" + modelInstance.getSelectedCar().getFuelType());
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ModifyCarActivity.class);
    }
}