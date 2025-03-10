package main.soap.request;

import lombok.*;

import jakarta.xml.bind.annotation.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@XmlRootElement(name = "deleteLabworkRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteLabworkRequestFromDiscipline {
    private Long disciplineId;
    private Long labworkId;

}