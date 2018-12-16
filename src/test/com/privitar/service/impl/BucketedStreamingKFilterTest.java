package com.privitar.service.impl;

import com.privitar.InputRecord;
import com.privitar.OutputRecord;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class BucketedStreamingKFilterTest {

    private BucketedStreamingKFilter bucketedStreamingKFilter;

    @Before
    public void setUp() {
        bucketedStreamingKFilter = new BucketedStreamingKFilter();
    }

    @Test
    public void testBucketedStreamingFilter() {
        InputRecord inputRecord1 = new InputRecord(1, 54.1);
        InputRecord inputRecord2 = new InputRecord(2, 64.2);
        InputRecord inputRecord3 = new InputRecord(3, 19.8);
        InputRecord inputRecord4 = new InputRecord(4, 37.6);

        bucketedStreamingKFilter.processNewRecord(inputRecord1);
        bucketedStreamingKFilter.processNewRecord(inputRecord2);
        bucketedStreamingKFilter.processNewRecord(inputRecord3);
        bucketedStreamingKFilter.processNewRecord(inputRecord4);

        Collection<OutputRecord> outputRecords = bucketedStreamingKFilter.returnPublishableRecords();

        System.out.println(outputRecords.size());

        InputRecord inputRecord5 = new InputRecord(5, 5.0);

        bucketedStreamingKFilter.processNewRecord(inputRecord5);

        outputRecords = bucketedStreamingKFilter.returnPublishableRecords();
        System.out.println(outputRecords.size());
        for (OutputRecord outputRecord : outputRecords) {
            System.out.print("ANON: " + outputRecord.getAnonymisedValue());
            System.out.print(" INPUT: " + outputRecord.getInputTime());
            System.out.print(" OUTPUT: " + outputRecord.getOutputTime());
            System.out.print(" RAW: " + outputRecord.getRawValue());
            System.out.println();
        }

        InputRecord inputRecord6 = new InputRecord(6,97.7);
        InputRecord inputRecord7 = new InputRecord(7,502.2);
        InputRecord inputRecord8 = new InputRecord(8,13.8);
        InputRecord inputRecord9 = new InputRecord(9,65.1);
        InputRecord inputRecord10 = new InputRecord(10,1.9);
        InputRecord inputRecord11 = new InputRecord(11,76.1);

        bucketedStreamingKFilter.processNewRecord(inputRecord6);
        bucketedStreamingKFilter.processNewRecord(inputRecord7);
        bucketedStreamingKFilter.processNewRecord(inputRecord8);
        bucketedStreamingKFilter.processNewRecord(inputRecord9);
        bucketedStreamingKFilter.processNewRecord(inputRecord10);
        bucketedStreamingKFilter.processNewRecord(inputRecord11);

        outputRecords = bucketedStreamingKFilter.returnPublishableRecords();
        System.out.println(outputRecords.size());
        for (OutputRecord outputRecord : outputRecords) {
            System.out.print("ANON: " + outputRecord.getAnonymisedValue());
            System.out.print(" INPUT: " + outputRecord.getInputTime());
            System.out.print(" OUTPUT: " + outputRecord.getOutputTime());
            System.out.print(" RAW: " + outputRecord.getRawValue());
            System.out.println();
        }
    }

}
