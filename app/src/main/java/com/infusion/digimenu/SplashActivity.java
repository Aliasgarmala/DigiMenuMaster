package com.infusion.digimenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SplashActivity extends Activity {
    private Button mShowMenuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mShowMenuBtn = (Button) findViewById(R.id.showMenuButton);
        mShowMenuBtn.setOnClickListener(mShowMenuClickListener);

        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/lily_script_one_regular.ttf"));
    }

    private final View.OnClickListener mShowMenuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // navigate to menu activity
            Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    };
}
