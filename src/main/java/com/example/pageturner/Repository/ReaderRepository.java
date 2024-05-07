package com.example.pageturner.Repository;

import com.example.pageturner.Model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader,Integer> {
    Reader findReaderByReaderId(Integer id);

}
