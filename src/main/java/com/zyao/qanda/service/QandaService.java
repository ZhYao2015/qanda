package com.zyao.qanda.service;

import org.springframework.stereotype.Service;

@Service
public class QandaService {
	public String getMessage(int userId) {
		return "Hello Message "+String.valueOf(userId);
	}
}
