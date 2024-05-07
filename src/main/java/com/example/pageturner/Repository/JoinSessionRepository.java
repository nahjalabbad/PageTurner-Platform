package com.example.pageturner.Repository;

import com.example.pageturner.Model.Event;
import com.example.pageturner.Model.JoinSession;
import com.example.pageturner.Model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinSessionRepository extends JpaRepository<JoinSession, Integer> {
    JoinSession findByEventsAndReaders(Event event, Reader reader);

    @Query("select meeting from Reader meeting where meeting.meeting.meetingId=?1 and meeting.readerId=?2")
    JoinSession findByMeetingIdAndReaderId(Integer meetingId, Integer readerId);


}
