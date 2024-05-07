package com.example.pageturner.Controller;

import com.example.pageturner.Api.ApiResponse;
import com.example.pageturner.Model.Goals;
import com.example.pageturner.Model.User;
import com.example.pageturner.Service.GoalsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/goals")
public class GoalsController {

    Logger logger = LoggerFactory.getLogger(GoalsController.class);
    private final GoalsService goalsService;

    @GetMapping("/get-all")
    public ResponseEntity getAllGoals(){
        logger.info("inside get all goals");
        return ResponseEntity.ok(goalsService.getAllGoals());
    }

    @GetMapping("/get-my-goals")
    public ResponseEntity getMyGoals(@AuthenticationPrincipal User user){
        logger.info("inside get my goals");
        return ResponseEntity.ok().body(goalsService.getMyGoals(user.getUserId()));
    }
    @PostMapping("/add")
    public ResponseEntity addGoals(@AuthenticationPrincipal User reader, @RequestBody @Valid Goals goals){
        logger.info("inside add goals");
        goalsService.addGoals(reader.getUserId(),goals);
        return ResponseEntity.ok(new ApiResponse("new goal added"));
    }

    @PutMapping("/update/{goalsId}")
    public ResponseEntity updateGoals(@AuthenticationPrincipal User reader, @PathVariable Integer goalsId, @RequestBody @Valid Goals goals){
        logger.info("inside update goals");
        goalsService.updateGoals(reader.getUserId(), goalsId ,goals);
        return ResponseEntity.ok(new ApiResponse("goal has been updated"));
    }

    @DeleteMapping("/delete/{goalsId}")
    public ResponseEntity deleteGoals(@AuthenticationPrincipal User reader, @PathVariable Integer goalsId){
        logger.info("inside delete goals");
        goalsService.deleteGoals(reader.getUserId(), goalsId);
        return ResponseEntity.ok(new ApiResponse("goal has been deleted"));
    }

    //Reenad 10
    @PutMapping("/change-goal-status/{goalId}")
    public ResponseEntity changeGoalStatus(@AuthenticationPrincipal User reader,@PathVariable Integer goalId){
        logger.info("inside change goals status");
        goalsService.changeGoalStatus(reader.getUserId(),goalId);
        return ResponseEntity.ok().body(new ApiResponse("status Updated"));
    }
}
