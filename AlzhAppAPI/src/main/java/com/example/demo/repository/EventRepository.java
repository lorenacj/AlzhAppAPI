package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Event;

@Repository("eventRepository")
public interface EventRepository extends JpaRepository<Event, Serializable> {
	public abstract Event findById(int id);

}
