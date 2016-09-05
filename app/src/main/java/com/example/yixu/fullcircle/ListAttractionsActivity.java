package com.example.yixu.fullcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListAttractionsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_top_attractions);

        String[] attractions = {"Merlion Park", "Singapore Flyer", "Botanic Garden SG", "Armenian Church",
                "Telok Ayer Market", "Kusu Island"};

        ListAdapter attractionListAdapter = new AttractionListAdapter(this, attractions);

        ListView attractionListView = (ListView) findViewById(R.id.attractionListView);

        attractionListView.setAdapter(attractionListAdapter);

        attractionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 if (parent.getItemAtPosition(position).equals("Merlion Park")) {

                     Intent goMerlion = new Intent(getApplicationContext(), MerlionActivity.class);
                     startActivity(goMerlion);
                     finish();

                 }
                 else if (parent.getItemAtPosition(position).equals("Singapore Flyer")){

                     Intent goSingaporeFlyer = new Intent(getApplicationContext(), SgFlyerActivity.class);
                     startActivity(goSingaporeFlyer);
                     finish();

                 }


             }
         });

    }
}
