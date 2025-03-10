package main.soap.request;

import lombok.Getter;
import main.LabWork;

import jakarta.xml.bind.annotation.*;

@Getter
@XmlRootElement(name = "updateLabWorkRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateLabWorkRequest {
    // Getters and Setters
    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "labWork")
    private LabWork labWork;

    public void setId(Long id) {
        this.id = id;
    }

    public void setLabWork(LabWork labWork) {
        this.labWork = labWork;
    }
}
