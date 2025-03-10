package main.service;

import main.client.LabWorkServiceClient;
import main.exception.AppException;
import main.soap.request.DecreaseDifficultyRequest;
import main.soap.request.DeleteLabworkRequestFromDiscipline;
import main.soap.response.StatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

@Endpoint
public class SoapService {

    private static final String NAMESPACE_URI = "http://example.com/soap";
    private static final Logger log = LoggerFactory.getLogger(SoapService.class);
    @Autowired
    private SecondaryService secondaryService;

    @Autowired
    private LabWorkServiceClient labWorkServiceClient;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "decreaseDifficultyRequest")
    @SoapAction("http://example.com/soap/decreaseDifficulty")
    @ResponsePayload
    public StatusResponse decreaseDifficulty(@RequestPayload DecreaseDifficultyRequest request) throws AppException {
        log.info("decreaseDifficulty request");
        try {
            secondaryService.decreaseDifficulty(request.getLabworkId(), request.getStepsCount());
            StatusResponse response = new StatusResponse();
            response.setStatus("OK");
            response.setMessage("OK");
            return response;
        }catch (Exception e) {
            log.info(e.getMessage());
            return new StatusResponse("ERROR", "произошла ошибка: " + e.getMessage());
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteLabworkRequest")
    @SoapAction("http://example.com/soap/deleteLabwork")
    @ResponsePayload
    public StatusResponse deleteLabwork(@RequestPayload DeleteLabworkRequestFromDiscipline request) throws AppException {
        log.info("delete request");
        try {
            secondaryService.deleteLabwork(request.getDisciplineId(), request.getLabworkId());
            StatusResponse response = new StatusResponse();
            response.setStatus("OK");
            response.setMessage("OK");
            return response;
        }catch (Exception e) {
            log.info(e.getMessage());
            return new StatusResponse("ERROR", "произошла ошибка: " + e.getMessage());
        }
    }
}