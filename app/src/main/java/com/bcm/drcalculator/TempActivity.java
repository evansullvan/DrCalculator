package com.bcm.drcalculator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TempActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner toSpinner;
    private Spinner fromSpinner;
    private EditText inputEditText;
    private TextView outputTextView;
    private Button calculateButton;

    private double inputValue;
    private double outputValue;
    private String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
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
                startActivity(new Intent(TempActivity.this, MathsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        // Initialize views
        toSpinner = findViewById(R.id.spinner);
        fromSpinner = findViewById(R.id.spinner2);
        inputEditText = findViewById(R.id.input);
        outputTextView = findViewById(R.id.tempOutput);
        calculateButton = findViewById(R.id.calulateBtn);

        // Set up spinner adapters
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, temperatureUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(adapter);
        fromSpinner.setAdapter(adapter);

        // Set default selection
        toSpinner.setSelection(0);
        fromSpinner.setSelection(1);

        // Set click listener for the calculate button
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTemperature();
            }
        });
    }



    private void convertTemperature() {
        String toUnit = toSpinner.getSelectedItem().toString();
        String fromUnit = fromSpinner.getSelectedItem().toString();

        // Retrieve input value
        try {
            inputValue = Double.parseDouble(inputEditText.getText().toString());
        } catch (NumberFormatException e) {
//            outputTextView.setText("Invalid input");
            Toast.makeText(this, "Temperture cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform temperature conversion
        switch (fromUnit) {
            case "Celsius":
                outputValue = convertFromCelsius(inputValue, toUnit);
                break;
            case "Fahrenheit":
                outputValue = convertFromFahrenheit(inputValue, toUnit);
                break;
            case "Kelvin":
                outputValue = convertFromKelvin(inputValue, toUnit);
                break;
            default:
                outputTextView.setText("Invalid conversion");
                return;
        }

        // Update output text view
        outputTextView.setText(String.valueOf(outputValue));
    }

    private double convertFromCelsius(double value, String toUnit) {
        switch (toUnit) {
            case "Celsius":
                return value;
            case "Fahrenheit":
                return (value * 9 / 5) + 32;
            case "Kelvin":
                return value + 273.15;
            default:
                return 0;
        }
    }

    private double convertFromFahrenheit(double value, String toUnit) {
        switch (toUnit) {
            case "Celsius":
                return (value - 32) * 5 / 9;
            case "Fahrenheit":
                return value;
            case "Kelvin":
                return (value + 459.67) * 5 / 9;
            default:
                return 0;
        }
    }

    private double convertFromKelvin(double value, String toUnit) {
        switch (toUnit) {
            case "Celsius":
                return value - 273.15;
            case "Fahrenheit":
                return (value * 9 / 5) - 459.67;
            case "Kelvin":
                return value;
            default:
                return 0;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }
}
