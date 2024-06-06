package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Event;

@Repository("eventRepository")
public interface EventRepository extends JpaRepository<Event, Serializable> {
	public abstract Event findById(int id);
	public abstract List<Event> findByType(String type);
}
