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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class CompoundInterestActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText initialInvestmentEditText;
    private EditText monthlyContributionEditText;
    private SeekBar loanTermSeekBar;
    private EditText interestRateEditText;
    private Button calculateButton;
    private TextView resultTextView;
    private TextView afteryears;
    private TextView loanTermTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compound_interest);
        toolbar = findViewById(R.id.toolbar);
        initialInvestmentEditText = findViewById(R.id.initialInvestment);
        monthlyContributionEditText = findViewById(R.id.monthlyContirbution);
        loanTermSeekBar = findViewById(R.id.loanTerm);
        loanTermTextView = findViewById(R.id.loanTermTextView);
        interestRateEditText = findViewById(R.id.interestRate);
        calculateButton = findViewById(R.id.calulateBtn);
        resultTextView = findViewById(R.id.resultTextView);
        afteryears = findViewById(R.id.afteryears);
        loanTermSeekBar.setOnSeekBarChangeListener(loanTermSeekBarListener);
        loanTermSeekBar.setProgress(40);


        toolbar.setTitle("Compound interest");
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
                startActivity(new Intent(CompoundInterestActivity.this, FinancialActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCompoundInterest();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }

    private void calculateCompoundInterest() {
        String initialInvestmentStr = initialInvestmentEditText.getText().toString().trim();
        String monthlyContributionStr = monthlyContributionEditText.getText().toString().trim();
        String interestRateStr = interestRateEditText.getText().toString().trim();

        if (initialInvestmentStr.isEmpty()) {
            initialInvestmentEditText.setError("Please enter the initial investment");
            return;
        }
        if (monthlyContributionStr.isEmpty()) {
            monthlyContributionEditText.setError("Please enter the monthly contribution");
            return;
        }
        if (interestRateStr.isEmpty()) {
            interestRateEditText.setError("Please enter the interest rate");
            return;
        }

        // Parse input values
        double initialInvestment = Double.parseDouble(initialInvestmentStr);
        double monthlyContribution = Double.parseDouble(monthlyContributionStr);
        double interestRate = Double.parseDouble(interestRateStr);

        // Validate input values
        if (initialInvestment > 10000000) {
            initialInvestmentEditText.setError("Initial investment must be under 10,000,000");
            return;
        }
        if (monthlyContribution > 100000) {
            monthlyContributionEditText.setError("Monthly contribution cannot exceed 100,000");
            return;
        }
        if (interestRate > 100) {
            interestRateEditText.setError("Interest rate cannot exceed 100");
            return;
        }

        // Validate SeekBar
        if (loanTermSeekBar.getProgress() == 0) {
            Toast.makeText(this, "Please move the loan term SeekBar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform the compound interest calculation
        int loanTerm = loanTermSeekBar.getProgress() + 1; // Add 1 to account for the starting month
        double compoundInterest = 0;
        double totalAmount = initialInvestment;

        for (int i = 0; i < loanTerm; i++) {
            compoundInterest += (totalAmount + monthlyContribution) * (interestRate / 100);
            totalAmount = (totalAmount + monthlyContribution) + compoundInterest;
        }

        // Update the result view
        afteryears.setText("After " + loanTerm + " years");
        resultTextView.setText(String.format("$%,.2f", totalAmount));
    }
    private void updateLoanTerm(int progress) {
        int loanTerm = progress + 1;  // Modify this to match your desired range and scale
        loanTermTextView.setText(String.valueOf(loanTerm));
    }

    private final SeekBar.OnSeekBarChangeListener loanTermSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updateLoanTerm(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // Not needed for this implementation
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Not needed for this implementation
        }
    };
}