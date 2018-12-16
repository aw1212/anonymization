package com.privitar.util;

import com.privitar.InputRecord;
import com.privitar.dto.InputRecordDTO;

public class InputRecordUtil {

    private InputRecordUtil() {
        // no impl
    }

    public static InputRecord fromDTO(InputRecordDTO inputRecordDTO) {
        return new InputRecord(inputRecordDTO.getTime(), inputRecordDTO.getRawValue());
    }

}
