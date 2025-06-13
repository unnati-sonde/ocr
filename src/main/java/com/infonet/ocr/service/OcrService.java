package com.infonet.ocr.service;

import com.infonet.ocr.model.Image;
import com.infonet.ocr.model.OcrRequest;
import com.infonet.ocr.model.OcrResponse;
import com.infonet.ocr.model.Response;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.litongjava.ai.djl.paddle.ocr.v4.PaddlePaddleOCRV4;

import java.util.*;

@Service
public class OcrService {
    private static final Logger logger = LoggerFactory.getLogger(OcrService.class);
    private PaddlePaddleOCRV4 paddleOCR = PaddlePaddleOCRV4.INSTANCE;
    private static final String EMPTY_STRING = "";

    @PostConstruct
    public void init() {
        popullateTextCorrectionMap();
    }


    public OcrResponse detectText(OcrRequest ocrRequest) {
        //initialize response list
        OcrResponse ocrResponse = new OcrResponse();
        List<Response> responseList = new ArrayList<>();
        ocrResponse.setResponseList(responseList);

        // Process each OCR request
        for (Image image : ocrRequest.getImages()) {
            ocrResponse.getResponseList().add(processOcrRequest(image));
        }
        return ocrResponse;
    }

    private Response processOcrRequest(Image image) {
        Response response = new Response();
        try {
            //Base64 decode
//            String ext = image.getContent().split(";")[0].split("/")[1];
//            System.out.println("ext="+ext);
//            image.setContent(image.getContent().substring(image.getContent().indexOf(",")+1));
            byte[] decodedImage = Base64.getDecoder().decode(image.getContent());
                // Fetch text
            String result = detectText(decodedImage);
            response.setImageId(image.getImageId());
            response.setText(result);
        } catch (Exception e) {
            logger.error("processOcrRequest() Error processing OCR request: {}" + e.getMessage());
            logger.error("Image id: {}" + image.getImageId());
            logger.error("Image content: {}" + image.getContent());
            response.setImageId(image.getImageId());
            response.setText("Error processing image");
        }
        return response;
    }


    private String detectText(byte[] imageBytes) throws Exception {
        // Implement OCR logic here using Tesseract or any other library
        String result =EMPTY_STRING;
        try {
            result = paddleOCR.ocr(imageBytes);
        } catch (Exception e) {
            logger.error("detectText() Error processing image: {}" + e.getMessage());
            result="Error processing image";
            throw e;
        }
        return improveText(result);
    }

    private Map<String,String> textCorrectionMap = new HashMap<>();
    private String improveText(String text) {
        for (Map.Entry<String, String> entry : textCorrectionMap.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }
        return text;
    }

    private void popullateTextCorrectionMap() {
        textCorrectionMap.put("PollutionUnder", "Pollution Under");
        textCorrectionMap.put("ControlCertificate", "Control Certificate");
        textCorrectionMap.put("Validityupto", "Validity upto");
        textCorrectionMap.put("RegistrationNo", "Registration No");
        textCorrectionMap.put("Mon&YearofManactung", "Month & Year of Manufacturing");
        textCorrectionMap.put("Month&YearofManufacturing", "Month & Year of Manufacturing");
        textCorrectionMap.put("MobileNumber", "Mobile Number");
        textCorrectionMap.put("EmissinNorms", "Emission Norms");
        textCorrectionMap.put("VehiclePhotowithRegistrationplate", "Vehicle Photo with Registration plate");
        textCorrectionMap.put("Emissionlimits", "Emission limits");
        textCorrectionMap.put("IdingEmissions", "Idling Emissions");
        textCorrectionMap.put("PollutionUnderControl", "Pollution Under Control");
        textCorrectionMap.put("ValidMobileNumber", "Valid Mobile Number");
        textCorrectionMap.put("upto2decimal", "upto 2 decimal");
        textCorrectionMap.put("ThisPUCcertific", "This PUC certific");
        textCorrectionMap.put("AuthorisedSignaturewithstampfPUCperatr ", "Authorised Signature with stamp of PUC peratr ");
        textCorrectionMap.put("ofRegisration", "of Regisration");
        textCorrectionMap.put("EmissionNorms", "Emission Norms");
        textCorrectionMap.put("EmissionNoms", "Emission Norms");
        textCorrectionMap.put("IdlingEmissions", "Idling Emissions");
        textCorrectionMap.put("Authorised Signature", "Authorised Signature");
        textCorrectionMap.put("withRegistration", "with Registration");
        textCorrectionMap.put("PUCcertificates", "PUC certificates");
        textCorrectionMap.put("AuthorsSignaturewthstampfUCpator", "Authorised Signature with stamp of PUC operator");
        textCorrectionMap.put("PolutionUnderControlCertificate", "Pollution Under Control Certificate");
        textCorrectionMap.put("Vlid", "Valid");
        textCorrectionMap.put("GovernmentofMaharashtra", "Government of Maharashtra");
        textCorrectionMap.put("PhotowithRegistration", "Photo with Registration");
        textCorrectionMap.put("SmokeDensity", "Smoke Density");
        textCorrectionMap.put("AuhorisedSignatuwthstfCpralr", "Authorised Signature with stamp of PUC operator");
        textCorrectionMap.put("AuthorisedSignatureihstampfPucOperalor", "Authorised Signature with stamp of PUC operator");
        textCorrectionMap.put("withstampofPUCoperator", "with stamp of PUC operator");
        textCorrectionMap.put("PhotowithRegistrationplate", "Photo with Registration plate");
        textCorrectionMap.put("PUCOperator", "PUC Operator");

        textCorrectionMap.put("TODRIVEFOLLOWNG", "TO DRIVE FOLLOWNG");
        textCorrectionMap.put("OFVEHICLES", "OF VEHICLES");
        textCorrectionMap.put("THROUGHOUTINDIA", "THROUGHOUT INDIA");
        textCorrectionMap.put("Signature&IDof", "Signature & ID of");
        textCorrectionMap.put("rthority", "Authority");
        textCorrectionMap.put("DRIVEFOLLOWING", "DRIVE FOLLOWING");
        textCorrectionMap.put("INDA", "INDIA");
        textCorrectionMap.put("NDA DOI", "INDIA");
        textCorrectionMap.put("Signature&IDofAuthority", "Signature & ID of Authority");
        textCorrectionMap.put("OFVEHICLESTHROUGHOUTINDIA", "OF VEHICLES THROUGHOUT INDIA");
        textCorrectionMap.put("STATEMOTORDRIVING", "STATE MOTOR DRIVING");
        textCorrectionMap.put("AUTHORISATIONTODRIVEFOLLOWINGCLASS", "AUTHORISATION TO DRIVE FOLLOWING CLASS");
        textCorrectionMap.put("DRIVEFOLLOWINGCLASS", "DRIVE FOLLOWING CLASS");
        textCorrectionMap.put("FOLLOWINGCLASS", "FOLLOWING CLASS");
        textCorrectionMap.put("THEUNION", "THE UNION");
        textCorrectionMap.put("OFINDIA", "OF INDIA");
        textCorrectionMap.put("STATEMOTORDRIVINGLICENCE", "STATE MOTOR DRIVING LICENCE");
        textCorrectionMap.put("THROUGHOUTNDIA", "THROUGHOUT INDIA");
        textCorrectionMap.put("PollutionUnderControlCertificate", "Pollution Under Control Certificate");
        textCorrectionMap.put("ThisPucertificateissystemgeneratethroughthenationaregistefmotorvehiclsandde", "This PUC certificate is system generated through the national register of motor vehicls and does not require any signature");
        textCorrectionMap.put("ValidMobile", "Valid Mobile");
        textCorrectionMap.put("stampofPUCoperator", "Stamp of PUC operator");
    }
}
