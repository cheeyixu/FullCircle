package com.example.yixu.fullcircle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AfterLoginActivity extends AppCompatActivity{

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_after_login);
    }


    public void onExistingPlanClick(View view) {

        Intent goDoSomethingWithExistingPlan = new Intent(this, DoStuffWithExistingPlanActivity.class);

        startActivity(goDoSomethingWithExistingPlan);

        finish();

    }

    public void onNewPlanClick(View view) {

        Intent goNewPlanInfoQueryActivity = new Intent(this, AttractionSearchMainMenuActivity.class);

        startActivity(goNewPlanInfoQueryActivity);

        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.after_login_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onBackPressed() {

        finish();
    }

    public void onLogout(MenuItem item) {

        pref = getSharedPreferences("testapp", MODE_PRIVATE);

        editor = pref.edit();

        editor.putString("Logged", "false");

        editor.commit();

        Intent goLogin = new Intent(this, LoginActivity.class);

        startActivity(goLogin);

        finish();

    }
}
