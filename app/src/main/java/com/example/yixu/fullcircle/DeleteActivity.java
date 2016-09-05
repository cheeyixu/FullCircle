package com.example.yixu.fullcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import java.util.List;

/**
 * Created by Yi Xu on 11/11/2015.
 */
public class DeleteActivity extends AppCompatActivity {

    AttractionDatabaseHandler db = null;
    List<Attractions> attractions = null;
    String [] attractionArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delete_activity);

        db = new AttractionDatabaseHandler(this);

        attractions = db.getAllAttractions();

        attractionArray = new String[attractions.size()];

        int i = 0;

        for (Attractions curAttraction : attractions){

            attractionArray[i] = curAttraction.getAttractionName() + ": " + curAttraction.getVisitDate();
            i++;

        }

        AttractionListAdapter attractionListAdapter = new AttractionListAdapter(this, attractionArray);

        ListView attractionListView = (ListView) findViewById(R.id.attractionListView);

        attractionListView.setAdapter(attractionListAdapter);

        attractionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (Attractions curAttraction : attractions) {

                    if (parent.getItemAtPosition(position).equals(curAttraction.getAttractionName() + ": " + curAttraction.getVisitDate())) {

                        db.deleteAttraction(curAttraction);

                        Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_LONG).show();


                        break;

                    }

                }

            }
        });

        attractionListAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.delete_activity, menu);

        return super.onCreateOptionsMenu(menu);

    }

    public void onDoneDeletingClick(MenuItem item) {

        finish();
        System.exit(0);

    }
}
