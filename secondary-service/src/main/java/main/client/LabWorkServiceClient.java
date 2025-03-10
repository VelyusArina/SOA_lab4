package main.client;

import main.LabWork;
import main.soap.request.DeleteLabWorkRequest;
import main.soap.request.GetLabWorkByIdRequest;
import main.soap.request.UpdateLabWorkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import jakarta.xml.bind.JAXBException;

@Service
public class LabWorkServiceClient {

    private final WebServiceTemplate webServiceTemplate;

    // Конфигурация клиента для внешнего SOAP-сервиса
    @Autowired
    public LabWorkServiceClient(Jaxb2Marshaller marshaller) {
        this.webServiceTemplate = new WebServiceTemplate(marshaller);
        this.webServiceTemplate.setDefaultUri("http://external-service-url/api/"); // URL внешнего сервиса
        this.webServiceTemplate.setMessageSender(new HttpComponentsMessageSender()); // Настройка таймаутов и т.д.
    }

    // 1. getLabWorkById (аналог GET /api/labworks/{id})
    public LabWork getLabWorkById(Long id) {
        try {
            // Создаем объект запроса
            GetLabWorkByIdRequest request = new GetLabWorkByIdRequest();
            request.setId(id);

            // Отправляем SOAP-запрос с соответствующим SOAP Action
            return (LabWork) webServiceTemplate.marshalSendAndReceive(
                    request,
                    new SoapActionCallback("https://example.com/soap/getLabWorkById")
            );
        } catch (RuntimeException e) {
            throw new RuntimeException("Ошибка при выполнении getLabWorkById", e);
        }
    }

    // 2. updateLabWork (аналог PUT /api/labworks/{id})
    public void updateLabWork(Long id, LabWork labWork) {
        try {
            // Создаем объект запроса
            UpdateLabWorkRequest request = new UpdateLabWorkRequest();
            request.setId(id);
            request.setLabWork(labWork);

            // Отправляем SOAP-запрос с соответствующим SOAP Action
            webServiceTemplate.marshalSendAndReceive(
                    request,
                    new SoapActionCallback("http://example.com/soap/updateLabWork")
            );
        } catch (RuntimeException e) {
            throw new RuntimeException("Ошибка при выполнении updateLabWork", e);
        }
    }

    // 3. deleteLabWork (аналог DELETE /api/labworks/{id})
    public void deleteLabWork(Long id) {
        try {
            // Создаем объект запроса
            DeleteLabWorkRequest request = new DeleteLabWorkRequest();
            request.setId(id);

            // Отправляем SOAP-запрос с соответствующим SOAP Action
            webServiceTemplate.marshalSendAndReceive(
                    request,
                    new SoapActionCallback("http://example.com/soap/deleteLabWork")
            );
        } catch (RuntimeException e) {
            throw new RuntimeException("Ошибка при выполнении deleteLabWork", e);
        }
    }
}