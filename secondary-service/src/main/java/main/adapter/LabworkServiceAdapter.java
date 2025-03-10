package main.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConfiguration;
import main.exception.AppException;
import main.exception.AppRuntimeException;
import main.LabWork;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;


@Component
@RequiredArgsConstructor
@Slf4j
public class LabworkServiceAdapter {
    private final AppConfiguration appConfiguration;
    private final HttpClient client = HttpClient.newHttpClient();
    private final XmlMapper<LabWork> labWorkXmlMapper = new XmlMapper<>(LabWork.class);

    public LabWork getById(Long id) throws AppException {
        URI uri = URI.create(LabworkApi.GET_BY_ID.buildUrl(appConfiguration.baseEndpoint, id));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .GET()
                .build();
        System.out.println(request.toString());
        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        LabWork labwork = null;

        try {
            labwork = futureResponse.thenApply(response -> {
                if (response.statusCode() == HttpStatus.OK.value()) {
                    return labWorkXmlMapper.deserialize(response.body());
                }

                if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                    throw new AppRuntimeException(HttpStatus.NOT_FOUND, String.format("Нет лабораторной с таким ID: %s", id));
                }

                throw new AppRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Не обработан статус ответа от основного сервера");
            }).join();
        }
        catch (CompletionException e) {
            catchCompletionException(e);
        }


        return labwork;
    }


    public String deleteById(Long labworkId) throws AppException {
        URI uri = URI.create(LabworkApi.DELETE_BY_ID.buildUrl(appConfiguration.baseEndpoint, labworkId));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .header("Content-Type", "application/xml")
                .DELETE()
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String resp = "";

        try {
            resp = futureResponse.thenApply(response -> {
                if (response.statusCode() == HttpStatus.OK.value()) {
                    return response.body();
                }

                if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                    throw new AppRuntimeException(HttpStatus.NOT_FOUND, String.format("Нет лабораторной с таким ID: %s", labworkId));
                }

                if (response.statusCode() == HttpStatus.BAD_REQUEST.value()) {
                    throw new AppRuntimeException(HttpStatus.BAD_REQUEST, "Плохой запрос");
                }

                throw new AppRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Не обработан статус ответа от основного сервера");
            }).join();
        }
        catch (CompletionException e) {
            catchCompletionException(e);
        }

        return resp;
    }

    public LabWork putById(Long labworkId, LabWork labwork) throws AppException {
        URI uri = URI.create(LabworkApi.PUT_BY_ID.buildUrl(appConfiguration.baseEndpoint, labworkId));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .header("Content-Type", "application/xml")
                .PUT(HttpRequest.BodyPublishers.ofString(labWorkXmlMapper.serialize(labwork)))
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        LabWork updatedLabwork = null;

        try {
            updatedLabwork = futureResponse.thenApply(response -> {
                if (response.statusCode() == HttpStatus.OK.value()) {
                    return labWorkXmlMapper.deserialize(response.body());
                }

                if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                    throw new AppRuntimeException(HttpStatus.NOT_FOUND, String.format("Нет лабораторной с таким ID: %s", labworkId));
                }

                if (response.statusCode() == HttpStatus.BAD_REQUEST.value()) {
                    throw new AppRuntimeException(HttpStatus.BAD_REQUEST, "Плохой запрос");
                }

                throw new AppRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Не обработан статус ответа от основного сервера");
            }).join();
        }
        catch (CompletionException e) {
            catchCompletionException(e);
        }

        return updatedLabwork;
    }

    private void catchCompletionException(CompletionException e) throws AppException {
        try {
            throw e.getCause();
        }
        catch (AppRuntimeException appRuntimeException) {
            throw new AppException(appRuntimeException.getStatus(), appRuntimeException.getMessage());
        }
        catch (ConnectException connectException) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Нет соединения с основным сервисом");
        }
        catch(Throwable impossible) {
            log.error(impossible.getMessage(), impossible);
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Необработанное исключение");
        }
    }
}
