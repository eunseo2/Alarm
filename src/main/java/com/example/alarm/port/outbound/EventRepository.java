package com.example.alarm.port.outbound;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.alarm.domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
