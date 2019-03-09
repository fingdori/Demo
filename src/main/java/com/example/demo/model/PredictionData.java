package com.example.demo.model;

public class PredictionData {
    private String modelName;
    private double[] input;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public double[] getInput() {
        return input;
    }

    public void setInput(double[] input) {
        this.input = input;
    }

    public String inputDataToString() {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (Double d: input) {
            if(index != 0) {
                sb.append(",");
            }
            sb.append(d);
            index++;
        }
        return sb.toString();
    }
}
