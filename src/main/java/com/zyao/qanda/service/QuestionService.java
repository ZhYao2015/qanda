package com.zyao.qanda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import com.zyao.qanda.dao.QuestionDao;
import com.zyao.qanda.model.Question;

@Service
public class QuestionService {
	@Autowired
	QuestionDao questionDao;
	
	public int addQuestion(Question question) {
		
		question.setContent(HtmlUtils.htmlEscape(question.getContent()));
		question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
		//敏感词过滤 - 字典树
		
		
		return questionDao.addQuestion(question)>0?question.getId():0;
	}
	
	public Question getById(int questionId) {
		return questionDao.selectQuestionById(questionId);
	}
	
	public List<Question> getLastestQuestions(int userId,int offset,int limit){
		return questionDao.selectLatestQuestions(userId, offset, limit);
	}
}
