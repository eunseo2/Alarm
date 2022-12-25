package com.example.alarm.port.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.alarm.adapter.inbound.dto.request.MessageRequestDto;
import com.example.alarm.config.RabbitMQConfig;
import com.example.alarm.port.inbound.AlarmMessageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmMessageServiceImpl implements AlarmMessageService {
	private final RabbitTemplate rabbitTemplate;

	@Override
	public void sendMessage(MessageRequestDto messageRequestDto) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, messageRequestDto);
	}
}
