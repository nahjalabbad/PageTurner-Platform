package com.example.pageturner.Repository;

import com.example.pageturner.Model.Goals;
import com.example.pageturner.Model.User;
import lombok.Locked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalsRepository extends JpaRepository<Goals,Integer> {
    Goals findGoalsByTitle(String title);
    Goals findGoalsByGoalsID(Integer genreId);

    List<Goals> findAllByReader(User reader);
}
