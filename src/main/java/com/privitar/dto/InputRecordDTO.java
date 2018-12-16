package com.privitar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputRecordDTO {

    @JsonProperty("Time")
    private int time;

    @JsonProperty("Raw_Value")
    private double rawValue;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getRawValue() {
        return rawValue;
    }

    public void setRawValue(double rawValue) {
        this.rawValue = rawValue;
    }

}
