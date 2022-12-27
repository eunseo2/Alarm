package com.example.alarm.port.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.alarm.adapter.inbound.dto.request.MessageRequestDto;
import com.example.alarm.config.RabbitMQConfig;
import com.example.alarm.port.inbound.AlarmMessageService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmMessageServiceImpl implements AlarmMessageService {
	private static final boolean AUTO_ACK = false;
	private final RabbitTemplate rabbitTemplate;

	@Override
	public void sendMessage(MessageRequestDto messageRequestDto) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, messageRequestDto.getPayload());
		this.consume();
	}

	private void consume() {
		ConnectionFactory factory = new ConnectionFactory();
		try {
			Channel channel = factory.newConnection().createChannel();

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");

				log.info(" [x] Received '" + message + "'");
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			};

			channel.basicConsume(RabbitMQConfig.QUEUE_NAME, AUTO_ACK, deliverCallback, consumerTag -> {
			});
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
