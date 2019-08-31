package com.zyao.qanda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyao.qanda.dao.CommentDao;
import com.zyao.qanda.model.Comment;

@Service
public class CommentService {
	@Autowired
	CommentDao commentDao;
	
	public List<Comment> getCommentsByEntity(int entityId,int entityType){
		return commentDao.selectCommentByEntity(entityId,entityType);
	}
	
	public int addComment(Comment comment) {
		return commentDao.addComment(comment)>0?comment.getId():0;
	}
	
	public int getCommentCount(int entityId,int entityType) {
		return commentDao.getCommentCount(entityId, entityType);
	}
	
	public boolean deleteComment(int commentId) {
		return false;
	}	
}
