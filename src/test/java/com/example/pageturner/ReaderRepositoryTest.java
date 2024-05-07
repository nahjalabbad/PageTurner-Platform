package com.example.pageturner;

import com.example.pageturner.Model.Reader;
import com.example.pageturner.Model.User;
import com.example.pageturner.Repository.AuthRepository;
import com.example.pageturner.Repository.ReaderRepository;
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
public class ReaderRepositoryTest {

    @Autowired
    AuthRepository authRepository;

    @Autowired
    ReaderRepository readerRepository;

    User user;

    @BeforeEach
    void setUp(){
        user = new User(2,"ReaderName","reader2","1234","READER","0502004422","reader@example.com","Reader Bio",0,0,0,null,null,null);
        user = authRepository.save(user);
    }

    @Test
    public void findReaderByReaderIdTesting(){
        Reader reader = new Reader();
        reader.setUser(user);
        reader.setReaderId(2);
        reader = readerRepository.save(reader);

        Reader foundReader = readerRepository.findReaderByReaderId(reader.getReaderId());

        Assertions.assertThat(foundReader).isNotNull();
        Assertions.assertThat(foundReader.getUser().getUserId()).isEqualTo(user.getUserId());
    }
}
