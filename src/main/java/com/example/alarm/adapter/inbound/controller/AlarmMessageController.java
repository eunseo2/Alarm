package com.example.alarm.adapter.inbound.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.alarm.adapter.inbound.dto.request.MessageRequestDto;
import com.example.alarm.port.inbound.SendMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
public class AlarmMessageController {
	private final SendMessageService sendMessageService;

	@PostMapping("/message/publish")
	public ResponseEntity<Void> publish(
		@RequestBody MessageRequestDto messageRequestDto
	) {
		sendMessageService.sendMessage(messageRequestDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
