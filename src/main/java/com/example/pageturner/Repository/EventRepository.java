package com.example.pageturner.Repository;

import com.example.pageturner.Model.Event;
import com.example.pageturner.Model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
    Event findEventByEventId(Integer id);

    @Query("SELECT event FROM Event event WHERE event.reader.readerId = ?1 AND event.endTime < ?2")
    List<Event> getEventsByReaderIdAndEndTimeBefore(Integer readerId, LocalDateTime endTime);
    List<Event> getEventsByStartTimeAfterAndStatus(LocalDateTime upcoming,String status);

    Event getEventByTitle(String title);

    Event getEventByBookName(String bookName);

    List<Event> getAllByStatus(String status);


    List<Event> getAllByReader(Reader reader);



}
