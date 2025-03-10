package main.soap.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


@XmlRootElement(name = "statusResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusResponse {
    private String status;
    private String message;
}