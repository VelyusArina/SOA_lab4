package main.soap.request;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "getLabWorkByIdRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetLabWorkByIdRequest {
    @XmlElement(name = "id")
    private Long id;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}