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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText input;
    private TextView output1;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;

    String[] fromOptions = {"Celsius", "Fahrenheit", "Kelvin"};
    String[] toOptions = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        toolbar = findViewById(R.id.toolbar);
        spinnerTo = findViewById(R.id.spinner);
        spinnerFrom = findViewById(R.id.spinner2);
        input = findViewById(R.id.input);
        output1 = findViewById(R.id.tempOutput); // Initialize output1 TextView

        toolbar.setTitle("Temperature");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fromOptions);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(fromAdapter);

        ArrayAdapter<String> toAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, toOptions);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(toAdapter);

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

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                onNumberEntered();
            }
        });
    }

    private void onNumberEntered() {
        double output = 0; // Declare output as a local variable
        String toString1 = spinnerTo.getSelectedItem().toString();
        String fromString = spinnerFrom.getSelectedItem().toString();

        if ("Celsius".equals(toString1)) {
            if ("Celsius".equals(fromString)) {
                output = Double.parseDouble(String.valueOf(input.getText())) * 1;
            } else if ("Kelvin".equals(fromString)) {
                output = Double.parseDouble(String.valueOf(input.getText())) + 273.15;
            } else if ("Fahrenheit".equals(fromString)) {
                output = ((Double.parseDouble(String.valueOf(input.getText())) * 9) / 5) + 32;
            }
        } else if ("Kelvin".equals(toString1)) {
            if ("Celsius".equals(fromString)) {
                output = Double.parseDouble(String.valueOf(input.getText())) - 273.15;
            } else if ("Kelvin".equals(fromString)) {
                output = Double.parseDouble(String.valueOf(input.getText())) * 1;
            } else if ("Fahrenheit".equals(fromString)) {
                output = (((Double.parseDouble(String.valueOf(input.getText())) - 273.15) * 9) / 5) + 32;
            }
        } else if ("Fahrenheit".equals(toString1)) {
            if ("Celsius".equals(fromString)) {
                output = ((Double.parseDouble(String.valueOf(input.getText())) - 32) * 5) / 9;
            } else if ("Kelvin".equals(fromString)) {
                output = Double.parseDouble(String.valueOf(input.getText())) * 1;
            } else if ("Fahrenheit".equals(fromString)) {
                output = (((Double.parseDouble(String.valueOf(input.getText())) - 32) * 5) / 9) + 273.15;
            }
        }
        double output2 = Math.round(output * 10000.0) / 10000.0;
        output1.setText(String.valueOf(output2));
    }
}
