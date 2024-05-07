package com.example.pageturner.Service;
import java.util.Collections;
import java.util.HashSet;
import com.example.pageturner.Api.ApiException;
import com.example.pageturner.Model.*;
import com.example.pageturner.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JoinSessionService {

    private final AuthRepository authRepository;
    private final ReaderRepository readerRepository;
    private final EventRepository eventRepository;
    private final JoinSessionRepository joinSessionRepository;
    private final MeetingRepository meetingRepository;

    //20 Reenad
    //Revised by Nahj and Abdulrahman
    public void joinEvent(Integer eventId, Integer readerId) {
            Event event = eventRepository.findEventByEventId(eventId);
            Reader reader = readerRepository.findReaderByReaderId(readerId);
            User user = authRepository.findUserByUserId(readerId);

            if (event == null) {
                throw new ApiException("Can't join event because the event is not available");
            }

            if (event.getReader().getReaderId().equals(readerId)) {
                throw new ApiException("Can't join, you're the host of this event");
            }

            if (user.getIsJoin() != null && user.getIsJoin().equals("joined")) {
                throw new ApiException("You already joined this event");
            }

            if (event.getStatus().equals("ended")||event.getStatus().equals("not started")){
            throw new ApiException("the meeting has not started or ended you cannot join");
            }

            user.setIsJoin("joined");
            authRepository.save(user);

            event.setNumberOfPeople(event.getNumberOfPeople() == null ? 1 : event.getNumberOfPeople() + 1);
            event.setTotalNoP(event.getTotalNoP()+1);

            JoinSession joinSession = new JoinSession();
            joinSession.setIsJoin("joined");
            joinSession.setMeetingCode(event.getEventCode());
            joinSession.setReaderName(user.getName());
            joinSession.setSessionType("event");
            joinSession.setReaders(new HashSet<>(Collections.singletonList(reader)));

             if (joinSession.getEvents() == null) {
                 joinSession.setEvents(new HashSet<>());
            }

            joinSession.getEvents().add(event);

            eventRepository.save(event);
            joinSessionRepository.save(joinSession);
        }


    //21 Reenad
    //Revised by Nahj and Abdulrahman
    public void joinMeeting(Integer meetingId, Integer readerId) {
        Meeting meeting = meetingRepository.findMeetingByMeetingId(meetingId);
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        User user = authRepository.findUserByUserId(readerId);

        if (meeting == null) {
            throw new ApiException("Can't join meeting because the event is not available");
        }

        if (meeting.getWriter() == null || meeting.getWriter().getWriterId() == null) {
            throw new ApiException("Can't join meeting because the host writer is not available");
        }

        if (meeting.getWriter().getWriterId().equals(readerId)) {
            throw new ApiException("Can't join, you're the host of this meeting");
        }

        if (user.getIsJoin() != null && user.getIsJoin().equals("joined")) {
            throw new ApiException("You already joined this meeting");
        }
        if (meeting.getStatus().equals("ended")||meeting.getStatus().equals("not started")){
            throw new ApiException("the meeting has not started or ended you cannot join");
        }

        user.setIsJoin("joined");
        authRepository.save(user);

        reader.setMeeting(meeting);

        meeting.setNumberOfPeople(meeting.getNumberOfPeople() == null ? 1 : meeting.getNumberOfPeople() + 1);
        meeting.setTotalNoP(meeting.getTotalNoP()+1);

        JoinSession joinSession = new JoinSession();
        joinSession.setIsJoin("joined");
        joinSession.setMeetingCode(meeting.getMeetingCode());
        joinSession.setReaderName(user.getName());
        joinSession.setSessionType("meeting");
        joinSession.setReaders(new HashSet<>(Collections.singletonList(reader)));

        if (joinSession.getMeetings() == null) {
            joinSession.setMeetings(new HashSet<>());
        }

        joinSession.getMeetings().add(meeting);

        readerRepository.save(reader);
        meetingRepository.save(meeting);
        joinSessionRepository.save(joinSession);
    }

    //22 Reenad
    //Revised by Nahj and Abdulrahman
    public void leaveMeeting(Integer meetingId, Integer readerId) {
        Meeting meeting = meetingRepository.findMeetingByMeetingId(meetingId);
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        User user = authRepository.findUserByUserId(readerId);

        if (meeting == null) {
            throw new ApiException("Can't leave meeting because the event ID was not found!");
        } else if (reader == null) {
            throw new ApiException("Can't leave event because the reader ID was not found!");
        }
        if (user.getIsJoin() != null && user.getIsJoin().equals("left")) {
            throw new ApiException("You already left this event");
        }



        user.setIsJoin("left");
        authRepository.save(user);

        meeting.setNumberOfPeople(Math.max(0, meeting.getNumberOfPeople() == null ? 0 : meeting.getNumberOfPeople() - 1));


        meetingRepository.save(meeting);

        JoinSession joinSession = new JoinSession();
        joinSession.setIsJoin("left");
        joinSession.setMeetingCode(meeting.getMeetingCode());
        joinSession.setReaderName(user.getName());
        joinSession.setSessionType("meeting");
        joinSession.setReaders(new HashSet<>(Collections.singletonList(reader)));

        joinSession.getReaders().remove(reader);

        joinSessionRepository.save(joinSession);
    }

    //23 Reenad
    //Revised by Nahj and Abdulrahman
    public void leaveEvent(Integer eventId, Integer readerId) {
        Event event = eventRepository.findEventByEventId(eventId);
        Reader reader = readerRepository.findReaderByReaderId(readerId);
        User user = authRepository.findUserByUserId(readerId);

        if (event == null) {
            throw new ApiException("Can't leave event because the event ID was not found!");
        } else if (reader == null) {
            throw new ApiException("Can't leave event because the reader ID was not found!");
        }
        if (user.getIsJoin() != null && user.getIsJoin().equals("left")) {
            throw new ApiException("You already left this event");
        }

        user.setIsJoin("left");
        authRepository.save(user);

        event.setNumberOfPeople(Math.max(0, event.getNumberOfPeople() == null ? 0 : event.getNumberOfPeople() - 1));


        eventRepository.save(event);

        JoinSession joinSession = new JoinSession();
        joinSession.setIsJoin("left");
        joinSession.setMeetingCode(event.getEventCode());
        joinSession.setReaderName(user.getName());
        joinSession.setSessionType("event");
        joinSession.setReaders(new HashSet<>(Collections.singletonList(reader)));

        joinSession.getReaders().remove(reader);

        joinSessionRepository.save(joinSession);
    }


    //24 Reenad
    //Revised by Nahj and Abdulrahman
    public String getMeetingCode(Integer readerId, Integer meetingId) {
        Meeting meeting = meetingRepository.findMeetingByMeetingId(meetingId);
        if (meeting == null) {
            throw new ApiException("The meeting was not found!");
        }

        Reader reader = readerRepository.findReaderByReaderId(readerId);
        if (reader == null) {
            throw new ApiException("The reader was not found!");
        }

        if (reader.getMeeting().getMeetingId().equals(meetingId)) {
            throw new ApiException("you're the host of this meeting");
        }

        JoinSession joinSession = joinSessionRepository.findByMeetingIdAndReaderId(meetingId, readerId);
        if (joinSession == null || joinSession.getIsJoin().equals("left")) {
            throw new ApiException("You have not joined this meeting!");
        }

        return "Thank you for joining meeting " + meeting.getTitle() + ". Here's the meeting code: " + meeting.getMeetingCode();
    }

    //25 Reenad
    //Revised by Nahj and Abdulrahman
    public String getEventCode(Integer readerId, Integer eventId) {
        Event event = eventRepository.findEventByEventId(eventId);
        if (event == null) {
            throw new ApiException("The event was not found!");
        }

        Reader reader = readerRepository.findReaderByReaderId(readerId);
        if (reader == null) {
            throw new ApiException("The reader was not found!");
        }

        if (event.getReader().getReaderId().equals(readerId)) {
            throw new ApiException(" you're the host of this meeting");
        }
        JoinSession joinSession = joinSessionRepository.findByEventsAndReaders(event, reader);
        if (joinSession == null || joinSession.getIsJoin().equals("left")) {
            throw new ApiException("You have not joined this event!");
        }

        return "Thank you for joining event " + event.getTitle() + ". Here's the event code: " + event.getEventCode();
    }




}
