package com.zyao.qanda.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zyao.qanda.dao.LoginTicketDao;
import com.zyao.qanda.dao.UserDao;
import com.zyao.qanda.model.HostHolder;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
	@Autowired
	LoginTicketDao loginTicketDao;
	
	@Autowired
	UserDao userDao;

	@Autowired
	HostHolder hostHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(hostHolder.getUser()==null) {
			response.sendRedirect("/relogin?next="+request.getRequestURI());
		}
		return true;
	}
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
}
