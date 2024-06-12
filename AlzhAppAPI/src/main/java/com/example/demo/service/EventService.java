package com.example.demo.service;

import java.util.List;

import com.example.demo.model.EventModel;
import com.example.demo.entity.Carer;
import com.example.demo.entity.Event;
import com.example.demo.entity.Patient;

public interface EventService {

	public abstract Event createEvent(EventModel event, Patient patient);
	
	public abstract Event updateEvent(EventModel event);
	
	public abstract int deleteEvent(int idevent);
	
	public abstract List<EventModel> listAllEvents(); 

	public abstract List<Event> listEventByPatient(Patient patient);

	public abstract List<Event> listEventFinal();
	
	public abstract List<Event> listEventNOFinal();

	public abstract Event transformEvent(EventModel eventModel);
	
	public abstract EventModel transformEvent(Event event);

	public abstract List<Event> getEventsByType(String type, Carer carer);

	public abstract List<Event> getEventsByCarer(Carer carer);

	public abstract void remove(List<Event> events);

}
