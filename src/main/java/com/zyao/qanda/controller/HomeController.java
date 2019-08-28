package com.zyao.qanda.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zyao.qanda.model.Question;
import com.zyao.qanda.model.ViewObject;
import com.zyao.qanda.service.QuestionService;
import com.zyao.qanda.service.UserService;

@Controller
public class HomeController {
	public static final Logger logger=LoggerFactory.getLogger(HomeController.class);

	@Autowired
	UserService userService;
	
	@Autowired
	QuestionService questionService;
	
	
	@RequestMapping(path= {"/","/index"})
	public String index(Model model) {
		List<Question> questions=questionService.getLastestQuestions(0, 0, 10);
		List<ViewObject> vos=new ArrayList<>();
		for(Question question:questions) {
			ViewObject vo=new ViewObject();
			vo.set("question", question);
			vo.set("user",userService.getUser(question.getUserId()) );
			vos.add(vo);
		}
		model.addAttribute("vos", vos);
		return "index";
	}
}
