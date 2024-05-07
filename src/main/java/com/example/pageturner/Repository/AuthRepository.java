package com.example.pageturner.Repository;

import com.example.pageturner.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
    User findUserByUserId(Integer userId);
    User findByName(String name);


}
