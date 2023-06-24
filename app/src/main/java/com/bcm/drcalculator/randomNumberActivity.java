package com.bcm.drcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class randomNumberActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText minNumEditText;
    private EditText maxNumEditText;
    private Button generateButton;
    private TextView randomOutputTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_number);
        toolbar = findViewById(R.id.toolbar);
        minNumEditText = findViewById(R.id.minNum);
        maxNumEditText = findViewById(R.id.maxNum);
        generateButton = findViewById(R.id.generateButton);
        randomOutputTextView = findViewById(R.id.randomOutput);



        toolbar.setTitle("Temperature");
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
                startActivity(new Intent(randomNumberActivity.this, MathsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomNumber();
            }
        });
    }

    private void generateRandomNumber() {
        String minNumText = minNumEditText.getText().toString().trim();
        String maxNumText = maxNumEditText.getText().toString().trim();

        if (minNumText.isEmpty() || maxNumText.isEmpty()) {
            Toast.makeText(this, "Please enter minimum and maximum numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        int minNum = Integer.parseInt(minNumText);
        int maxNum = Integer.parseInt(maxNumText);

        if (minNum >= maxNum) {
            Toast.makeText(this, "Maximum number cannot be smaller than the minimum number", Toast.LENGTH_SHORT).show();

            return;
        }

        Random random = new Random();
        int randomNumber = random.nextInt(maxNum - minNum + 1) + minNum;

        randomOutputTextView.setText(String.valueOf(randomNumber));
    }
}