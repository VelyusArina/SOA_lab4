package main;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.xml.bind.annotation.*;
import java.time.ZonedDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "labwork")
@XmlAccessorType(XmlAccessType.FIELD)
public class LabWork {
    private Integer id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @XmlElement(name = "coordinates")
    private Coordinates coordinates;

    @XmlElement(name = "creationDate")
    private ZonedDateTime creationDate;

    @Positive(message = "Minimal point must be greater than 0")
    private double minimalPoint;

    @Size(max = 8818, message = "Description cannot be longer than 8818 characters")
    private String description;

    private Long tunedInWorks;

    @XmlEnum
    public enum Difficulty {
        NORMAL, HARD, VERY_HARD, IMPOSSIBLE, TERRIBLE
    }

    @XmlElement(name = "difficulty")
    private Difficulty difficulty;

    @XmlElement(name = "discipline")
    private Discipline discipline;
}