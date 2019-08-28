package com.zyao.qanda.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyao.qanda.model.User;

@Controller
public class IndexController {
	
	
	
	
	//RequestMethod.POST will block the access
	@RequestMapping(path= {"/","/index"}, method= {RequestMethod.GET})
	@ResponseBody  
	public String index(HttpSession httpSession) {
		System.out.println("中文乱码测试");
		return "Hello qanda 中文测试"+httpSession.getAttribute("msg");
		
	}
	
	@RequestMapping(path= {"/profile/{groupId}/{userId}"})
	@ResponseBody
	public String profile(@PathVariable("userId") int userId,
						  @PathVariable("groupId") String groupId,
						  @RequestParam(value="page",defaultValue="1") int page,
						  @RequestParam(value="key",defaultValue = "wow",required=false) String key) {
		return String.format("Profile Page of %s / %d p:{%d} s:%s", groupId,userId,page,key);
	}
	
	@RequestMapping(path= {"/th"},method= {RequestMethod.GET})
	//@ResponseBody
	public String template(Model model) {
		model.addAttribute("v1","vamos");
		List<String> colors=Arrays.asList(new String[] {"RED","GREEN","BLUE"});
		model.addAttribute("colors",colors);
		Map<String,String> map=new HashMap<>();
		for(int i=0;i<4;i++) {
			map.put(String.valueOf(i),String.valueOf(i*i));
		}
		model.addAttribute("map",map);
		model.addAttribute("user",new User("LePont"));
		return "test";
	}
	
	@RequestMapping("/tt")
	public String templateInc() {
		return "testInc";
	}
	
	@RequestMapping(path= {"/request"})
	@ResponseBody
	public String request(Model model,HttpServletResponse response, HttpServletRequest request,
			HttpSession httpSession, @CookieValue("JSESSIONID") String sessionID) {
		StringBuilder sb=new StringBuilder();
		sb.append("CookieValue: "+sessionID);
		Enumeration<String> headerNames=request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String name=headerNames.nextElement();
			sb.append(name+":"+request.getHeader(name)+"<br>");
		}
		
		if(request.getCookies()!=null) {
			for(Cookie cookie:request.getCookies()) {
				sb.append("Cookie:"+cookie.getName()+" value:"+cookie.getValue());
			}
		}
		
		sb.append(request.getMethod()+"<br>");
		sb.append(request.getQueryString()+"<br>");
		sb.append(request.getPathInfo()+"<br>");
		sb.append(request.getRequestURL()+"<br>");
		
		//response ...
		
		return sb.toString();
	}
	
	@RequestMapping(path= {"/redirect/{code}"})
	public String redirect(@PathVariable("code") int code,
			HttpSession httpSession) {
		httpSession.setAttribute("msg", "jumping from redirect.");
		return "redirect:/";
	}
	
	//301 redirect???
	
	@RequestMapping(path= {"/admin"})
	@ResponseBody
	public String admin(@RequestParam("key") String key) throws UnsupportedEncodingException {
		if("admin".equals(key)) {
			return "hello admin";
		}
		throw new IllegalArgumentException("参数错误");
	}
	
	@ExceptionHandler()
	@ResponseBody
	public String error(Exception e) {
		return "error:"+e.getMessage();
	}
	
	
	
}
