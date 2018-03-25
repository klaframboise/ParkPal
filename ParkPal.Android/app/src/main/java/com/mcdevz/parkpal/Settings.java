package com.mcdevz.parkpal;

/**
 * Created by adamgover on 3/17/18.
 */

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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import modules.DirectionFinder;
import modules.DirectionFinderListener;


public class Settings extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {


    private Button update;
    private RadioGroup units;
    private RadioButton kilometers;
    private RadioButton miles;
    private EditSpinner favorites;
    private ArrayList<String> favs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);
        if (favs == null) {
            favs = new ArrayList<String>();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            favs = (ArrayList<String>) ObjectSerializerFav.deserialize(prefs.getString("favs", ObjectSerializerFav.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        update = (Button) findViewById(R.id.update);
        favorites = (EditSpinner) findViewById(R.id.etOrigin);
        units = (RadioGroup) findViewById(R.id.units);
        kilometers = (RadioButton) findViewById(units.getCheckedRadioButtonId());
        miles = (RadioButton) findViewById(units.getCheckedRadioButtonId());
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                favs);
        favorites.setAdapter(adapter);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addString(favorites.getText().toString());
                update();
            }
        });
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.kilometers:
                if (checked)
                    units = "kilometers";
                break;
            case R.id.miles:
                if (checked)
                    units = "miles";
                break;
        }
    }

    private void update() {
        String addr = favorites.getText().toString();

    }


}
