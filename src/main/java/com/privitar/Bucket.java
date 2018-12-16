package com.privitar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bucket {

    private long id;
    private double lowerBound;
    private double upperBound;
    private double totalValue;
    private List<InputRecord> inputRecords;
    private Set<Double> distinctValues;

    public Bucket() {
        inputRecords = new ArrayList<>();
        distinctValues = new HashSet<>();
    }

    public Bucket(double lowerBound, double upperBound) {
        this();
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public void add(InputRecord inputRecord) {
        inputRecords.add(inputRecord);

        double rawValue = inputRecord.getRawValue();
        distinctValues.add(Math.round(rawValue*100.0)/100.0);
        totalValue += rawValue;

        lowerBound = Math.min(lowerBound, rawValue);
        upperBound = Math.max(upperBound, rawValue);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDistinctSize() {
        return distinctValues.size();
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public List<InputRecord> getInputRecords() {
        return inputRecords;
    }

    public void setInputRecords(List<InputRecord> inputRecords) {
        this.inputRecords = inputRecords;
    }

    public Set<Double> getDistinctValues() {
        return distinctValues;
    }

    public void setDistinctValues(Set<Double> distinctValues) {
        this.distinctValues = distinctValues;
    }

}
