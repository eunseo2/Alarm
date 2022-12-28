package com.example.alarm.port.inbound;

import com.example.alarm.adapter.inbound.dto.request.MessageRequestDto;

public interface SendMessageService {
	void sendMessage(MessageRequestDto messageRequestDto);
}
