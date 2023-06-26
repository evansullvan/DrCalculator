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

public class BinaryActivity extends AppCompatActivity {

    private EditText valueToConvertEditText;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private Button calculateButton;
    private Toolbar toolbar;
    private TextView conversionResultTextView;
    private String[] BinaryUnits = {"Binary", "Decimal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary);

        toolbar = findViewById(R.id.toolbar);
        valueToConvertEditText = findViewById(R.id.valueToConvert);
        fromSpinner = findViewById(R.id.spinner2);
        toSpinner = findViewById(R.id.spinner);
        calculateButton = findViewById(R.id.calulateBtn);
        conversionResultTextView = findViewById(R.id.conversionResult);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, BinaryUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(adapter);
        fromSpinner.setAdapter(adapter);

        toolbar.setTitle("Binary");
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
                startActivity(new Intent(BinaryActivity.this, MathsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertBinary();
            }
        });
    }

    private void convertBinary() {
        String valueToConvert = valueToConvertEditText.getText().toString().trim();

        if (valueToConvert.isEmpty()) {
            Toast.makeText(this, "Please enter a binary value", Toast.LENGTH_SHORT).show();
            return;
        }

        String fromUnit = fromSpinner.getSelectedItem().toString();
        String toUnit = toSpinner.getSelectedItem().toString();

        if (fromUnit.equals(toUnit)) {
            Toast.makeText(this, "Please select different conversion units", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fromUnit.equals("Binary") && toUnit.equals("Decimal")) {
            // Convert binary to decimal
            try {
                long decimalValue = Long.parseLong(valueToConvert, 2);
                conversionResultTextView.setText(String.valueOf(decimalValue));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid binary value", Toast.LENGTH_SHORT).show();
            }
        } else if (fromUnit.equals("Decimal") && toUnit.equals("Binary")) {
            // Convert decimal to binary
            try {
                long decimalValue = Long.parseLong(valueToConvert);
                String binaryValue = Long.toBinaryString(decimalValue);
                conversionResultTextView.setText(binaryValue);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid decimal value", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }
}