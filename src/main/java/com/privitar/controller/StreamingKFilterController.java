package com.privitar.controller;

import com.privitar.InputRecord;
import com.privitar.OutputRecord;
import com.privitar.dto.OutputRecordDTO;
import com.privitar.util.InputRecordUtil;
import com.privitar.dto.InputRecordDTO;
import com.privitar.service.StreamingKFilter;
import com.privitar.util.OutputRecordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/records")
public class StreamingKFilterController {

    private final StreamingKFilter streamingKFilter;

    @Autowired
    public StreamingKFilterController(StreamingKFilter streamingKFilter) {
        this.streamingKFilter = streamingKFilter;
    }

    @PostMapping
    public void publishRecord(@RequestBody InputRecordDTO inputRecordDTO) {
        InputRecord inputRecord = InputRecordUtil.fromDTO(inputRecordDTO);
        streamingKFilter.processNewRecord(inputRecord);
    }

    @GetMapping
    public List<OutputRecordDTO> getRecords() {
        Collection<OutputRecord> outputRecords = streamingKFilter.returnPublishableRecords();

        List<OutputRecordDTO> outputRecordDTOS = new ArrayList<>();
        for (OutputRecord outputRecord : outputRecords) {
            OutputRecordDTO outputRecordDTO = OutputRecordUtil.toDTO(outputRecord);
            outputRecordDTOS.add(outputRecordDTO);
        }
        return outputRecordDTOS;
    }

}
