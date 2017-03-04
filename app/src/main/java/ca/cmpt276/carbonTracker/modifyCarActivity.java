package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sasha.carbontracker.R;

public class ModifyCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_car);

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
                startActivity(new Intent(ModifyCarActivity.this, EditCarActivity.class));
            }
        });
    }

    public void deleteCarButton() {
        Button btn = (Button) findViewById(R.id.deleteCarBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyCarActivity.this, MainMenuActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}