package com.example.dhritiwasan.nightlife;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    /*
    1. Display total cost and total time for each route
    2. Brute force algorithm
    3. Remove the previous polyline and zoom in on current
    4. Remove markers that were not selected.
     */

    private GoogleMap mMap;
    private LatLng msb;
    private LatLng vivo;
    private LatLng rws;
    private LatLng zouk;
    private LatLng flyer;
    private LatLng orchard;
    private Button prev;
    private Button next;
    private TextView tv;
    PolylineOptions op;
    int i = 0;
    int ctr = 0;
    private HashMap<String, LatLng> places;
    boolean[] visitingLocations;
    double credit;
    String hotel;
    Algorithm.Route[] routes;
    final String[] locations = {"Marina Bay Sands", "Singapore Flyer", "Vivocity Singapore", "Resorts World Sentosa", "Orchard Road", "Zouk Singapore"};
    ArrayList<String> templateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Log.i("Dhriti", "I am in maps activity - Ob=nCreate()");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Log.i("dhriti", mapFragment.toString());
        mapFragment.getMapAsync(this);
        places = new HashMap<>();

        Intent startingIntent = getIntent();
        visitingLocations = startingIntent.getBooleanArrayExtra("LOCATIONS");
        hotel = startingIntent.getStringExtra("HOTEL");
        credit = startingIntent.getDoubleExtra("CREDIT", 0.0);
        Log.i("LOCATIONS", ""+visitingLocations[0]);
        Algorithm algo = new Algorithm(credit, hotel, visitingLocations);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Shree714.ttc");

        Button costText = (Button) findViewById(R.id.buttoncost);
        StringBuilder sbtime = new StringBuilder();
        sbtime.append("Cost: " + algo.totalPay +"\nTime: "+algo.totalTime);
        costText.setText(sbtime.toString());
        costText.setTypeface(custom_font);

        routes = algo.routes;


        // So, this is how I would implement the changing directions:
        final ArrayList<String> templateText = new ArrayList<String>(Arrays.asList("Go from ","\nto ", "\nby "));

        Button prev = (Button) findViewById(R.id.buttonprevdir);
        Button next = (Button) findViewById(R.id.buttonnextdir);

        final Button bgbutton = (Button) findViewById(R.id.bgButton);
        bgbutton.setTypeface(custom_font);
        StringBuilder sb = new StringBuilder();
        sb.append(templateText.get(0));
        sb.append(routes[i].from);
        sb.append(templateText.get(1));
        sb.append(routes[i].to);
        sb.append(templateText.get(2));
        sb.append(routes[i].mode);
        String direction = sb.toString();
        bgbutton.setText(direction);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctr!=0) {
                    ctr--;
                    i--;
                    StringBuilder sb = new StringBuilder();
                    sb.append(templateText.get(0));
                    sb.append(routes[i].from);
                    sb.append(templateText.get(1));
                    sb.append(routes[i].to);
                    sb.append(templateText.get(2));
                    sb.append(routes[i].mode);
                    String direction = sb.toString();
                    bgbutton.setText(direction);
                    AddPolyLine polyLine = new AddPolyLine();
                    polyLine.execute();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctr<routes.length-1) {
                    ctr++;
                    i++;
                    StringBuilder sb = new StringBuilder();
                    sb.append(templateText.get(0));
                    sb.append(routes[i].from);
                    sb.append(templateText.get(1));
                    sb.append(routes[i].to);
                    sb.append(templateText.get(2));
                    sb.append(routes[i].mode);
                    String direction = sb.toString();
                    bgbutton.setText(direction);
                    AddPolyLine polyLine = new AddPolyLine();
                    polyLine.execute();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i("dhriti", ""+googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style)));
        // Add a marker in Sydney and move the camera
        msb = new LatLng(1.284147, 103.860988);
        flyer = new LatLng(1.289632, 103.863126);
        vivo = new LatLng(1.264497, 103.822136);
        orchard = new LatLng(1.302046, 103.837829);
        zouk = new LatLng(1.290971, 103.846044);
        rws = new LatLng(1.259641, 103.821639);
        if (visitingLocations[0]) {

            googleMap.addMarker(new MarkerOptions().position(msb).title("Marina Bay Sands"));
        }
        if (visitingLocations[1]){
            googleMap.addMarker(new MarkerOptions().position(flyer).title("Singapore Flyer"));
        }
        if (visitingLocations[2]) {
            googleMap.addMarker(new MarkerOptions().position(vivo).title("Vivocity Singapore"));
        }
        if (visitingLocations[3]) {
            googleMap.addMarker(new MarkerOptions().position(rws).title("Resorts World Sentosa"));
        }
        if (visitingLocations[4]) {

            googleMap.addMarker(new MarkerOptions().position(orchard).title("Orchard Road"));
        }
        if (visitingLocations[5]) {

            googleMap.addMarker(new MarkerOptions().position(zouk).title("Zouk Singapore"));
        }
        places.put("Marina Bay Sands", msb);
        places.put("Singapore Flyer", flyer);
        places.put("Vivocity Singapore", vivo);
        places.put("Resorts World Sentosa", rws);
        places.put("Orchard Road", orchard);
        places.put("Zouk Singapore", zouk);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zouk, 12));
        AddPolyLine polyLine = new AddPolyLine();
        polyLine.execute();

    }

    private String getUrl(double latitude, double longitude, double end_latitude, double end_longitude)
    {
        Log.i("dhriti", "i get here");
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        Log.i("lol","lol")      ;
        googleDirectionsUrl.append("origin="+latitude+","+longitude);
        googleDirectionsUrl.append("&destination="+end_latitude+","+end_longitude);
        googleDirectionsUrl.append("&key="+"AIzaSyCAcfy-02UHSu2F6WeQ1rhQhkCr51eBL9g");
        googleDirectionsUrl.append("&mode=driving");
        System.out.println("here");
        return googleDirectionsUrl.toString();
    }
    public String getJson(String url) {
        String googleDirectionsData = "";
        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {

            e.printStackTrace();
        }
        System.out.println(googleDirectionsData);
        return googleDirectionsData;
    }
    public void displayDirection(String[] directionsList)
    {
        int count = directionsList.length;
        System.out.println(count);
        // remove previous polyline
        if (op != null) {
            Polyline polyline = this.mMap.addPolyline(op);
            polyline.remove();
        }
        for(int i = 0;i<count;i++)
        {
            Log.i("dhriti", directionsList[i]);
            PolylineOptions options = new PolylineOptions();
            options.color(Color.YELLOW);
            options.width(10);
            options.addAll(PolyUtil.decode(directionsList[i]));
            Log.i("POLYLINE_DECODE", PolyUtil.decode(directionsList[i]).toString());
            try {
                op = options;
                mMap.addPolyline(options);
            } catch (Exception e) {
                Log.i("dhriti", e.getMessage());
            }
        }
    }
    public String[] parseDirections(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("here3");
        return getPaths(jsonArray);
    }

    public String[] getPaths(JSONArray googleStepsJson )
    {
        int count = googleStepsJson.length();
        String[] polylines = new String[count];

        for(int i = 0;i<count;i++)
        {
            try {
                polylines[i] = getPath(googleStepsJson.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("here4");
        return polylines;
    }
    public String getPath(JSONObject googlePathJson)
    {
        String polyline = "";
        try {
            polyline = googlePathJson.getJSONObject("polyline").getString("points");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("here5");
        return polyline;
    }

    public class AddPolyLine extends AsyncTask<Integer, PolylineOptions, String[]> {


        @Override
        protected String[] doInBackground(Integer... integers){
            final ArrayList<String> templateText = new ArrayList<String>(Arrays.asList("Go from ","\nto ", "\nby "));
            final ArrayList<String> directionsList = new ArrayList<String>(Arrays.asList("Marina Bay Sands, VivoCity Singapore, walk", "Orchard Road, Zouk Singapore, Taxi"));
            LatLng from = places.get(routes[i].from);
            LatLng to = places.get(routes[i].to);
            return parseDirections(getJson(getUrl(from.latitude, from.longitude, to.latitude, to.longitude)));
        }

        @Override
        protected void onPostExecute(String[] po) {
            displayDirection(po);
        }
    }

    public static void main(String[] args) {
        Algorithm algo = new Algorithm(0.0, "Zouk Singapore", new boolean[] {true, true, false, false, false, false});
        System.out.println(algo.routes.length);
    }



}

