package com.example.pageturner.Service;


import com.example.pageturner.Api.ApiException;

import com.example.pageturner.Model.*;

import com.example.pageturner.Repository.AuthRepository;
import com.example.pageturner.Repository.MeetingRepository;
import com.example.pageturner.Repository.ReaderRepository;
import com.example.pageturner.Repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final WriterRepository writerRepository;
    private final AuthRepository authRepository;
    private final ReaderRepository readerRepository;

    public List<Meeting> getAllMeeting(){
        return meetingRepository.findAll();
    }

    public void addMeeting(Integer writerId, Meeting meeting){
        User w=authRepository.findUserByUserId(writerId);
        Writer w1=writerRepository.findWriterByWriterId(writerId);
        if(w==null){
            throw new ApiException("can't assign");
        }
        meeting.setWriter(w1);
        meeting.setNumberOfPeople(0);
        meeting.setTotalNoP(0);
        meeting.setStatus("not started");
        meeting.setHost(w.getName());

        meetingRepository.save(meeting);
    }

    public void updateMeeting(Integer writerId, Integer meetingId, Meeting meeting){
        Meeting m=meetingRepository.findMeetingByMeetingId(meetingId);
        if(!m.getWriter().getWriterId().equals(writerId)){
            throw new ApiException("not found");
        }

        m.setHost(meeting.getHost());
        m.setTitle(meeting.getTitle());
        m.setStartTime(meeting.getStartTime());
        m.setDescription(meeting.getDescription());
        m.setBookName(meeting.getBookName());
        m.setPrice(meeting.getPrice());

        meetingRepository.save(m);
    }

    public void deleteMeeting(Integer writerId, Integer meetingId) {
        Meeting meeting = meetingRepository.findMeetingByMeetingId(meetingId);
        if (meeting == null) {
            throw new ApiException("Meeting not found");
        }

        if (!meeting.getWriter().getWriterId().equals(writerId)) {
            throw new ApiException("Unauthorized access: Event does not belong to the specified writer");
        }

        meetingRepository.delete(meeting);
    }

    //26 Abdulrahman
    public void startMeeting(Integer writerId, Integer meetingId) {
        Writer writer = writerRepository.findWriterByWriterId(writerId);
        Meeting meeting = meetingRepository.findMeetingByMeetingId(meetingId);
        if (writer == null) {
            throw new ApiException("writer with id " + writerId + " not found!");
        } else if (meeting == null) {
            throw new ApiException("meeting with id " + meetingId + " not found!");
        }
        meeting.setStatus("active");
        meetingRepository.save(meeting);
    }

    //27 Abdulrahman
    public void endMeeting(Integer writerId, Integer meetingId){
        Writer writer = writerRepository.findWriterByWriterId(writerId);
        Meeting meeting = meetingRepository.findMeetingByMeetingId(meetingId);
        if (writer == null) {
            throw new ApiException("writer with id " + writerId + " not found!");
        }
        if (meeting == null) {
            throw new ApiException("meeting with id " + meetingId + " not found!");
        }
        meeting.setEndTime(LocalDateTime.now());
        meeting.setStatus("ended");
        meetingRepository.save(meeting);

    }

    //28 Abdulrahman
    //Revised by Nahj
    public List<String> getAllPreviousMeetingsForReader(Integer readerId) {
        Reader reader = readerRepository.findReaderByReaderId(readerId);

        if (reader == null) {
            throw new ApiException("Reader with ID " + readerId + " not found!");
        }

        List<String> meetingDetailsByReaderAndStatus = meetingRepository.findAllMeetingDetailsByReaderIdAndStatus(readerId, "ended");

        if (meetingDetailsByReaderAndStatus.isEmpty()) {
            throw new ApiException("No previous meetings found for reader with ID: " + readerId);
        }

        return meetingDetailsByReaderAndStatus;
    }



    //29 Abdulrahman
    public List<Meeting> getAllPreviousMeetingsForWriter(Integer writerId) {
        Writer writer = writerRepository.findWriterByWriterId(writerId);
        if (writer == null){
            throw new ApiException("Writer with id " + writerId + " not found!");
        }
        List<Meeting> meetingsByWriter = meetingRepository.findAllByWriterAndStatus(writer, "ended");
        if (meetingsByWriter.isEmpty()){
            throw new ApiException("You have no previous meetings");
        }
        return meetingsByWriter;
    }

    //30 Abdulrahman
    public List<Meeting> getAllUpcomingMeetings() {
        return meetingRepository.findAllByStatus("not started");
    }

    //31 Abdulrahman
    public Meeting searchForMeetingByMeetingId(Integer meetingId) {
        return meetingRepository.findMeetingByMeetingId(meetingId);
    }

    //32 Abdulrahman
    public Meeting searchForMeetingByMeetingTitle(String title) {
        return meetingRepository.findMeetingByTitle(title);
    }

    //33 Abdulrahman
    public Meeting searchForMeetingByBookName(String BookName) {
        return meetingRepository.findMeetingByBookName(BookName);
    }

    //34 Abdulrahman
    public List<Meeting> getCurrentMeetings() {
        return meetingRepository.findAllByStatus("active");
    }

    //35 Abdulrahman
    public Meeting getCurrentMeeting(Integer meetingId) {
        Meeting meeting = meetingRepository.findMeetingByMeetingId(meetingId);
        if (meeting != null && meeting.getStatus().equals("active")) {
            return meeting;
        } else {
            throw new ApiException("This meeting isn't active or doesn't exist!");
        }
    }

    //36 Abdulrahman
    public List<Meeting> getAllMyMeetings(Integer writerId) {
        Writer writer = writerRepository.findWriterByWriterId(writerId);
        List<Meeting> meetingsByWriter = meetingRepository.findAllByWriter(writer);
        if (meetingsByWriter.isEmpty()){
            throw new ApiException("you have no meetings, try to schedule an meeting");
        }
        return meetingsByWriter;
    }



}
