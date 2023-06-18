package com.bcm.drcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MathsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button toTemp;
    private Button toDist;
    private Button toMatrix;
    private Button toRNG;
    private Button toBin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths);

        toolbar = findViewById(R.id.toolbar);
        toTemp = findViewById(R.id.toTemp);
        toDist = findViewById(R.id.toDistance);
        toMatrix = findViewById(R.id.toMatrix);
        toRNG = findViewById(R.id.toRNG);
        toBin = findViewById(R.id.toBinary);



        toolbar.setTitle("Maths");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MathsActivity.this, menuActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        toTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MathsActivity.this, TempActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        toDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MathsActivity.this, DistActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        toMatrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MathsActivity.this, MatrixActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        toRNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MathsActivity.this, randomNumberActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        toBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MathsActivity.this, BinaryActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

    }
}