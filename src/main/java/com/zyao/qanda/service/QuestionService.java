package com.zyao.qanda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyao.qanda.dao.QuestionDao;
import com.zyao.qanda.model.Question;

@Service
public class QuestionService {
	@Autowired
	QuestionDao questionDao;
	
	public List<Question> getLastestQuestions(int userId,int offset,int limit){
		return questionDao.selectLatestQuestions(userId, offset, limit);
	}
}
