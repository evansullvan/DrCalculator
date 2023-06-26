package com.bcm.drcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DistActivity extends AppCompatActivity {

    private EditText inputEditText;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private Button calculateButton;
    private TextView resultTextView;
    private Toolbar toolbar;

    private String[] distanceUnits = {"Meters", "Kilometers", "Miles", "Centimeters", "Inches", "Feet"};
    private double[] conversionFactors = {1.0, 0.001, 0.000621371, 100.0, 39.3701, 3.28084};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dist);
        toolbar = findViewById(R.id.toolbar);


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
                startActivity(new Intent(DistActivity.this, MathsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });


        inputEditText = findViewById(R.id.inputEditText);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, distanceUnits);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertDistance();
            }
        });
    }

    private void convertDistance() {
        String inputText = inputEditText.getText().toString().trim();
        if (inputText.isEmpty()) {
           // resultTextView.setText("Please enter a value");
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double inputValue = Double.parseDouble(inputText);
        int fromIndex = fromSpinner.getSelectedItemPosition();
        int toIndex = toSpinner.getSelectedItemPosition();

        double result = inputValue * conversionFactors[fromIndex] / conversionFactors[toIndex];
        String resultText = String.format("%.2f", result);

        resultTextView.setText(resultText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }
}