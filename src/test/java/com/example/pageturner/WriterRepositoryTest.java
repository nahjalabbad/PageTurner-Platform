package com.example.pageturner;

import com.example.pageturner.Model.Writer;
import com.example.pageturner.Model.User;
import com.example.pageturner.Repository.AuthRepository;
import com.example.pageturner.Repository.WriterRepository;
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
public class WriterRepositoryTest {

    @Autowired
    AuthRepository authRepository;

    @Autowired
    WriterRepository writerRepository;

    User user;

    @BeforeEach
    void setUp(){
        user = new User(1,"WriterName","writer1","1234","WRITER","0502004422","writer@example.com","Writer Bio",0,0,0,null,null,null);
        user = authRepository.save(user);
    }

    @Test
    public void findWriterByWriterIdTesting(){
        Writer writer = new Writer();
        writer.setUser(user);
        writer = writerRepository.save(writer);

        Writer foundWriter = writerRepository.findWriterByWriterId(writer.getWriterId());

        Assertions.assertThat(foundWriter).isNotNull();
        Assertions.assertThat(foundWriter.getUser().getUserId()).isEqualTo(user.getUserId());
    }
}
