package com.example.pageturner;

import com.example.pageturner.Model.User;
import com.example.pageturner.Repository.AuthRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthRepositoryTest {

    @Autowired
    AuthRepository authRepository;

    User user;

    @BeforeEach
    void setUp(){
        user = new User(1,"Reenad","reen0","1234","READER","0502004422","r@gmail.com","life is good",0,0,0,null,null,null);
    }

    @Test
    public void findUserByUsernameTesting(){
        user = authRepository.save(user);

        User foundUser = authRepository.findUserByUsername(user.getUsername());

        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    public void findUserByUserIdTesting(){
        user = authRepository.save(user);

        User foundUser = authRepository.findUserByUserId(user.getUserId());

        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void findByNameTesting(){
        user = authRepository.save(user);

        User foundUser = authRepository.findByName(user.getName());

        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUserId()).isEqualTo(user.getUserId());
    }
}
