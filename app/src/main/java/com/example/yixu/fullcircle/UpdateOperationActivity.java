package com.example.yixu.fullcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Yi Xu on 11/11/2015.
 */
public class UpdateOperationActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_update_operation);

    }

    public void onDeleteActivityClick(View view) {

        Intent goDeleteActivity = new Intent(this, DeleteActivity.class);

        startActivity(goDeleteActivity);

        finish();

    }
}
