package com.example.yixu.fullcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class ViewPlanActivity extends AppCompatActivity {

    static LatLonCoordinate conversionResult;
    static LatLng AttractionPos;
    private GoogleMap googleMap;
    AttractionDatabaseHandler db;
    List<Attractions> attractions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_plan);

        db = new AttractionDatabaseHandler(this);

        attractions = db.getAllAttractions();

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            // Show a satellite map with roads
            /* MAP_TYPE_NORMAL: Basic map with roads.
            MAP_TYPE_SATELLITE: Satellite view with roads.
            MAP_TYPE_TERRAIN: Terrain view without roads.
            */
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            // Place dot on current location
            googleMap.setMyLocationEnabled(true);

            // Turns traffic layer on
            googleMap.setTrafficEnabled(true);

            // Enables indoor maps
            googleMap.setIndoorEnabled(true);

            // Turns on 3D buildings
            googleMap.setBuildingsEnabled(true);

            // Show Zoom buttons
            googleMap.getUiSettings().setZoomControlsEnabled(true);


            for (Attractions currentAttraction : attractions){

                conversionResult = SVY21.computeLatLon(currentAttraction.getAttractionLat(), currentAttraction.getAttractionLong());

                AttractionPos = new LatLng(conversionResult.getLatitude() , conversionResult.getLongitude());


                Marker marker = googleMap.addMarker(new MarkerOptions().
                        position(AttractionPos).title(currentAttraction.getAttractionName()+ ": " +currentAttraction.getVisitDate()));

            }

            if (attractions.size() == 0){

                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(AttractionPos, 12.0f);
                googleMap.animateCamera(yourLocation);

            } else {

                AttractionPos = new LatLng(1.3, 103.8);
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(AttractionPos, 12.0f);
                googleMap.animateCamera(yourLocation);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Override
    public void onBackPressed() {

        Intent goExistingPlan = new Intent(this, DoStuffWithExistingPlanActivity.class);

        startActivity(goExistingPlan);

        finish();

    }
}
