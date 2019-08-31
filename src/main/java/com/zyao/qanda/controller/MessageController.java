package com.zyao.qanda.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyao.qanda.model.HostHolder;
import com.zyao.qanda.model.Message;
import com.zyao.qanda.model.User;
import com.zyao.qanda.service.MessageService;
import com.zyao.qanda.service.UserService;
import com.zyao.qanda.utils.QandaUtils;

@Controller
public class MessageController {
	private static final Logger logger=LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping(path= {"/msg/addMessage"},method= {RequestMethod.POST})
	@ResponseBody
	public String addMessage(@RequestParam("toName") String toName,
							@RequestParam("content") String content) {
		try {
			if(hostHolder.getUser()==null) {
				return QandaUtils.getJSONString(999,"未登录");
			}
			
			User user=userService.selectByName(toName);
			
			if(user==null) {
				return QandaUtils.getJSONString(1, "用户不存在");
			}
			
			Message message=new Message();
			message.setCreatedDate(new Date());
			message.setFromId(hostHolder.getUser().getId());
			message.setToId(user.getId());
			message.setContent(content);
			messageService.addMessage(message);
			
			return QandaUtils.getJSONString(0);
			
		}catch(Exception e) {
			logger.error("消息发送失败"+e.getMessage());
			return QandaUtils.getJSONString(1,"发送失败");
		}
	}
}
