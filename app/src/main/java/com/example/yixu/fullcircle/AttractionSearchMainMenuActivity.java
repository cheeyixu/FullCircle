package com.example.yixu.fullcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class AttractionSearchMainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_activity_main);

    }

    public void onListAttractionClick(View view) {

        Intent goToTopAttraction = new Intent(this, ListAttractionsActivity.class);

        startActivity(goToTopAttraction);

        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent goToAfterLogin = new Intent(this, AfterLoginActivity.class);

        startActivity(goToAfterLogin);

        finish();

    }
}
