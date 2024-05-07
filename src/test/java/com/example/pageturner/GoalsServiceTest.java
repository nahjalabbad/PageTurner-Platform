package com.example.pageturner;

import com.example.pageturner.Model.Goals;
import com.example.pageturner.Model.Reader;
import com.example.pageturner.Model.User;
import com.example.pageturner.Repository.AuthRepository;
import com.example.pageturner.Repository.GoalsRepository;
import com.example.pageturner.Repository.ReaderRepository;
import com.example.pageturner.Service.GoalsService;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GoalsServiceTest {

    @Mock
    private GoalsRepository goalsRepository;

    @Mock
    private ReaderRepository readerRepository;

    @Mock
    private AuthRepository authRepository;

    @InjectMocks
    private GoalsService goalsService;

    User user;

    Reader reader;

    Goals goals1,goals2,goals3;

    List<Goals> goals;



    @BeforeEach
    public void setUp() {
        user = new User(1,"Reenad","reen0","1234","READER","0502004422","r@gmail.com","life is good",0,0,0,null,null,null);
        reader=new Reader(null,null,null,null,null,user,null,null,null,null,null,null,null);
        goals1 = new Goals(1,"goals1",null,reader);
        goals2 = new Goals(2,"goals2",null,reader);
        goals3 = new Goals(3,"goals3",null,reader);

        goals = new ArrayList<>();
        goals.add(goals1);
        goals.add(goals2);
        goals.add(goals3);        }

    @Test
    public void testGetAllGoals() {
        when(goalsRepository.findAll()).thenReturn(goals);

        List<Goals> allGoals = goalsService.getAllGoals();

        assertEquals(3,allGoals.size());
        verify(goalsRepository,times(1)).findAll();;
        verify(goalsRepository,times(1)).findAll();
    }
    //
    @Test
    public void testGetMyGoals() {
        when(authRepository.findUserByUserId(user.getUserId())).thenReturn(user);
        when(goalsRepository.findAllByReader(user)).thenReturn(goals);

        List<Goals> goalsList=goalsService.getMyGoals(user.getUserId());
        assertEquals(goalsList,goals);
        verify(authRepository,times(1)).findUserByUserId(user.getUserId());
        verify(goalsRepository,times(1)).findAllByReader(user);
    }
    //
    @Test
    public void testAddGoals() {
        when(readerRepository.findReaderByReaderId(reader.getReaderId())).thenReturn(reader);

        goalsService.addGoals(reader.getReaderId(),goals3);
        verify(readerRepository,times(1)).findReaderByReaderId(reader.getReaderId());
        verify(goalsRepository,times(1)).save(goals3);

    }

    @Test
    public void testDeleteGoals() {

        Integer goalsId = 1;
        Goals mockGoals = new Goals();
        when(goalsRepository.findGoalsByGoalsID(goalsId)).thenReturn(mockGoals);

        goalsService.deleteGoals(user.getUserId(),goalsId);


        verify(goalsRepository).delete(mockGoals);
    }
    @Test
    public void testChangeGoalStatus() {

        Integer goalId = 1;
        Goals mockGoals = new Goals();
        mockGoals.setStatus("not finished");
        when(goalsRepository.findGoalsByGoalsID(goalId)).thenReturn(mockGoals);


        goalsService.changeGoalStatus(user.getUserId(),goalId);


        verify(goalsRepository).save(mockGoals);
        assertEquals("finished", mockGoals.getStatus());
    }
}
