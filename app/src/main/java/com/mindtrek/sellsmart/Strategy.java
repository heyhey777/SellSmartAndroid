package com.mindtrek.sellsmart;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;

public class Strategy {
    public static class StrategyStep {
        public double percentage;
        public double sharePercentage;

        public StrategyStep(double percentage, double sharePercentage) {
            this.percentage = percentage;
            this.sharePercentage = sharePercentage;
        }
    }

    private List<StrategyStep> profitTakingSteps;
    private List<StrategyStep> stopLossSteps;
    private SharedPreferences sharedPreferences;

    public Strategy(Context context) {
        sharedPreferences = context.getSharedPreferences("StrategyPrefs", Context.MODE_PRIVATE);
        loadStrategy();
    }

    private void loadStrategy() {
        profitTakingSteps = new ArrayList<>();
        stopLossSteps = new ArrayList<>();

        profitTakingSteps.add(new StrategyStep(
                sharedPreferences.getFloat("profitTaking1", 10f) / 100,
                sharedPreferences.getFloat("sharePercentage1", 25f) / 100
        ));
        profitTakingSteps.add(new StrategyStep(
                sharedPreferences.getFloat("profitTaking2", 20f) / 100,
                sharedPreferences.getFloat("sharePercentage2", 25f) / 100
        ));

        stopLossSteps.add(new StrategyStep(
                sharedPreferences.getFloat("stopLoss1", 10f) / 100,
                sharedPreferences.getFloat("sharePercentage3", 25f) / 100
        ));
        stopLossSteps.add(new StrategyStep(
                sharedPreferences.getFloat("stopLoss2", 20f) / 100,
                sharedPreferences.getFloat("sharePercentage4", 25f) / 100
        ));
    }

    public List<StrategyStep> getProfitTakingSteps() {
        return profitTakingSteps;
    }

    public List<StrategyStep> getStopLossSteps() {
        return stopLossSteps;
    }
}