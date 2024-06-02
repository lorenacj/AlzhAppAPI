package com.example.demo.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.entity.Patient;
import com.example.demo.model.EventModel;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.EventService;

@Service("eventService")
public class EventServiceImpl implements EventService{

	@Autowired
	@Qualifier("eventRepository")
	private EventRepository eventRepository;

    @Override
    public Event createEvent(EventModel eventModel, Patient patient) {
        Event event = transformEvent(eventModel);
        List<Event> listEvents=patient.getEvents();
        listEvents.add(event);
        patient.setEvents(listEvents);
        
        event.setPatient(patient);
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(EventModel eventModel) {
        Event event = transformEvent(eventModel);
        return eventRepository.save(event);
    }

    @Override
    public int deleteEvent(int eventId) {
        eventRepository.deleteById(eventId);
        return eventId;
    }

    @Override
    public List<EventModel> listAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::transformEvent)
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> listEventByPatient(Patient patient) {
        return eventRepository.findAll()
                .stream()
                .filter(event -> event.getPatient() != null && event.getPatient().equals(patient))
                .collect(Collectors.toList());
    }
	@Override
	public List<Event> listEventFinal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> listEventNOFinal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event transformEvent(EventModel eventModel) {
	    if (eventModel == null) {
	        return null;
	    }

	    ModelMapper modelMapper = new ModelMapper();
	    return modelMapper.map(eventModel, Event.class);
	}

	@Override
	public EventModel transformEvent(Event event) {
	    if (event == null) {
	        return null;
	    }
	    
	    ModelMapper modelMapper = new ModelMapper();
	    return modelMapper.map(event, EventModel.class);
	}

}
