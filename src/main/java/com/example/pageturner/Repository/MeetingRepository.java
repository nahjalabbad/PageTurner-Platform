package com.example.pageturner.Repository;

import com.example.pageturner.Model.Meeting;
import com.example.pageturner.Model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Integer> {
    Meeting findMeetingByMeetingId(Integer meetingId);

    List<Meeting> findAllByStatus(String status);

    Meeting findMeetingByTitle(String title);
    Meeting findMeetingByBookName(String bookName);

    List<Meeting> findAllByWriter(Writer writerId);

    @Query("SELECT CONCAT('Title: ', m.title, ', Host: ', m.host, ', Start Time: ', m.startTime, ', End Time: ', m.endTime) FROM Meeting m JOIN m.readers r WHERE r.readerId = ?1 AND m.status = ?2")
    List<String> findAllMeetingDetailsByReaderIdAndStatus(Integer readerId, String status);

    List<Meeting> findAllByWriterAndStatus(Writer reader, String status);

}
