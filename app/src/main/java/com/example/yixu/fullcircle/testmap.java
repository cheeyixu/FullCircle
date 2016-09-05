package com.example.yixu.fullcircle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


public class testmap extends AppCompatActivity {

    static final LatLonCoordinate conversionResult = SVY21.computeLatLon(29258.72398204,29879.82621632);
    static LatLng DerekPos = new LatLng(conversionResult.getLatitude() , conversionResult.getLongitude());
    private TextView textView;
    private GoogleMap googleMap;
    private Button testButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmap);
        textView = (TextView)findViewById(R.id.textview);
        testButton = (Button)findViewById(R.id.testButton);
        editText = (EditText)findViewById(R.id.editText);
        textView.setText(DerekPos.toString());
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
            Marker marker = googleMap.addMarker(new MarkerOptions().
                    position(DerekPos).title("Hello"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTestClick(View view) {

        new SaveTheFeed().execute();

    }

    class SaveTheFeed extends AsyncTask<Void, Void, Void>{

        String jsonString = "";

        String result = "";

        double [] longtitute = new double[100];
        double [] latitute = new double[100];

        @Override
        protected Void doInBackground(Void... params) {

            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());

            String url = "http://www.onemap.sg/APIV2/services.svc/basicSearchV2?token=2+Y84ep2R" +
                    "vGZeKvWhiWotgUL40hbi4nxaO3CgzuNCF3hp9P4v9cSA3tFz5vhpZaQKtSJB/wNDj/6A" +
                    "2z+cjJ1DGdXEC0fI94abHnFvR4CeeWCYh2o2aag7FJAb4UNVe0G&searchVal=City%20" +
                    "Hall&otptFlds=SEARCHVAL,CATEGORY&returnGeom=0&rset=1&projSys=WGS84";


            HttpGet httpGet = new HttpGet(url);

            httpGet.setHeader("Content-type", "application/json");

            InputStream inputStream = null;

            try{

                HttpResponse httpResponse = httpClient.execute(httpGet);

                HttpEntity httpEntity = httpResponse.getEntity();

                inputStream = httpEntity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                StringBuilder sb = new StringBuilder();

                String line = null;

                while((line = reader.readLine()) != null){

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

            editText.setText(result);

            for (int i = 0; i < 10; i++){

                DerekPos = new LatLng(latitute[i], longtitute[i]);

                Marker marker = googleMap.addMarker(new MarkerOptions().
                        position(DerekPos).title("Hello " + "you suck * " + i));

            }
        }

        protected void outputResult(JSONArray jsonArray) {

            try{

                for(int i = 1; i < jsonArray.length(); i++){

                    JSONObject translationObject =
                            jsonArray.getJSONObject(i);

                    result = result + Integer.toString(i) + " : " +
                            translationObject.getDouble("X") + translationObject.getDouble("Y") +
                            "\n";

                    longtitute[i] = translationObject.getDouble("X");
                    latitute[i] = translationObject.getDouble("Y");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
