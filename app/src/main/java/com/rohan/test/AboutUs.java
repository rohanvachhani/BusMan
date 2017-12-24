package com.rohan.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutUs extends WithMenuActivity {


    //for differnt font
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    //

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Display.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboout_us);


    }
}
