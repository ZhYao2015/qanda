package com.zyao.qanda.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class SensitiveService implements InitializingBean{
	
	private static final Logger logger=LoggerFactory.getLogger(SensitiveService.class);
	
	
	private class TrieNode{
		private boolean end=false;
		
		private Map<Character,TrieNode> subNodes=new HashMap<Character, TrieNode>();
		
		public void addSubNode(Character key,TrieNode node) {
			subNodes.put(key, node);
		}
		
		TrieNode getSubNode(Character key) {
			return subNodes.get(key);
		}
		
		boolean isKeywordEnd() {
			return end;
		}
		
		void setKeywordEnd(boolean end) {
			this.end=end;
		}
	}
	
	private TrieNode root=new TrieNode();

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br=null;
		try {
			InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("sensitiveWords.txt");
			InputStreamReader read=new InputStreamReader(is);
			br=new BufferedReader(read);
			
			String lineTxt;
			while((lineTxt=br.readLine())!=null) {
				addWord(lineTxt.trim());
			}
			
			
		}catch(Exception e) {
			logger.error("读取敏感词文件失败"+e.getMessage());
		}finally {
			//If multiple streams are chained together, then closing the one which was the last to be constructed, and is thus at 
			//the highest level of abstraction, will automatically close all the underlying streams. 
			if(br!=null) {
				br.close();
			}
			
		}
	}
	
	public String filter(String text) {
		if(StringUtils.isBlank(text)) {
			return text;
		}
		
		String replacement="***";
		TrieNode tempNode=root;
		int begin=0;
		int position=0;
		//算法问题 待补全 
	}
	
	private void addWord(String lineTxt) {
		TrieNode tempNode=root;
		
		for(int i=0;i<lineTxt.length();i++) {
			char c=lineTxt.charAt(i);
			
			TrieNode node=root.getSubNode(c);
			if(node==null) {
				root.addSubNode(c, node);
				
			}
			tempNode=node;
		}
	}
	
	
}
