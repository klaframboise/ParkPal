package com.mcdevz.parkpal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by alexandertorabi on 2018-04-01.
 */

public class FAQSection extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

    }


    public void onBackClick(View view) {
      finish();
    }
}
