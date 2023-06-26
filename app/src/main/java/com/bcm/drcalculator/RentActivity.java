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

public class RentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText monthlyIncomeEditText;
    private EditText savingsEditText;
    private EditText debtPaymentEditText;
    private EditText expensesEditText;
    private Button calculateButton;
    private TextView resultTextView;
    private TextView afterYearsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        toolbar = findViewById(R.id.toolbar);
        monthlyIncomeEditText = findViewById(R.id.MonthlyIncome);
        savingsEditText = findViewById(R.id.savings);
        debtPaymentEditText = findViewById(R.id.debtPayment);
        expensesEditText = findViewById(R.id.Expenses);
        calculateButton = findViewById(R.id.calulateBtn);
        resultTextView = findViewById(R.id.result);
        afterYearsTextView = findViewById(R.id.afteryears);


        toolbar.setTitle("Rent Calculator");
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
                startActivity(new Intent(RentActivity.this, FinancialActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateRent();
            }
        });


    }
    private void calculateRent() {
        // Validate EditTexts
        String monthlyIncomeStr = monthlyIncomeEditText.getText().toString().trim();
        String savingsRateStr = savingsEditText.getText().toString().trim();
        String debtPaymentRateStr = debtPaymentEditText.getText().toString().trim();
        String expensesRateStr = expensesEditText.getText().toString().trim();

        if (monthlyIncomeStr.isEmpty()) {
            monthlyIncomeEditText.setError("Please enter the monthly income");
            return;
        }
        if (savingsRateStr.isEmpty()) {
            savingsEditText.setError("Please enter the savings rate");
            return;
        }
        if (debtPaymentRateStr.isEmpty()) {
            debtPaymentEditText.setError("Please enter the debt payment rate");
            return;
        }
        if (expensesRateStr.isEmpty()) {
            expensesEditText.setError("Please enter the expenses rate");
            return;
        }

        // Parse input values
        double monthlyIncome = Double.parseDouble(monthlyIncomeStr);
        double savingsRate = Double.parseDouble(savingsRateStr) / 100.0;
        double debtPaymentRate = Double.parseDouble(debtPaymentRateStr);
        double expensesRate = Double.parseDouble(expensesRateStr);

        // Validate input values
        if (monthlyIncome >= 10000000) {
            monthlyIncomeEditText.setError("Monthly income cannot exceed 10,000,000");
            return;
        }
        if (savingsRate >= 0.99) {
            savingsEditText.setError("Savings rate cannot exceed 99%");
            return;
        }
        if (debtPaymentRate >= monthlyIncome) {
            debtPaymentEditText.setError("Debt payment should not exceed the monthly income");
            return;
        }
        if (expensesRate >= monthlyIncome) {
            expensesEditText.setError("Expenses should exceed the monthly income");
            return;
        }

        // Perform rent calculation
        double monthlyNetBeforeSavings = monthlyIncome - debtPaymentRate - expensesRate;
        double savings = monthlyNetBeforeSavings * savingsRate;
        double afterEverything = monthlyNetBeforeSavings - savings;

        resultTextView.setText(String.format("$%,.2f", afterEverything));
        afterYearsTextView.setText("Maximum amount for monthly rent");

        // Check if debt payment or expenses exceed the monthly income
        if (debtPaymentRate > monthlyIncome || expensesRate > monthlyIncome) {
            // Show a popup or display a message advising to visit a financial advisor
            showFinancialAdvisorPopup();
        }
    }

    private void showFinancialAdvisorPopup() {
        // Display a popup or a Toast message advising the user to visit a financial advisor
        Toast.makeText(this, "Please consider consulting a financial advisor", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }
}