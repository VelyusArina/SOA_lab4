package main.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;



import main.LabWork;
import main.adapter.LabworkServiceAdapter;
import main.client.LabWorkServiceClient;
import main.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;


@Component
@AllArgsConstructor
@Slf4j
public class SecondaryService {
    @Autowired
    private LabworkServiceAdapter labworkServiceAdapter;

    public LabWork decreaseDifficulty(Long labwork_id, Long steps_count) throws AppException {
        System.out.println("dfvsd");
        LabWork labwork = labworkServiceAdapter.getById(labwork_id);
        System.out.println("ds");
        LabWork.Difficulty difficulty = labwork.getDifficulty();
        for(long i = 0; i < steps_count; i++){

            switch(labwork.getDifficulty()){
                case NORMAL -> throw new AppException(HttpStatus.CONFLICT, "Нельзя понизить сложность на такое количество шагов");
                case HARD -> difficulty= LabWork.Difficulty.NORMAL;
                case VERY_HARD -> difficulty= LabWork.Difficulty.HARD;
                case IMPOSSIBLE -> difficulty= LabWork.Difficulty.VERY_HARD;
                case TERRIBLE -> difficulty= LabWork.Difficulty.IMPOSSIBLE;
            }
        }
        labwork.setDifficulty(difficulty);
        labworkServiceAdapter.putById(labwork_id, labwork);
        return labwork;
    }

    public void deleteLabwork(Long discipline_id, @PathVariable Long labwork_id) throws AppException {
        try {
            labworkServiceAdapter.deleteById(labwork_id);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException(HttpStatus.CONFLICT, "Не удалось удалить лабораторную работу:" + e.getMessage());

        }
    }
}
