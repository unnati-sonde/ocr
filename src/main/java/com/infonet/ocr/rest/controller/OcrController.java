package com.infonet.ocr.rest.controller;

import com.infonet.ocr.service.OcrService;
import com.infonet.ocr.model.OcrRequest;
import com.infonet.ocr.model.OcrResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class OcrController {
    private static final Logger logger = LoggerFactory.getLogger(OcrController.class);
    @Autowired
    OcrService ocrService;

    @RequestMapping(value="detectText", method= RequestMethod.POST,consumes="application/json",produces="application/json")
    @ResponseBody
    public ResponseEntity<OcrResponse> processOcr(@RequestBody OcrRequest ocrRequest) {
        logger.info("Received OCR request with {} images", ocrRequest.getImages().size());
         OcrResponse response = ocrService.detectText(ocrRequest);
         return ResponseEntity.ok(response);
    }
}
