package com.example.alarm.adapter.inbound.dto.request;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestDto {
	private Map<String, Object> payload;
}
