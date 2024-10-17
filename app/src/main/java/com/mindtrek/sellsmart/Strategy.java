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
    private String currentStrategyName;

    public Strategy(Context context) {
        sharedPreferences = context.getSharedPreferences("StrategyPrefs", Context.MODE_PRIVATE);
        currentStrategyName = sharedPreferences.getString("currentStrategy", "Default");
        loadStrategy();
    }

    private void loadStrategy() {
        profitTakingSteps = new ArrayList<>();
        stopLossSteps = new ArrayList<>();

        profitTakingSteps.add(new StrategyStep(
                sharedPreferences.getFloat(currentStrategyName + "_profitTaking1", 10f) / 100,
                sharedPreferences.getFloat(currentStrategyName + "_sharePercentage1", 25f) / 100
        ));
        profitTakingSteps.add(new StrategyStep(
                sharedPreferences.getFloat(currentStrategyName + "_profitTaking2", 20f) / 100,
                sharedPreferences.getFloat(currentStrategyName + "_sharePercentage2", 25f) / 100
        ));

        stopLossSteps.add(new StrategyStep(
                sharedPreferences.getFloat(currentStrategyName + "_stopLoss1", 10f) / 100,
                sharedPreferences.getFloat(currentStrategyName + "_sharePercentage3", 25f) / 100
        ));
        stopLossSteps.add(new StrategyStep(
                sharedPreferences.getFloat(currentStrategyName + "_stopLoss2", 20f) / 100,
                sharedPreferences.getFloat(currentStrategyName + "_sharePercentage4", 25f) / 100
        ));
    }

    public List<StrategyStep> getProfitTakingSteps() {
        return profitTakingSteps;
    }

    public List<StrategyStep> getStopLossSteps() {
        return stopLossSteps;
    }

    public String getCurrentStrategyName() {
        return currentStrategyName;
    }
}