package com.example.yixu.fullcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yi Xu on 10/11/2015.
 */
public class GetInfoAboutTheActivity extends AppCompatActivity{

    EditText dateEditText;
    AttractionDatabaseHandler db = null;
    private Pattern pattern;
    private Matcher matcher;

    private static final String DATE_PATTERN =
            "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_info_about_activity);

        db = new AttractionDatabaseHandler(this);

        dateEditText = (EditText)findViewById(R.id.dateEditText);

        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "ddmmyyyy";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dateEditText.setText(current);
                    dateEditText.setSelection(sel < current.length() ? sel : current.length());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };

        dateEditText.addTextChangedListener(tw);

        pattern = Pattern.compile(DATE_PATTERN);

    }

    public void onConfirmDateAndTime(View view) {

        double longtitute = getIntent().getDoubleExtra("longtitute", 0);
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        String attractionName = getIntent().getStringExtra("attractionname");

        List<Attractions> attractions = db.getAllAttractions();

        boolean exist = false;

        matcher = pattern.matcher(dateEditText.getText().toString());

        if (matcher.matches()) {

            for (Attractions curAttraction : attractions) {

                if (curAttraction.getAttractionName().equals(attractionName.toString())) {

                    Toast.makeText(this, "Already planned!", Toast.LENGTH_LONG).show();
                    exist = true;
                    break;

                }

            }

            if (!exist) {

                db.addAttraction(new Attractions(attractionName.toString(), longtitute, latitude, dateEditText.getText().toString()));
                Toast.makeText(this, "Added successfully!", Toast.LENGTH_LONG).show();
                Intent goBackToLogin = new Intent(this, LoginActivity.class);
                startActivity(goBackToLogin);

            }

            Intent goBackToActivityMainMenu = new Intent(this, AttractionSearchMainMenuActivity.class);

            startActivity(goBackToActivityMainMenu);

            finish();

        }

        else{

            Toast.makeText(this, "Wrong date format!", Toast.LENGTH_LONG).show();
            dateEditText.setText("");

        }

    }


}
