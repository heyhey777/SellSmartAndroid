package com.mindtrek.sellsmart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText stockNameInput;
    private EditText stockPriceInput;
    private EditText shareAmountInput;
    private Button calculateButton;
    private Button strategySettingsButton;
    private TextView resultTextView;
    private Strategy strategy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        strategy = new Strategy(this);

        calculateButton.setOnClickListener(v -> calculateAndDisplayResults());
        strategySettingsButton.setOnClickListener(v -> openStrategySettings());
    }

    @Override
    protected void onResume() {
        super.onResume();
        strategy = new Strategy(this);  // Reload strategy when returning to this activity
    }

    private void initializeViews() {
        stockNameInput = findViewById(R.id.stockNameInput);
        stockPriceInput = findViewById(R.id.stockPriceInput);
        shareAmountInput = findViewById(R.id.shareAmountInput);
        calculateButton = findViewById(R.id.calculateButton);
        strategySettingsButton = findViewById(R.id.strategySettingsButton);
        resultTextView = findViewById(R.id.resultTextView);
    }

    private void openStrategySettings() {
        Intent intent = new Intent(this, StrategySettingsActivity.class);
        startActivity(intent);
    }

    private void calculateAndDisplayResults() {
        String stockName = stockNameInput.getText().toString();
        double stockPrice = Double.parseDouble(stockPriceInput.getText().toString());
        int shareAmount = Integer.parseInt(shareAmountInput.getText().toString());

        StringBuilder result = new StringBuilder();
        result.append("Results for ").append(stockName).append(":\n\n");

        result.append("Profit-Taking:\n");
        for (Strategy.StrategyStep step : strategy.getProfitTakingSteps()) {
            double targetPrice = stockPrice * (1 + step.percentage);
            int sharesToSell = (int) (shareAmount * step.sharePercentage);
            result.append(String.format(Locale.US, "Sell %d shares at $%.2f (+%.1f%%)\n",
                    sharesToSell, targetPrice, step.percentage * 100));
        }

        result.append("\nStop-Loss:\n");
        for (Strategy.StrategyStep step : strategy.getStopLossSteps()) {
            double targetPrice = stockPrice * (1 - step.percentage);
            int sharesToSell = (int) (shareAmount * step.sharePercentage);
            result.append(String.format(Locale.US, "Sell %d shares at $%.2f (-%.1f%%)\n",
                    sharesToSell, targetPrice, step.percentage * 100));
        }

        resultTextView.setText(result.toString());
    }
}