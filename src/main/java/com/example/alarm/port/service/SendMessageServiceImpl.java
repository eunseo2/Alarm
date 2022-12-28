package com.example.alarm.port.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.alarm.adapter.inbound.dto.request.MessageRequestDto;
import com.example.alarm.config.RabbitMQConfig;
import com.example.alarm.port.inbound.ConsumeMessageService;
import com.example.alarm.port.inbound.SendMessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendMessageServiceImpl implements SendMessageService {
	private final RabbitTemplate rabbitTemplate;
	private final ConsumeMessageService consumeMessageService;

	@Override
	public void sendMessage(MessageRequestDto messageRequestDto) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, messageRequestDto.getPayload());
		consumeMessageService.consumeMessage();
	}
}
