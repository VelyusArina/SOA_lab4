package main;

import jakarta.validation.constraints.NotBlank;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement(name = "discipline")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Discipline {
    private Integer id;

    @NotBlank
    private String name;

    @XmlElement(name = "labsCount")
    private Integer labsCount;
}