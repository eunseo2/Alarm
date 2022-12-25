package com.example.alarm.port.inbound;

import com.example.alarm.adapter.inbound.dto.request.MessageRequestDto;

public interface AlarmMessageService {
	void sendMessage(MessageRequestDto messageRequestDto);
}
