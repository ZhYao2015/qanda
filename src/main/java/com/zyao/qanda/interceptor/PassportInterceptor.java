package com.zyao.qanda.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zyao.qanda.dao.LoginTicketDao;
import com.zyao.qanda.dao.UserDao;
import com.zyao.qanda.model.HostHolder;
import com.zyao.qanda.model.LoginTicket;
import com.zyao.qanda.model.User;

@Component
public class PassportInterceptor implements HandlerInterceptor{

	@Autowired
	LoginTicketDao loginTicketDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	HostHolder hostHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//System.out.println("开始拦截。。。。。。");
		String ticket=null;
		if(request.getCookies()!=null) {
			for(Cookie cookie:request.getCookies()) {
				if(cookie.getName().equals("ticket")) {
					ticket=cookie.getValue();
					break;
				}
			}
		}
		
		if(ticket!=null) {
			LoginTicket loginTicket=loginTicketDao.selectByTicket(ticket);
			if(loginTicket==null||loginTicket.getExpired().before(new Date())
					||loginTicket.getStatus()==1) {
				return true;
			}
			User user=userDao.selectById(loginTicket.getUserId());
			//System.out.println("拦截到当前用户: "+user.getName());
			hostHolder.setUser(user);

		}
		
		
		//return false?
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView!=null) {
			modelAndView.addObject("user",hostHolder.getUser());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		hostHolder.clear();
	}
	
}
