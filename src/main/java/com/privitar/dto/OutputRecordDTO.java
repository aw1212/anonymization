package com.privitar.dto;

public class OutputRecordDTO {

    private int inputTime;
    private int outputTime;
    private double rawValue;
    private double anonymisedValue;

    public int getInputTime() {
        return inputTime;
    }

    public void setInputTime(int inputTime) {
        this.inputTime = inputTime;
    }

    public int getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(int outputTime) {
        this.outputTime = outputTime;
    }

    public double getRawValue() {
        return rawValue;
    }

    public void setRawValue(double rawValue) {
        this.rawValue = rawValue;
    }

    public double getAnonymisedValue() {
        return anonymisedValue;
    }

    public void setAnonymisedValue(double anonymisedValue) {
        this.anonymisedValue = anonymisedValue;
    }
}
