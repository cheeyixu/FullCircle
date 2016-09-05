package com.example.yixu.fullcircle;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Yi Xu on 10/11/2015.
 */
public class SgFlyerActivity extends AppCompatActivity{

    static LatLonCoordinate conversionResult;
    static LatLng flyerPos;
    private GoogleMap googleMap;
    double[] longtitute = new double[100];
    double[] latitude = new double[100];
    AttractionDatabaseHandler db = null;
    String attractionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sg_flyer);

        db = new AttractionDatabaseHandler(this);

        new SaveTheFeed().execute();

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

            // Create a marker in the map at a given position with a title

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPictureClick(View view) {

        Intent goWebpage = new Intent();
        goWebpage.setAction(Intent.ACTION_VIEW);
        goWebpage.addCategory(Intent.CATEGORY_BROWSABLE);

        goWebpage.setData(Uri.parse("http://www.singaporeflyer.com/"));
        startActivity(goWebpage);

    }

    public void dateEnquiry(MenuItem item) {


            Intent goGetInfoAboutTheActivity = new Intent(this, GetInfoAboutTheActivity.class);

            goGetInfoAboutTheActivity.putExtra("longtitute", longtitute[0]);
            goGetInfoAboutTheActivity.putExtra("latitude", latitude[0]);
            goGetInfoAboutTheActivity.putExtra("attractionname", "Singapore Flyer");

            startActivity(goGetInfoAboutTheActivity);

            finish();



    }

    class SaveTheFeed extends AsyncTask<Void, Void, Void> {

        String jsonString = "";

        String result = "";


        @Override
        protected Void doInBackground(Void... params) {

            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());

            String url = "http://www.onemap.sg/API/services.svc/themeSearch?token=eTy4uP66Ed9VxJWa7+tn5macjhsyikeOw6IqPD3kYQZi5Xzahyp00quGLl0UybGxJnQsqGPjb0MDZevPgzfru3O0Ya9MvZEiZdDCoaWNSDvH6B1RQNRES7CngQn3AtA7&searchval=flyer&wc=theme=%27tourism%27&otptFlds=SEARCHVAL,CATEGORY,THEME,AGENCY&returnGeom=1&rset=1";


            HttpGet httpGet = new HttpGet(url);

            httpGet.setHeader("Content-type", "application/json");

            InputStream inputStream = null;

            try {

                HttpResponse httpResponse = httpClient.execute(httpGet);

                HttpEntity httpEntity = httpResponse.getEntity();

                inputStream = httpEntity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                jsonString = sb.toString();

                JSONObject jObject = new JSONObject(jsonString);

                JSONArray jsonArray = jObject.getJSONArray("SearchResults");

                outputResult(jsonArray);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            conversionResult = SVY21.computeLatLon(latitude[0], longtitute[0]);

            flyerPos = new LatLng(conversionResult.getLatitude(), conversionResult.getLongitude());

            Marker marker = googleMap.addMarker(new MarkerOptions().
                    position(flyerPos).title("Singapore Flyer"));

            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(flyerPos, 17.0f);
            googleMap.animateCamera(yourLocation);
        }

        protected void outputResult(JSONArray jsonArray) {

            try {


                JSONObject jsonObject = jsonArray.getJSONObject(1);

                longtitute[0] = jsonObject.getDouble("X");
                latitude[0] = jsonObject.getDouble("Y");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_activity_from_map_interface, menu);

        return super.onCreateOptionsMenu(menu);

    }

}
