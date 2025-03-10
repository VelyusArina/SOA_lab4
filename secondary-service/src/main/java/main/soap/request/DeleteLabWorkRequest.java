package main.soap.request;

import lombok.Getter;
import lombok.Setter;

import jakarta.xml.bind.annotation.*;

@Setter
@Getter
@XmlRootElement(name = "deleteLabWorkRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteLabWorkRequest {
    // Getters and Setters
    @XmlElement(name = "id")
    private Long id;

}