package com.mcdevz.parkpal;

import android.Manifest;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.TintableBackgroundView;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.CompoundButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import com.mcdevz.parkpal.gtfs.ScheduleSystem;
import com.mcdevz.parkpal.uber.UberAPIController;
import com.reginald.editspinner.EditSpinner;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mcdevz.parkpal.directions.DirectionFinder;
import com.mcdevz.parkpal.directions.DirectionFinderListener;
import com.mcdevz.parkpal.directions.Route;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, DirectionFinderListener,
        PickupDialogCallback {

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
    private final static String TAG = "parkpal/MActivity";
    private ArrayList<String> history;
    private boolean parkedNRode;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private String mLocation;
    private Switch nightSwitch;
    private LinearLayout LinLayout;
    private TextView colour;
    private static final int PICKUP_YES_REQUEST = 1;
    static Boolean isTouched = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        LinLayout=(LinearLayout)findViewById(R.id.LinLayout);
        nightSwitch=(Switch)findViewById(R.id.nightSwitch);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.offline_msg, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ScheduleBrowser.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {

            Log.d(TAG, "Building main activity");
            ScheduleSystem.downloadFeeds(this);

            //setContentView(R.layout.activity_maps);
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
            /*When the night toggle is enabled on the side menu, night mode is enabled.*/
            nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (nightSwitch.isChecked()) {
                        LinLayout.setBackgroundColor(Color.DKGRAY);
                    }
                    else LinLayout.setBackgroundColor(Color.parseColor("#b20001"));

                }
        });

            // Setup location services
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        }
    }


    @Override
    public void onResume() {
        super.onResume();

        /* Check whether to prompt user to go pickup car */
        readParkNRode();
        promptForPickup();
    }

    @Override
    public void onStop() {
        super.onStop();

        writeParkNRode();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //TODO side menu code goes here
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }/* else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PICKUP_YES_REQUEST) {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                mLocation = null;
                Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT);
            }
            else {
                pickupYes(readLastParking());
            }
        }
    }

    /**
     * Checks if network is available.
     * @return true if connected through either wifi or lte
     */
    private boolean isNetworkAvailable() {

        Log.d(TAG, "Checking if network is available");

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    /**
     * Reads whether the last mode of transportation was park n ride from persistence.
     * Sets the value of the field parkNRode to the read value.
     */
    private void readParkNRode() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        parkedNRode = prefs.getBoolean(getString(R.string.parked_n_rode), false);
    }

    /**
     * Writes the current value of the field parkedNRode to persistence.
     */
    private void writeParkNRode() {
        writeParkNRode(parkedNRode);
    }

    /**
     * Writes to persistence the given boolean value for paredkNRode. Also sets the parkNRode field
     * to the given value.
     * @param value new value for persistent parkNRode
     */
    private void writeParkNRode(boolean value) {
        this.parkedNRode = value;
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getString(R.string.parked_n_rode), value);
        editor.apply();
    }

    /**
     * Reads the last parking spot from persistence.
     * @return name of the last parking spot, null if the value was never mapped
     */
    private String readLastParking() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        return prefs.getString(getString(R.string.last_parking), "");
    }

    /**
     * Writes the last parking location to persistence.
     */
    private void writeLastParking(String parkingName) {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.last_parking), parkingName);
        editor.apply();
    }

    /**
     * Checks if the last mode of transportation was park n ride before the app was closed.
     * If so, prompts the user to go pickup their car via transit.
     */
    private void promptForPickup() {

        // Do nothing if last mode of transportation was not park n ride
        if (!parkedNRode) return;

        // Get last parking
        String lastParking = readLastParking();

        /* Create dialog */
        PickupDialog pickupDialog = new PickupDialog();
        Bundle dialogArgs = new Bundle();
        dialogArgs.putString(PickupDialog.LAST_PARKING, lastParking);
        pickupDialog.setArguments(dialogArgs);
        pickupDialog.setCallback(this);

        // Show fragment
        pickupDialog.show(getFragmentManager(), "pickup");

    }

    public void onRadioButtonClicked(View view) {

        Log.d(TAG, "Radio clicked.");

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.radioDrive:
                if (checked)
                    transportation = "driving";
                break;
            case R.id.radioTransit:
                if (checked)
                    transportation = "transit";
                break;
            case R.id.radioWalk:
                if (checked)
                    transportation = "walking";
                break;
            case R.id.radioUber:
                if (checked) {
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
                writeParkNRode(false);
                new DirectionFinder(this, origin, destination, "driving").execute();
            } else if (transportation.equals("parknride")) {
                writeParkNRode(true);
                new DirectionFinder(this, origin, origin, "driving").execute();
            } else {
                writeParkNRode(false);
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

        // Add a marker in Montreal and move the camera, changed from the original Sydney location
        LatLng MTL = new LatLng(45.5, -73.57);
        mMap.addMarker(new MarkerOptions().position(MTL).title("Marker in MTL"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MTL));

        try {
            GeoJsonLayer layer = new GeoJsonLayer(getMap(), R.raw.stat_incitatifs, this);
            customizeMarkers(layer);
            layer.addLayerToMap();
        } catch (IOException e) {
            Log.e(TAG, "GeoJSON file could not be read");
        } catch (JSONException e) {
            Log.e(TAG, "GeoJSON file could not be converted to a JSONObject");
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
        Log.d(TAG, "Direction finder start.");
        progressDialog = ProgressDialog.show(this, "Please wait",
                "Computing Directions", true);

        getMap().clear();
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
                        Log.e(TAG, "GeoJSON file could not be read");
                    } catch (JSONException e) {
                        Log.e(TAG, "GeoJSON file could not be converted to a JSONObject");
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
                        Log.e(TAG, "GeoJSON file could not be read");
                    } catch (JSONException e) {
                        Log.e(TAG, "GeoJSON file could not be converted to a JSONObject");
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
                        Log.e(TAG, "GeoJSON file could not be read");
                    } catch (JSONException e) {
                        Log.e(TAG, "GeoJSON file could not be converted to a JSONObject");
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
        if (transportation.equals("uber")) {
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
            if (duration > route.duration.value) {
                duration = route.duration.value;
                timeRoute = route;
            }
            if (distance > route.distance.value) {
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
                writeLastParking(closestParking);
                if (distRoute.distance.value == 0) {
                    new DirectionFinder(this, etOrigin.getText().toString(), closestParking, "driving").execute();
                } else {
                    new DirectionFinder(this, closestParking, etDestination.getText().toString(), "transit").execute();
                    transportation = "transit";
                }

            } catch (IOException e) {
                Log.e(TAG, "GeoJSON file could not be read");
            } catch (JSONException e) {
                Log.e(TAG, "GeoJSON file could not be converted to a JSONObject");
            }
        }


        if (distRoute.distance.value == 0) {
            double latA = routes.get(0).startLocation.latitude;
            double lngA = routes.get(0).startLocation.longitude;
            try {
                GeoJsonLayer layer = new GeoJsonLayer(getMap(), R.raw.stat_incitatifs, this);
                String closestParking = getClosestParking(latA, lngA, layer);
            } catch (IOException e) {
                Log.e(TAG, "GeoJSON file could not be read");
            } catch (JSONException e) {
                Log.e(TAG, "GeoJSON file could not be converted to a JSONObject");
            }

        }

        /* Check if routes were found */
        if (routes.isEmpty()) {
            Toast.makeText(this, "No routes were found between the entered origin and destination", Toast.LENGTH_SHORT).show();
            return;
        }


        drawRoute(getSelectedRoute(timeRoute, distRoute));

    }

    /**
     * Show directions from current location to last parking.
     * @param lastParking
     */
    @Override
    public void pickupYes(String lastParking) {
        pickupYes(lastParking, 0);
    }

    private void pickupYes(final String lastParking, final int tries) {

        /* Get last know location */
        //Check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PICKUP_YES_REQUEST);
            mLocation = null;   // Set current location to null if no permission
        }
        else {
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        String lat = String.valueOf(location.getLatitude());
                        String lon = String.valueOf(location.getLongitude());
                        mLocation = lat + "," + lon;
                    }
                    else {
                        if(tries < 5) {
                            Log.d(TAG, "Location try number " + tries);
                            pickupYes(lastParking, tries + 1);
                        }
                        else {
                            Toast.makeText(MainActivity.this, R.string.location_null, Toast.LENGTH_SHORT).show();
                            mLocation = null;
                        }
                    }
                }
            });
        }

        /* Set destination to parking and mode to bus*/
        etDestination.setText(lastParking);
        ((RadioGroup) findViewById(R.id.transGroup)).check(R.id.radioTransit);
        transportation = "transit";

        /* If current location was obtained, populate origin field and send directions request*/
        if(mLocation != null) {
            Log.d(TAG, "Setting origin to: " + mLocation + " and sending request");
            etOrigin.setText(mLocation);
            sendRequest();
        }
        else {
            Log.d(TAG, "Setting origin to empty string and prompting");
            etOrigin.setText("");
        }

    }

    @Override
    public void pickupNo() {
        // Nothing required, except dissmissing the prompt.
    }

    @Override
    public void pickupAlready() {
        // Prevent prompt from appearing
        writeParkNRode(false);
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
            Toast.makeText(this, "Warning! Don't Drink and drive! You can continue driving if you are sober. Otherwise, please use the provided Transit or Uber com.mcdevz.parkpal.directions!", Toast.LENGTH_LONG).show();
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

