package com.example.alarm.adapter.inbound.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.alarm.adapter.inbound.dto.request.MessageRequestDto;
import com.example.alarm.port.inbound.AlarmMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
public class AlarmMessageController {
	private final AlarmMessageService alarmMessageService;

	@PostMapping("/message/publish")
	public void publish(
		@RequestBody MessageRequestDto messageRequestDto
	) {
		alarmMessageService.sendMessage(messageRequestDto);
	}
}
