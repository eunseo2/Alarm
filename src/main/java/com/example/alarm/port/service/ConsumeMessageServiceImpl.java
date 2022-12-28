package com.example.alarm.port.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.alarm.config.RabbitMQConfig;
import com.example.alarm.domain.Event;
import com.example.alarm.port.inbound.ConsumeMessageService;
import com.example.alarm.port.outbound.EventRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumeMessageServiceImpl implements ConsumeMessageService {
	private static final boolean AUTO_ACK = false;
	private final EventRepository eventRepository;

	@Async
	@Override
	public void consumeMessage() {
		ConnectionFactory factory = new ConnectionFactory();
		try {
			Thread.sleep(3000);
			Channel channel = factory.newConnection().createChannel();

			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");

				log.info(" [x] Received '" + message + "'");

				eventRepository.save(Event.from(message));

				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			};

			channel.basicConsume(RabbitMQConfig.QUEUE_NAME, AUTO_ACK, deliverCallback, consumerTag -> {
			});
		} catch (IOException | TimeoutException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
