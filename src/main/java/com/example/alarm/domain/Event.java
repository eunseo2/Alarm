package com.example.alarm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "payload", nullable = false)
	private String payload;

	private Event(String payload) {
		this.payload = payload;
	}

	public static Event from(String payload) {
		return new Event(payload);
	}
}
