package com.zyao.qanda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zyao.qanda.model.Message;

@Mapper
public interface MessageDao {
	
	String TABLE_NAME=" message ";
	String INSERT_FIELDS=" from_id, to_id, content, has_read, converstation_id, created_date ";
	String SELECT_FIELDS=" id, "+INSERT_FIELDS;
			
	@Insert({"insert into ",TABLE_NAME, "(",INSERT_FIELDS,") values (#{fromId},#{toId},#{content},#{hasRead}"
			+ ",#{conversationId},#{createdDate})"})
	int addMessage(Message message);
	
	//需要分页
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
		"where conversation_id=#{conversationId} order by created_date desc"})
	List<Message> getConversationDetail(@Param("converstionId") String converstationId);
	
	
	
	
	
}
