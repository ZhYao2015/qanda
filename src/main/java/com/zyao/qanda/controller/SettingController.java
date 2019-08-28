package com.zyao.qanda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyao.qanda.model.User;
import com.zyao.qanda.service.QandaService;

@Controller
public class SettingController {
	
	@Autowired
	QandaService qandaService;
	
	@RequestMapping(path= {"/setting"})
	@ResponseBody
	public String setting() {
		System.out.print((new User()));
		return "Setting ok:"+qandaService.getMessage(7);
	}
	
}
