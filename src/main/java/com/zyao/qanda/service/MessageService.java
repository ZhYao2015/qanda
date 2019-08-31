package com.zyao.qanda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyao.qanda.dao.MessageDao;
import com.zyao.qanda.model.Message;

@Service
public class MessageService {
	
	@Autowired
	MessageDao messageDao;
	
	@Autowired
	SensitiveService sensitiveService;
	
	public int addMessage(Message message) {
		return messageDao.addMessage(message)>0?message.getId():0;
	}
	
	public List<Message> getConverstaionDetail(String conversationId){
		return messageDao.getConversationDetail(converstationId);
	}
}
