package com.example.etienne.connectthedots;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exitAppOnClick(View view) {
        finish();
        System.exit(0);
    }

    public void buttonOnClick(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
