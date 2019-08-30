package com.zyao.qanda.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
	//Map<ThreadId,User>
	private static ThreadLocal<User> users=new ThreadLocal<User> ();
	
	public User getUser() {
		return users.get();
	}
	
	public void setUser(User user) {
		users.set(user);
	}
	
	public void clear() {
		users.remove();
	}
	
}
