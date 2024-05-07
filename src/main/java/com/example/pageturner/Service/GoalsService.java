package com.example.pageturner.Service;


import com.example.pageturner.Api.ApiException;
import com.example.pageturner.Model.Goals;
import com.example.pageturner.Model.Reader;
import com.example.pageturner.Model.User;
import com.example.pageturner.Repository.AuthRepository;
import com.example.pageturner.Repository.GoalsRepository;
import com.example.pageturner.Repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalsService {

    private final GoalsRepository goalsRepository;
    private final ReaderRepository readerRepository;
    private final AuthRepository authRepository;


    public List<Goals> getAllGoals(){
        return goalsRepository.findAll();
    }
    public List<Goals> getMyGoals(Integer readerId){
        User reader1 = authRepository.findUserByUserId(readerId);
        return goalsRepository.findAllByReader(reader1);
    }

    public void addGoals(Integer readerId, Goals goals){
        Reader reader=readerRepository.findReaderByReaderId(readerId);
        goals.setStatus("not finished");
        goals.setReader(reader);
        goalsRepository.save(goals);
    }

    public void updateGoals( Integer readerId,Integer goalsId ,Goals goals){
        Goals goals1= goalsRepository.findGoalsByGoalsID(goalsId);
                if (!goals1.getReader().getReaderId().equals(readerId)) {
            throw new ApiException("goal not found");
        }
        goals1.setTitle(goals.getTitle());

        goalsRepository.save(goals1);
    }

    public void deleteGoals(Integer readerId,Integer goalsId){
        Goals goals1= goalsRepository.findGoalsByGoalsID(goalsId);
        if (goals1 == null){
            throw new ApiException("goal not found!");
        }
        goalsRepository.delete(goals1);
    }

    //19 Reenad
    public void changeGoalStatus(Integer readerId, Integer goalId) {
        Goals goals1= goalsRepository.findGoalsByGoalsID(goalId);
        if (goals1 == null) {
            throw new ApiException("goal not found");
        }

        if (goals1.getStatus().equalsIgnoreCase("not finished")) {
            goals1.setStatus("finished");
            goalsRepository.save(goals1);
        } else if (goals1.getStatus().equalsIgnoreCase("finished")) {
            goals1.setStatus("not finished");
            goalsRepository.save(goals1);

        }
    }
}
