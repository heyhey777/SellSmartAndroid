package com.mindtrek.sellsmart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StrategySettingsActivity extends AppCompatActivity {
    private EditText etProfitTaking1, etProfitTaking2, etStopLoss1, etStopLoss2;
    private EditText etSharePercentage1, etSharePercentage2, etSharePercentage3, etSharePercentage4;
    private Button btnSaveStrategy;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_settings);

        sharedPreferences = getSharedPreferences("StrategyPrefs", MODE_PRIVATE);

        initializeViews();
        loadCurrentStrategy();

        btnSaveStrategy.setOnClickListener(v -> saveStrategy());
    }

    private void initializeViews() {
        etProfitTaking1 = findViewById(R.id.etProfitTaking1);
        etProfitTaking2 = findViewById(R.id.etProfitTaking2);
        etStopLoss1 = findViewById(R.id.etStopLoss1);
        etStopLoss2 = findViewById(R.id.etStopLoss2);
        etSharePercentage1 = findViewById(R.id.etSharePercentage1);
        etSharePercentage2 = findViewById(R.id.etSharePercentage2);
        etSharePercentage3 = findViewById(R.id.etSharePercentage3);
        etSharePercentage4 = findViewById(R.id.etSharePercentage4);
        btnSaveStrategy = findViewById(R.id.btnSaveStrategy);
    }

    private void loadCurrentStrategy() {
        etProfitTaking1.setText(String.valueOf(sharedPreferences.getFloat("profitTaking1", 10f)));
        etProfitTaking2.setText(String.valueOf(sharedPreferences.getFloat("profitTaking2", 20f)));
        etStopLoss1.setText(String.valueOf(sharedPreferences.getFloat("stopLoss1", 10f)));
        etStopLoss2.setText(String.valueOf(sharedPreferences.getFloat("stopLoss2", 20f)));
        etSharePercentage1.setText(String.valueOf(sharedPreferences.getFloat("sharePercentage1", 25f)));
        etSharePercentage2.setText(String.valueOf(sharedPreferences.getFloat("sharePercentage2", 25f)));
        etSharePercentage3.setText(String.valueOf(sharedPreferences.getFloat("sharePercentage3", 25f)));
        etSharePercentage4.setText(String.valueOf(sharedPreferences.getFloat("sharePercentage4", 25f)));
    }

    private void saveStrategy() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("profitTaking1", Float.parseFloat(etProfitTaking1.getText().toString()));
        editor.putFloat("profitTaking2", Float.parseFloat(etProfitTaking2.getText().toString()));
        editor.putFloat("stopLoss1", Float.parseFloat(etStopLoss1.getText().toString()));
        editor.putFloat("stopLoss2", Float.parseFloat(etStopLoss2.getText().toString()));
        editor.putFloat("sharePercentage1", Float.parseFloat(etSharePercentage1.getText().toString()));
        editor.putFloat("sharePercentage2", Float.parseFloat(etSharePercentage2.getText().toString()));
        editor.putFloat("sharePercentage3", Float.parseFloat(etSharePercentage3.getText().toString()));
        editor.putFloat("sharePercentage4", Float.parseFloat(etSharePercentage4.getText().toString()));
        editor.apply();

        Toast.makeText(this, "Strategy saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}