package com.mcdevz.parkpal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Intent history = getIntent();
        Bundle x = history.getExtras();

        startActivity(new Intent(History.this,MapsActivity.class));
        finish();
    }
}
