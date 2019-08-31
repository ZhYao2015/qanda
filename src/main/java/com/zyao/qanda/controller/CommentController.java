package com.zyao.qanda.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.zyao.qanda.model.Comment;
import com.zyao.qanda.model.EntityType;
import com.zyao.qanda.model.HostHolder;
import com.zyao.qanda.service.CommentService;

@Controller
public class CommentController {

	public static final Logger logger=LoggerFactory.getLogger(CommentService.class);
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	CommentService commentService;
	
	@RequestMapping(path= {"/addComment"},method=RequestMethod.POST)
	public String addComment(@RequestParam("questionId") int  questionId,
							@RequestParam("content") String content) {
		try {
			Comment comment=new Comment();
			comment.setContent(content);
			if(hostHolder.getUser()!=null) {
				comment.setUserId(hostHolder.getUser().getId());
			}else {
				return "redirect:/reglogin";
			}
			
			comment.setCreatedDate(new Date());
			comment.setEntityType(EntityType.ENTITY_QUESTION);
			comment.setEntityId(questionId);
			commentService.addComment(comment);
		} catch (Exception e) {
			logger.error("增加评论失败"+e.getMessage());
		}
		
		return "redirect:/question/"+questionId;
	}
}
