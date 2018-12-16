package com.privitar.service.impl;

import com.privitar.Bucket;
import com.privitar.InputRecord;
import com.privitar.OutputRecord;
import com.privitar.service.StreamingKFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class BucketedStreamingKFilter implements StreamingKFilter {

    private static final int K_THRESHOLD = 5;

    private int time = 0;
    private double lowestValueSeen;
    private double highestValueSeen;

    private List<Bucket> buckets = new ArrayList<>();

    public BucketedStreamingKFilter() {
        Bucket bucket = new Bucket(Double.MIN_VALUE, Double.MAX_VALUE);
        buckets.add(bucket);
    }

    @Override
    public void processNewRecord(InputRecord input) {
        time = input.getTime();
        double rawValue = input.getRawValue();

        Bucket bucket;
        if (rawValue < lowestValueSeen) {
            lowestValueSeen = rawValue;
            bucket = findLowestBucket();
        } else if (rawValue > highestValueSeen) {
            highestValueSeen = rawValue;
            bucket = findHighestBucket();
        } else {
            bucket = findCorrectBucket(input);
        }

        addCurrentInputToBucket(input, bucket);
    }

    private void addCurrentInputToBucket(InputRecord inputRecord, Bucket bucket) {
        if (bucket == null) {
            throw new IllegalStateException("bucket cannot be null");
        }

        bucket.add(inputRecord);

        if (bucket.getDistinctSize() == K_THRESHOLD * 2) {
            split(bucket);
        }
    }

    private Bucket findLowestBucket() {
        Bucket lowestBucket = buckets.get(0);

        for (Bucket bucket : buckets) {
            if (bucket.getLowerBound() < lowestBucket.getLowerBound()) {
                lowestBucket = bucket;
            }
        }

        return lowestBucket;
    }

    private Bucket findHighestBucket() {
        Bucket highestBucket = buckets.get(0);

        for (Bucket bucket : buckets) {
            if (bucket.getUpperBound() > highestBucket.getUpperBound()) {
                highestBucket = bucket;
            }
        }

        return highestBucket;
    }

    private Bucket findCorrectBucket(InputRecord inputRecord) {
        double rawValue = inputRecord.getRawValue();

        for (Bucket bucket : buckets) {
            if (rawValue >= bucket.getLowerBound() && rawValue <= bucket.getUpperBound()) {
                return bucket;
            }
        }

        return null;
    }

    private void split(Bucket bucket) {
        List<InputRecord> inputRecords = bucket.getInputRecords();

        //sort bucket records
        inputRecords.sort(Comparator.comparingDouble(InputRecord::getRawValue));

        //divide bucket into two new buckets
        Bucket lowerBucket = new Bucket();
        Bucket upperBucket = new Bucket();

        int index = 0;
        while (lowerBucket.getDistinctSize() < K_THRESHOLD || inputRecords.get(index).getRawValue() == inputRecords.get(index + 1).getRawValue()) {
            InputRecord inputRecord = inputRecords.get(index++);
            lowerBucket.add(inputRecord);
        }

        while (index < inputRecords.size()) {
            InputRecord inputRecord = inputRecords.get(index++);
            upperBucket.add(inputRecord);
        }

        //remove bucket from bucket list
        buckets.remove(bucket);

        //add new buckets to list
        buckets.add(lowerBucket);
        buckets.add(upperBucket);
    }

    @Override
    public Collection<OutputRecord> returnPublishableRecords() {
        List<OutputRecord> outputRecords = new ArrayList<>();

        for (Bucket bucket : buckets) {
            List<InputRecord> inputRecords = bucket.getInputRecords();
            if (inputRecords.size() >= K_THRESHOLD) {
                double anonValue = bucket.getTotalValue() / inputRecords.size();
                double roundedAnon = Math.round(anonValue*100.0)/100.0;
                for (InputRecord inputRecord : inputRecords) {
                    int outputTime  = time - inputRecord.getTime();
                    OutputRecord outputRecord = new OutputRecord(inputRecord, outputTime, roundedAnon);
                    outputRecords.add(outputRecord);
                }
            }
        }
        return outputRecords;
    }

}
