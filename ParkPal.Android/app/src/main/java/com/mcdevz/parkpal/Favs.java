package com.mcdevz.parkpal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class Favs extends AppCompatActivity {

    private Button bt;
    private Button d1;
    private Button d2;
    private Button d3;
    private Button d4;
    private Button d5;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView t5;
    private TextView stat;
    private ArrayList<String> favs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs);


        SharedPreferences prefFavs = getSharedPreferences(getString(R.string.favs), Context.MODE_PRIVATE);
        try {
           favs = (ArrayList<String>) ObjectSerializer.deserialize(prefFavs.getString("favs", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        bt = (Button) findViewById(R.id.bt);
        d1 = (Button) findViewById(R.id.d1);
        d2 = (Button) findViewById(R.id.d2);
        d3 = (Button) findViewById(R.id.d3);
        d4 = (Button) findViewById(R.id.d4);
        d5 = (Button) findViewById(R.id.d5);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView) findViewById(R.id.t5);
        stat = (TextView) findViewById(R.id.stat);
        while(favs.size() < 5){
            favs.add("empty");
        }

        t1.setText(favs.get(0));
        t2.setText(favs.get(1));
        t3.setText(favs.get(2));
        t4.setText(favs.get(3));
        t5.setText(favs.get(4));



        final Intent myIntent = new Intent(this, MainActivity.class);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(myIntent);

            }
        });
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFav(0);
                update();

            }
        });
        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFav(1);
                update();
            }
        });
        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFav(2);
                update();
            }
        });
        d4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFav(3);
                update();
            }
        });
        d5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFav(4);
                update();
            }
        });
    }

    public void editFav(int x) {
        if(favs.get(x).equals("empty")){
            stat.setText("Favorite was already deleted");
        }
        favs.set(x,"empty");
        // save the task list to preference
        SharedPreferences prefFavs = getSharedPreferences(getString(R.string.favs), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefFavs.edit();
        try {
            editor.putString("favs", ObjectSerializer.serialize(favs));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }
    public void clearFav() {
        favs = new ArrayList<String>();
        SharedPreferences prefFavs = getSharedPreferences(getString(R.string.favs), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefFavs.edit();
        try {
            editor.putString("favs", ObjectSerializer.serialize(favs));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public void update(){
        t1.setText(favs.get(0));
        t2.setText(favs.get(1));
        t3.setText(favs.get(2));
        t4.setText(favs.get(3));
        t5.setText(favs.get(4));
    }
}
