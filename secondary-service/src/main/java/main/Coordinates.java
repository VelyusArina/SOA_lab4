package main;

import jakarta.validation.constraints.NotNull;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {
    @XmlElement(name="x")
    private double x;

    @XmlElement(name = "y")
    @NotNull(message = "Y cannot be null")
    private Long y;
}