package com.bcm.drcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.bcm.drcalculator.R;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class AutoLoanActivity extends AppCompatActivity {
    private SeekBar loanAmountSeekBar;
    private TextView loanAmountTextView;
    private SeekBar loanTermSeekBar;
    private TextView loanTermTextView;
    private EditText interestRateEditText;
    private EditText registrationFeeEditText;
    private TextView loanResultTextView;
    private Button calculateButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_loan);
        toolbar = findViewById(R.id.toolbar);

        // Initialize views
        loanAmountSeekBar = findViewById(R.id.loanAmountSeekBar);
        loanAmountTextView = findViewById(R.id.loanAmountTextView);
        loanTermSeekBar = findViewById(R.id.loanTermSeekBar);
        loanTermTextView = findViewById(R.id.loanTermTextView);
        interestRateEditText = findViewById(R.id.interestRateEditText);
        registrationFeeEditText = findViewById(R.id.registrationFeeEditText);
        loanResultTextView = findViewById(R.id.loanResultTextView);
        calculateButton = findViewById(R.id.calulateBtn);

        // Set listeners
        loanAmountSeekBar.setOnSeekBarChangeListener(loanAmountSeekBarListener);
        loanTermSeekBar.setOnSeekBarChangeListener(loanTermSeekBarListener);
        calculateButton.setOnClickListener(calculateButtonClickListener);
        loanAmountSeekBar.setProgress(40000);
        loanTermSeekBar.setProgress(30);

        toolbar.setTitle("Auto Loan");
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
                startActivity(new Intent(AutoLoanActivity.this, FinancialActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });
    }

    // SeekBar listener for loan amount
    private final SeekBar.OnSeekBarChangeListener loanAmountSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updateLoanAmount(progress);
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

    // SeekBar listener for loan term
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

    // Button click listener for calculation
    private final View.OnClickListener calculateButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calculateLoan();
        }
    };

    private void updateLoanAmount(int progress) {
        int loanAmount = progress;  // Modify this to match your desired range and scale
        loanAmountTextView.setText(String.valueOf(loanAmount));
    }

    private void updateLoanTerm(int progress) {
        int loanTerm = progress + 1;  // Modify this to match your desired range and scale
        loanTermTextView.setText(String.valueOf(loanTerm));
    }

    private void calculateLoan() {

        if (Double.parseDouble(interestRateEditText.getText().toString()) > 100) {
            interestRateEditText.setError("Interest rate cannot exceed 100");
            return;
        }


        if (loanAmountSeekBar.getProgress() == 0 || loanAmountTextView.toString().isEmpty()) {
            Toast.makeText(this, "Please move the loan amount SeekBar", Toast.LENGTH_SHORT).show();
            return;
        }
        if (loanTermSeekBar.getProgress() == 0 || loanTermTextView.toString().isEmpty()) {
            Toast.makeText(this, "Please move the loan term SeekBar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate EditTexts
        if (interestRateEditText.getText().toString().isEmpty()) {
            interestRateEditText.setError("Please enter an interest rate");
            return;
        }
        if (registrationFeeEditText.getText().toString().isEmpty()) {
            registrationFeeEditText.setError("Please enter a registration fee");
            return;
        }
        // Get the input values
        int loanAmount = Integer.parseInt(loanAmountTextView.getText().toString());
        int loanTerm = Integer.parseInt(loanTermTextView.getText().toString());
        double interestRate = Double.parseDouble(interestRateEditText.getText().toString());
        double registrationFee = Double.parseDouble(registrationFeeEditText.getText().toString());

        // Perform the loan calculation
        double loanResult = loanAmount * (1 + interestRate / 100) + registrationFee;

        // Update the result view
        loanResultTextView.setText(String.format(Locale.US, "$%,.2f", loanResult));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }
}


