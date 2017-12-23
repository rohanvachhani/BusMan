package com.rohan.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutUs extends WithMenuActivity {

    Animation animation;
    TextView textView;

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

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        textView = findViewById(R.id.text_v);
        textView.startAnimation(animation);
    }
}
