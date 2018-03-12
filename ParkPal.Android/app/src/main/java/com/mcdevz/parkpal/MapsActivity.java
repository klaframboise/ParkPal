package com.mcdevz.parkpal;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import com.mcdevz.parkpal.uber.UberAPIController;
import com.reginald.editspinner.EditSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

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
    private final static String mLogTag = "GeoJson";
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
            case R.id.radioParknride:
                if (checked) {
                    transportation = "parknride";
                }
        }
    }

    // Send the destination request to DirectionFinder
    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();

        try {
            if (transportation.equals("uber")) {
                Log.d("parkpal", "Starting direction finder with driving");
                new DirectionFinder(this, origin, destination, "driving").execute();
            }
            else if (transportation.equals("parknride")) {

                new DirectionFinder(this, origin, origin, "driving").execute();
            }
            else {
                new DirectionFinder(this, origin, destination, transportation).execute();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // If origin text box is left blank
        //if (origin.isEmpty()) {
            //Toast.makeText(this, "Please enter your current location", Toast.LENGTH_SHORT).show();
            //return;
        //}

        // If destination text box is left blank
        //if (destination.isEmpty()) {
            //Toast.makeText(this, "Please enter your destination", Toast.LENGTH_SHORT).show();
            //return;
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

        // Add a marker in Montreal and move the camera, changed from the original Sydney location
        LatLng MTL = new LatLng(45.5, -73.57);
        mMap.addMarker(new MarkerOptions().position(MTL).title("Marker in MTL"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MTL));

        try {
            GeoJsonLayer layer = new GeoJsonLayer(getMap(), R.raw.stat_incitatifs, this);
            customizeMarkers(layer);
            layer.addLayerToMap();
        } catch (IOException e) {
            Log.e(mLogTag, "GeoJSON file could not be read");
        } catch (JSONException e) {
            Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
        }

        // Checking if all the required permissions are enabled in the Android Manifest file
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    public void customizeMarkers(GeoJsonLayer layer) {

        for (GeoJsonFeature feature : layer.getFeatures()) {

            BitmapDescriptor pointIcon = BitmapDescriptorFactory.defaultMarker();

            GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();

            pointStyle.setIcon(pointIcon);
            pointStyle.setTitle(feature.getProperty(("NOM_STAT")));
            pointStyle.setSnippet(Integer.parseInt(feature.getProperty("STAT_REG")) + " total parking spots, " +
                    Integer.parseInt(feature.getProperty("STAT_VELO")) + " total bike parking spots");
            feature.setPointStyle(pointStyle);
        }
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait",
                "Computing Directions", true);

        if (transportation != "parknride") {


            if (originMarkers != null) {
                for (Marker marker : originMarkers) {
                    marker.remove();
                    getMap().clear();
                    try {
                        GeoJsonLayer layer = new GeoJsonLayer(getMap(), R.raw.stat_incitatifs, this);
                        customizeMarkers(layer);
                        layer.addLayerToMap();
                    } catch (IOException e) {
                        Log.e(mLogTag, "GeoJSON file could not be read");
                    } catch (JSONException e) {
                        Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
                    }
                }
            }

            if (destinationMarkers != null) {
                for (Marker marker : destinationMarkers) {
                    marker.remove();
                    getMap().clear();
                    try {
                        GeoJsonLayer layer = new GeoJsonLayer(getMap(), R.raw.stat_incitatifs, this);
                        customizeMarkers(layer);
                        layer.addLayerToMap();
                    } catch (IOException e) {
                        Log.e(mLogTag, "GeoJSON file could not be read");
                    } catch (JSONException e) {
                        Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
                    }
                }
            }

            if (polylinePaths != null) {
                for (Polyline polyline : polylinePaths) {
                    polyline.remove();
                    getMap().clear();
                    try {
                        GeoJsonLayer layer = new GeoJsonLayer(getMap(), R.raw.stat_incitatifs, this);
                        customizeMarkers(layer);
                        layer.addLayerToMap();
                    } catch (IOException e) {
                        Log.e(mLogTag, "GeoJSON file could not be read");
                    } catch (JSONException e) {
                        Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
                    }
                }
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


        Route timeRoute = routes.get(0);
        Route distRoute = routes.get(0);
        int duration = timeRoute.duration.value;
        int distance = distRoute.distance.value;
        Log.d("parkpal", "Number of routes returned: " + routes.size());
        for (Route route : routes) {
            if(duration > route.duration.value) {
                duration = route.duration.value;
                timeRoute = route;
            }
            if(distance > route.distance.value) {
                distance = route.distance.value;
                distRoute = route;
            }

        }

        if (transportation.equals("parknride")) {

            double latA = routes.get(0).startLocation.latitude;
            double lngA = routes.get(0).startLocation.longitude;
            try {
                GeoJsonLayer layer = new GeoJsonLayer(getMap(), R.raw.stat_incitatifs, this);
                String closestParking = getClosestParking(latA, lngA, layer);
                if (distRoute.distance.value == 0) {
                    new DirectionFinder(this, etOrigin.getText().toString(), closestParking, "driving").execute();
                }

                else {
                    new DirectionFinder(this, closestParking, etDestination.getText().toString(), "transit").execute();
                    transportation = "transit";
                }

            } catch (IOException e) {
                Log.e(mLogTag, "GeoJSON file could not be read");
            } catch (JSONException e) {
                Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
            }
        }


        if (distRoute.distance.value == 0) {
            double latA = routes.get(0).startLocation.latitude;
            double lngA = routes.get(0).startLocation.longitude;
            try {
                GeoJsonLayer layer = new GeoJsonLayer(getMap(), R.raw.stat_incitatifs, this);
                String closestParking = getClosestParking(latA, lngA, layer);
            } catch (IOException e) {
                Log.e(mLogTag, "GeoJSON file could not be read");
            } catch (JSONException e) {
                Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
            }

        }

        /* Check if routes were found */
        if(routes.isEmpty()) {
            Toast.makeText(this, "No routes were found between the entered origin and destination", Toast.LENGTH_SHORT).show();
            return;
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
            Toast.makeText(this, "Warning! Don't Drink and drive! You can continue driving if you are sober. Otherwise, please use the provided Transit or Uber directions!", Toast.LENGTH_LONG).show();
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

    protected GoogleMap getMap() {
        return mMap;
    }

    public String getClosestParking(double latA, double lngA, GeoJsonLayer layer) {

        Geocoder gc = new Geocoder(this, Locale.getDefault());
        float minDistance = -1;
        String closestParking = null;
        double latB;
        double lngB;

        /*try {

            list = gc.getFromLocationName(locationA, 1);

            if (gc.isPresent()) {

                while (list.size() == 0) {
                    list = gc.getFromLocationName(locationA, 1);
                }
                if (list.size() > 0) {
                    Address address = list.get(0);
                    latA = address.getLatitude();
                    lngA = address.getLongitude();
                }
            }
            else{
                System.out.println("wsuppppppppppp");
            }

        }
            catch (IOException ex) {
            ex.printStackTrace();
        }
        */
            for (GeoJsonFeature feature : layer.getFeatures()) {

                latB = Double.parseDouble(feature.getProperty("LATITUDE"));
                lngB = Double.parseDouble(feature.getProperty("LONGITUDE"));

                float[] results = new float[1];
                Location.distanceBetween(latA, lngA, latB, lngB, results);
                float distance = results[0];


                if (minDistance == -1 || distance < minDistance) {
                    minDistance = distance;
                    closestParking = feature.getProperty("NOM_STAT");
                }

            }

            return closestParking;
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



