package com.mindtrek.sellsmart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class StrategySettingsActivity extends AppCompatActivity {
    private TextInputEditText etStrategyName, etProfitTaking1, etProfitTaking2, etStopLoss1, etStopLoss2;
    private TextInputEditText etSharePercentage1, etSharePercentage2, etSharePercentage3, etSharePercentage4;
    private Button btnSaveStrategy;
    private TextView tvTotalSharePercentage;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_settings);

        sharedPreferences = getSharedPreferences("StrategyPrefs", MODE_PRIVATE);

        initializeViews();
        loadCurrentStrategy();
        setupTextChangeListeners();

        btnSaveStrategy.setOnClickListener(v -> saveStrategy());
    }

    private void initializeViews() {
        etStrategyName = findViewById(R.id.etStrategyName);
        etProfitTaking1 = findViewById(R.id.etProfitTaking1);
        etProfitTaking2 = findViewById(R.id.etProfitTaking2);
        etStopLoss1 = findViewById(R.id.etStopLoss1);
        etStopLoss2 = findViewById(R.id.etStopLoss2);
        etSharePercentage1 = findViewById(R.id.etSharePercentage1);
        etSharePercentage2 = findViewById(R.id.etSharePercentage2);
        etSharePercentage3 = findViewById(R.id.etSharePercentage3);
        etSharePercentage4 = findViewById(R.id.etSharePercentage4);
        btnSaveStrategy = findViewById(R.id.btnSaveStrategy);
        tvTotalSharePercentage = findViewById(R.id.tvTotalSharePercentage);
    }

    private void loadCurrentStrategy() {
        String currentStrategy = sharedPreferences.getString("currentStrategy", "Default");
        etStrategyName.setText(currentStrategy);
        etProfitTaking1.setText(String.valueOf(sharedPreferences.getFloat(currentStrategy + "_profitTaking1", 10f)));
        etProfitTaking2.setText(String.valueOf(sharedPreferences.getFloat(currentStrategy + "_profitTaking2", 20f)));
        etStopLoss1.setText(String.valueOf(sharedPreferences.getFloat(currentStrategy + "_stopLoss1", 10f)));
        etStopLoss2.setText(String.valueOf(sharedPreferences.getFloat(currentStrategy + "_stopLoss2", 20f)));
        etSharePercentage1.setText(String.valueOf(sharedPreferences.getFloat(currentStrategy + "_sharePercentage1", 25f)));
        etSharePercentage2.setText(String.valueOf(sharedPreferences.getFloat(currentStrategy + "_sharePercentage2", 25f)));
        etSharePercentage3.setText(String.valueOf(sharedPreferences.getFloat(currentStrategy + "_sharePercentage3", 25f)));
        etSharePercentage4.setText(String.valueOf(sharedPreferences.getFloat(currentStrategy + "_sharePercentage4", 25f)));
        updateTotalSharePercentage();
    }

    private void setupTextChangeListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateTotalSharePercentage();
            }
        };

        etSharePercentage1.addTextChangedListener(textWatcher);
        etSharePercentage2.addTextChangedListener(textWatcher);
        etSharePercentage3.addTextChangedListener(textWatcher);
        etSharePercentage4.addTextChangedListener(textWatcher);
    }

    private void updateTotalSharePercentage() {
        float total = 0;
        TextInputEditText[] shareInputs = {etSharePercentage1, etSharePercentage2, etSharePercentage3, etSharePercentage4};
        for (TextInputEditText input : shareInputs) {
            try {
                total += Float.parseFloat(input.getText().toString());
            } catch (NumberFormatException e) {
                // Ignore parse errors
            }
        }
        tvTotalSharePercentage.setText(String.format("Total Share Percentage: %.1f%%", total));
    }

    private boolean validateInputs() {
        if (etStrategyName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Strategy name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        TextInputEditText[] percentageInputs = {etProfitTaking1, etProfitTaking2, etStopLoss1, etStopLoss2,
                etSharePercentage1, etSharePercentage2, etSharePercentage3, etSharePercentage4};

        for (TextInputEditText input : percentageInputs) {
            if (input.getText().toString().isEmpty()) {
                Toast.makeText(this, "All percentage fields must be filled", Toast.LENGTH_SHORT).show();
                return false;
            }
            float value = Float.parseFloat(input.getText().toString());
            if (value < 0 || value > 100) {
                Toast.makeText(this, "Percentages must be between 0 and 100", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        float totalSharePercentage = Float.parseFloat(etSharePercentage1.getText().toString()) +
                Float.parseFloat(etSharePercentage2.getText().toString()) +
                Float.parseFloat(etSharePercentage3.getText().toString()) +
                Float.parseFloat(etSharePercentage4.getText().toString());

        if (Math.abs(totalSharePercentage - 100) > 0.01) {
            Toast.makeText(this, "Share percentages must sum to 100%", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveStrategy() {
        if (!validateInputs()) {
            return;
        }

        String strategyName = etStrategyName.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentStrategy", strategyName);
        editor.putFloat(strategyName + "_profitTaking1", Float.parseFloat(etProfitTaking1.getText().toString()));
        editor.putFloat(strategyName + "_profitTaking2", Float.parseFloat(etProfitTaking2.getText().toString()));
        editor.putFloat(strategyName + "_stopLoss1", Float.parseFloat(etStopLoss1.getText().toString()));
        editor.putFloat(strategyName + "_stopLoss2", Float.parseFloat(etStopLoss2.getText().toString()));
        editor.putFloat(strategyName + "_sharePercentage1", Float.parseFloat(etSharePercentage1.getText().toString()));
        editor.putFloat(strategyName + "_sharePercentage2", Float.parseFloat(etSharePercentage2.getText().toString()));
        editor.putFloat(strategyName + "_sharePercentage3", Float.parseFloat(etSharePercentage3.getText().toString()));
        editor.putFloat(strategyName + "_sharePercentage4", Float.parseFloat(etSharePercentage4.getText().toString()));
        editor.apply();

        Toast.makeText(this, "Strategy saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}