package com.mcdevz.parkpal;

import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mcdevz.parkpal.uber.UberAPIController;
import com.reginald.editspinner.EditSpinner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import modules.DirectionFinder;
import modules.DirectionFinderListener;
import modules.Route;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private Button btnGo;
    private RadioGroup transGroup;
    private RadioButton btnTrans;
    private String transportation;
    private List<Marker> originMarkers;
    private List<Marker> destinationMarkers;
    private List<Polyline> polylinePaths;
   // private View view;
    private EditSpinner etOrigin;
    private EditSpinner etDestination;
    private ProgressDialog progressDialog;
    private ArrayList<String> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (history == null) {
            history = new ArrayList<String>();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            history = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString("HISTORY", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnGo = (Button) findViewById(R.id.btnGo);
        etOrigin = (EditSpinner) findViewById(R.id.etOrigin);
        etDestination = (EditSpinner) findViewById(R.id.etDestination);
        transGroup = (RadioGroup) findViewById(R.id.transGroup);
        btnTrans = (RadioButton) findViewById(transGroup.getCheckedRadioButtonId());
        transportation = btnTrans.getText().toString();
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                history);
        etOrigin.setAdapter(adapter);
        etDestination.setAdapter(adapter);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addString(etDestination.getText().toString());
                addString(etOrigin.getText().toString());
                Log.d("parkpal", "transportation mode on Go click: " + transportation);
                sendRequest();
            }
        });
    }


    public void onRadioButtonClicked(View view){

        boolean checked = ((RadioButton)view).isChecked();
       // transGroup = findViewById(R.id.transGroup);
      //  btnTrans = (RadioButton) findViewById(transGroup.getCheckedRadioButtonId());

        switch(view.getId()){

            case R.id.radioDrive:
                if (checked)
                    transportation = "driving";
                break;
            case R.id.radioTransit:
                if (checked)
                    transportation = "transit";
                break;
            case R.id.radioWalk:
                if(checked)
                    transportation = "walking";
                break;
            case R.id.radioUber:
                if(checked) {
                    transportation = "uber";
                }
                break;
        }
    }

    // Send the destination request to DirectionFinder
    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();

        // If origin text box is left blank
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter your current location", Toast.LENGTH_SHORT).show();
            return;
        }

        // If destination text box is left blank
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter your destination", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if(transportation.equals("uber")) {
                Log.d("parkpal", "Starting direction finder with driving");
                new DirectionFinder(this, origin, destination, "driving").execute();
            }
            else {
                new DirectionFinder(this, origin, destination, transportation).execute();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Montral and move the camera, changed from the original Sydney location
        LatLng MTL = new LatLng(45.5, -73.57);
        mMap.addMarker(new MarkerOptions().position(MTL).title("Marker in MTL"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MTL));

        // Checking if all the required permissions are enabled in the Android Manifest file
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();

        /* Handle Uber requests */
        Log.d("parkpal", "transportation before Uber check: " + transportation);
        if(transportation.equals("uber")) {
            Log.d("parkpal", "Entering Uber subroutine");
            UberAPIController uber = new UberAPIController(getResources().getString(R.string.uber_client_id));
            LatLng origin = new LatLng(routes.get(0).startLocation.latitude, routes.get(0).startLocation.longitude);
            LatLng destination = new LatLng(routes.get(0).endLocation.latitude, routes.get(0).endLocation.longitude);
            try {
                uber.openUberApp(this, origin, destination);
            } catch (RuntimeException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return;
        }

        /* Check if routes were found */
        if(routes.isEmpty()) {
            Toast.makeText(this, "No routes were found between the entered origin and destination", Toast.LENGTH_SHORT).show();
            return;
        }

        Route timeRoute = routes.get(0);
        Route distRoute = routes.get(0);
        int duration = timeRoute.duration.value;
        int distance = distRoute.distance.value;
        Log.d("parkpal", "Number of routes returned: " + routes.size());
        for (Route route : routes){
            if(duration > route.duration.value) {
                duration = route.duration.value;
                timeRoute = route;
            }
            if(distance > route.distance.value) {
                distance = route.distance.value;
                distRoute = route;
            }

        }

        drawRoute(getSelectedRoute(timeRoute, distRoute));

    }

    /**
     * Determines which route to draw depending on user's selection.
     * @param timeRoute the fastest route
     * @param distRoute the shortest route
     * @return the route to be drawn
     */
    private Route getSelectedRoute(Route timeRoute, Route distRoute) {

        /* If both routes are the same, return anyone */
        if(timeRoute == distRoute) {
            Log.d("parkpal", "shortest and fastest routes are the same");
            return timeRoute;
        }

        /* Get the selected button */
        RadioGroup routeSel = findViewById(R.id.routeSelGroup);
        int selectedRoute = routeSel.getCheckedRadioButtonId();

        /* Switch on selected */
        switch(selectedRoute) {
            case R.id.radioShortestPath: Log.d("parkpal", "shortest route selected"); return distRoute;
            default: Log.d("parkpal", "fastest route selected"); return timeRoute;
        }

    }

    /**
     * Draws the given route on the map.
     * @param route
     */
    private void drawRoute(Route route) {
        if(route.containsBar && transportation.equals("driving")){
            Toast.makeText(this, "Warning! Dont Drink and drive!", Toast.LENGTH_SHORT).show();

        }
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
        ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
        ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                .title(route.startAddress)
                .position(route.startLocation)));
        destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                .title(route.endAddress)
                .position(route.endLocation)));

        PolylineOptions polylineOptions = new PolylineOptions().
                geodesic(true).
                color(Color.BLUE).
                width(10);

        for (int i = 0; i < route.points.size(); i++)
            polylineOptions.add(route.points.get(i));

        polylinePaths.add(mMap.addPolyline(polylineOptions));
    }

    public void addString(String s) {
        if (history == null) {
            history = new ArrayList<String>();
        }
        history.add(s);

        // save the task list to preference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("HISTORY", ObjectSerializer.serialize(history));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

}



