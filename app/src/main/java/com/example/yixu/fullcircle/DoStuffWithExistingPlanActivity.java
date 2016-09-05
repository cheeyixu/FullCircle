package com.example.yixu.fullcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DoStuffWithExistingPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_do_stuff_with_existing_plan);

    }

    public void onViewPlanButtonClick(View view) {

        Intent goViewPlan = new Intent(this, ViewPlanActivity.class);

        startActivity(goViewPlan);

        finish();

    }

    public void onUpdatePlanClick(View view) {

        Intent selectOperationOfUpdate = new Intent(this, UpdateOperationActivity.class);

        startActivity(selectOperationOfUpdate);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent goToAfterLogin = new Intent(this, AfterLoginActivity.class);

        startActivity(goToAfterLogin);

        finish();

    }

}
