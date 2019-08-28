package com.zyao.qanda;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.zyao.qanda.dao.UserDao;
import com.zyao.qanda.model.User;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=QandaApplication.class)
@Sql("/init-schema.sql")
public class InitDBTests {

	@Autowired
	UserDao userDao;
	
	@Test
	public void contextLoads() {
		Random random=new Random();
		for(int i=0;i<11;++i) {
			User user=new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setName(String.format("USER%d", i));
			user.setPassword("");
			user.setSalt("");
			userDao.addUser(user);
		}
	}
}
