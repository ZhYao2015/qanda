package com.zyao.qanda.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyao.qanda.dao.LoginTicketDao;
import com.zyao.qanda.dao.UserDao;
import com.zyao.qanda.model.LoginTicket;
import com.zyao.qanda.model.User;
import com.zyao.qanda.utils.QandaUtils;

@Service
public class UserService {
	
	
	private static final Logger logger=LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LoginTicketDao loginTicketDao;
	
	public Map<String,String> register(String username, String password){
		Map<String,String> map=new HashMap<String, String>();
		
		if(StringUtils.isBlank(username)) {
			map.put("msg", "用户名不能为空");
			return map;
		}
		if(StringUtils.isBlank(password)) {
			map.put("msg","密码不能为空");
			return map;
		}
		
		User user=userDao.selectByName(username);
		if(user!=null) {
			map.put("msg", "用户名已经存在");
			return map;
		}
		
		user=new User();
		user.setName(username);
		user.setSalt(UUID.randomUUID().toString().substring(0,5));
		user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", (new Random()).nextInt(1000)));
		user.setPassword(QandaUtils.md5(password+user.getSalt()));
		userDao.addUser(user);
		
		return map;
		
	}
	
	public Map<String,String> login(String username, String password){
		Map<String,String> map=new HashMap<String, String>();
		
		if(StringUtils.isBlank(username)) {
			map.put("msg", "用户名不能为空");
			return map;
		}
		if(StringUtils.isBlank(password)) {
			map.put("msg","密码不能为空");
			return map;
		}
		
		User user=userDao.selectByName(username);
		if(user==null) {
			map.put("msg", "用户名不存在");
			return map;
		}
		
		if(!QandaUtils.md5(password+user.getSalt()).equals(user.getPassword())){
			map.put("msg", "密码错误");
			return map;
		}
		
		String ticket=addLoginTicket(user.getId());
		map.put("ticket", ticket);
		
		return map;
		
	}
	
	
	public String addLoginTicket(int userId) {
		LoginTicket loginTicket=new LoginTicket();
		loginTicket.setUserId(userId);
		Date now=new Date();
		now.setTime(3600*24*100+now.getTime());
		loginTicket.setExpired(now);
		loginTicket.setStatus(0);
		loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		loginTicketDao.addTicket(loginTicket);
		
		return loginTicket.getTicket();
	}
	
	
	
	public User getUser(int id) {
		return userDao.selectById(id);
	}

	public void logut(String ticket) {
		loginTicketDao.updateStatus(ticket, 1);
	}
	
	
}
