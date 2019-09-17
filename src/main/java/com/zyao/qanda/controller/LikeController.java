package com.zyao.qanda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyao.qanda.model.EntityType;
import com.zyao.qanda.model.HostHolder;
import com.zyao.qanda.service.LikeService;
import com.zyao.qanda.utils.QandaUtils;

@Controller
public class LikeController {
	
	@Autowired
	LikeService likeService;
	
	@Autowired
	HostHolder hostHolder;
	
	
	@RequestMapping(path= {"/like"},method= {RequestMethod.POST})
	@ResponseBody
	public String like(@RequestParam("commendId") int commentId) {
		if(hostHolder.getUser()==null) {
			return QandaUtils.getJSONString(999);
		}
		
		long likeCount=likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
		return QandaUtils.getJSONString(0, String.valueOf(likeCount));
	}
	
	@RequestMapping(path= {"/dislike"},method= {RequestMethod.POST})
	@ResponseBody
	public String dislike(@RequestParam("commentId") int commentId) {
		if(hostHolder.getUser()==null) {
			return QandaUtils.getJSONString(999);
		}
		
		long dislikeCount=likeService.dislike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT,commentId);
		return QandaUtils.getJSONString(0, String.valueOf(dislikeCount));
	}
}
