package com.privitar.util;

import com.privitar.OutputRecord;
import com.privitar.dto.OutputRecordDTO;

public class OutputRecordUtil {

    private OutputRecordUtil() {
        // no impl
    }

    public static OutputRecordDTO toDTO(OutputRecord outputRecord) {
        OutputRecordDTO outputRecordDTO = new OutputRecordDTO();
        outputRecordDTO.setRawValue(outputRecord.getRawValue());
        outputRecordDTO.setAnonymisedValue(outputRecord.getAnonymisedValue());
        outputRecordDTO.setInputTime(outputRecord.getInputTime());
        outputRecordDTO.setOutputTime(outputRecord.getOutputTime());
        return outputRecordDTO;
    }

}
