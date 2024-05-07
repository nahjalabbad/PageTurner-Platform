package com.example.pageturner.Service;

import com.example.pageturner.Api.ApiException;
import com.example.pageturner.Model.Event;
import com.example.pageturner.Model.Reader;
import com.example.pageturner.Model.User;
import com.example.pageturner.Repository.AuthRepository;
import com.example.pageturner.Repository.EventRepository;
import com.example.pageturner.Repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ReaderRepository readerRepository;
    private final AuthRepository authRepository;

    public List<Event> getAllEvent(){
        return eventRepository.findAll();
    }

    public void addEvent(Integer readerId,Event event){
        User r=authRepository.findUserByUserId(readerId);
        Reader r1=readerRepository.findReaderByReaderId(readerId);
        if(r==null){
            throw new ApiException("can't assign");
        }
        event.setReader(r1);
        event.setStatus("not started");
        event.setNumberOfPeople(0);
        event.setTotalNoP(0);
        event.setHost(r.getName());

        eventRepository.save(event);
    }

    public void updateEvent(Integer readerId,Integer eventId,Event newEvent){
        Event oldEvent=eventRepository.findEventByEventId(eventId);
        User r=authRepository.findUserByUserId(readerId);

        oldEvent.setHost(r.getName());
        oldEvent.setTitle(newEvent.getTitle());
        oldEvent.setStartTime(newEvent.getStartTime());
        oldEvent.setDescription(newEvent.getDescription());
        oldEvent.setBookName(newEvent.getBookName());
        oldEvent.setAuthor(newEvent.getAuthor());
        oldEvent.setChapter(newEvent.getChapter());
        oldEvent.setGroupType(newEvent.getGroupType());
        oldEvent.setEventCode(newEvent.getEventCode());

        eventRepository.save(oldEvent);
    }


    public void deleteEvent(Integer readerId, Integer eventId) {
        Event e = eventRepository.findEventByEventId(eventId);
        if (e == null) {
            throw new ApiException("Event not found");
        }

        if (!e.getReader().getReaderId().equals(readerId)) {
            throw new ApiException("Unauthorized access: Event does not belong to the specified reader");
        }

        eventRepository.delete(e);
    }


    //EXTRA

    //9 Nahj
    public void startEvent(Integer hostId, Integer eventId){
        Event event=eventRepository.findEventByEventId(eventId);
        if (!event.getReader().getReaderId().equals(hostId)){
            throw new ApiException("there is no event available");
        }
        if (!event.getStatus().equalsIgnoreCase("not started")){
            throw new ApiException("this event is either active or already ended");
        }
        event.setStatus("active");
        eventRepository.save(event);
    }

    //10 Nahj
    public void endEvent(Integer hostId, Integer eventId){
        Event event=eventRepository.findEventByEventId(eventId);
        if (!event.getReader().getReaderId().equals(hostId)){
            throw new ApiException("there is no event available");
        }
        if (!event.getStatus().equalsIgnoreCase("active")){
            throw new ApiException("this event is either not started or already ended");
        }
        event.setStatus("ended");
        event.setEndTime(LocalDateTime.now());
        eventRepository.save(event);
    }

    //11 Nahj
    public List<Event> getPrevious(Integer readerId){
        LocalDateTime currentTime = LocalDateTime.now();
        List<Event> previousEvents = eventRepository.getEventsByReaderIdAndEndTimeBefore(readerId, currentTime.minusSeconds(1));
        if (previousEvents.isEmpty()){
            throw new ApiException("You have no previous events.");
        }
        return previousEvents;
    }

    //12 Nahj
    public List<Event> getUpComing(Integer userId){
        String status="not started";
        List<Event> getUpComing=eventRepository.getEventsByStartTimeAfterAndStatus(LocalDateTime.now().plusMinutes(10),status);
        if (getUpComing.isEmpty()){
            throw new ApiException("sorry no events have been scheduled");
        }
        if (!status.equalsIgnoreCase("not started")){
            throw new ApiException("sorry no events have been scheduled");
        }
        return getUpComing;
    }

    //13 Nahj
    public Event getCurrentEvent(Integer userId,Integer eventId){
        Event event=eventRepository.findEventByEventId(eventId);
        if (event==null){
            throw new ApiException("Event not found");
        }
        return event;
    }

    //14 Nahj
    public Event searchEventbyTitle(Integer userId,String title){
        Event event=eventRepository.getEventByTitle(title);
        if (event==null){
            throw new ApiException("Event not found");
        }
        return event;
    }

    //15 Nahj
    public Event searchEventbyBookName(Integer userId,String bookName){
        Event event=eventRepository.getEventByBookName(bookName);
        if (event==null){
            throw new ApiException("Event not found");
        }
        return event;
    }

    //16 Nahj
    public List<Event> getAllReaderEvent(Integer readerId){
        Reader reader=readerRepository.findReaderByReaderId(readerId);
        List<Event> eventsByReader=eventRepository.getAllByReader(reader);
        if (eventsByReader.isEmpty()){
            throw new ApiException("you have no events, try to schedule an event");
        }
        return eventsByReader;
    }

    //17 Nahj
    public List<Event> getAllCurrentEvents(Integer readerId){
        Reader reader=readerRepository.findReaderByReaderId(readerId);
        List<Event> currentEvents=eventRepository.getAllByStatus("active");
        if (currentEvents.isEmpty()){
            throw new ApiException("sorry no events have been scheduled");
        }
        return currentEvents;
    }


}
