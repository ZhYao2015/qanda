package com.zyao.qanda.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyao.qanda.model.Comment;
import com.zyao.qanda.model.EntityType;
import com.zyao.qanda.model.HostHolder;
import com.zyao.qanda.model.Question;
import com.zyao.qanda.model.ViewObject;
import com.zyao.qanda.service.CommentService;
import com.zyao.qanda.service.QuestionService;
import com.zyao.qanda.service.UserService;
import com.zyao.qanda.utils.QandaUtils;

@Controller
public class QuestionController {
	private static final Logger logger=LoggerFactory.getLogger(QuestionController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	HostHolder hostHolder;

	@Autowired
	CommentService commentService;
	
	
	@RequestMapping(value="/question/add",method= {RequestMethod.POST})
	@ResponseBody
	public String addQuestion(@RequestParam("title") String title,
					@RequestParam("content") String content) {
		try {
			Question question=new Question();
			question.setContent(content);
			question.setTitle(title);
			question.setCreatedDate(new Date());
			question.setCommentCount(0);
			if(hostHolder.getUser()==null) {
				question.setUserId(QandaUtils.ANONYMOUS_USER);
			}else {
				question.setUserId(hostHolder.getUser().getId());
			}
			
			if(questionService.addQuestion(question)>0) {
				return QandaUtils.getJSONString(0);
			}
			
			
		}catch(Exception e) {
			logger.error("增加问题失败"+e.getMessage());
		}
		 
		return QandaUtils.getJSONString(1, "失败");
	}
	
	
	
	@RequestMapping(value="/question/{qid}",method=RequestMethod.GET)
	public String questionDetail(Model model, @PathVariable("qid") int qid) {
		Question question = questionService.getById(qid);
		model.addAttribute("question", question);
		
		List<Comment> commentList=commentService.getCommentsByEntity(qid,EntityType.ENTITY_QUESTION);
		List<ViewObject> comments=new ArrayList<ViewObject>();
		for(Comment comment:commentList) {
			ViewObject vo=new ViewObject();
			vo.set("comment", comment);
			vo.set("user", userService.getUser(comment.getUserId()));
			comments.add(vo);
		}
		
		model.addAttribute("comments", comments);
		
		return "detail";
	}
}
