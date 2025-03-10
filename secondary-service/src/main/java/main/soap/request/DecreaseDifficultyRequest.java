package main.soap.request;

import lombok.*;

import jakarta.xml.bind.annotation.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@XmlRootElement(name = "decreaseDifficultyRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class DecreaseDifficultyRequest {
    private Long labworkId;
    private Long stepsCount;
}