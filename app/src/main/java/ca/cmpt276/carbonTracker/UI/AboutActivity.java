package ca.cmpt276.carbonTracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupWebsiteText();
        setupCreditsText();
    }

    private void setupCreditsText() {
        TextView creditsScrollview = (TextView) findViewById(R.id.scrollText);
        String scrollviewText = new String();
        InputStream is = getResources().openRawResource(R.raw.links);
        //
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int i;


        try {
            i = is.read();
            while (i != -1) {
                os.write(i);
                i = is.read();
            }
            is.close();
            scrollviewText = os.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        creditsScrollview.setText(scrollviewText);

    }

    private void setupWebsiteText() {
        TextView website = (TextView) findViewById(R.id.websiteText);
        website.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }
}
