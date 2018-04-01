package com.mcdevz.parkpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class Night extends AppCompatActivity {


   // public static Switch nightSwitch;
    public static LinearLayout LinLayout;
    private TextView colour;
    public static Boolean unitUS = false;
    public static Boolean checked1 = false;
    public static Boolean checked2 = false;
    public MainActivity ma = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);

        /*When the night toggle is enabled on the side menu, night mode is enabled.*/

        final Switch nightSwitch = (Switch)  findViewById(R.id.nightSwitch);
        nightSwitch.setChecked(checked1);
        nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (nightSwitch.isChecked()) {
                checked1 = true;
                ma.nightMode = true;
            } else {
                    ma.nightMode = false;
                    checked1 = false;
                }
            }
        });

        final Switch unitSwitch = (Switch) findViewById(R.id.unitSwitch);
        unitSwitch.setChecked(checked2);
        unitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (unitSwitch.isChecked()) {
                    checked2 = true;
                    unitUS = true;
                } else {
                    unitUS = false;
                    checked2 = false;
                }
            }
        });

    }




    public void onBackClick(View view) {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }
}
