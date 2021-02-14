package com.example.demo.model;

import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardSDao {
	Logger logger = Logger.getLogger(BoardSDao .class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate = null;
	public int boardSInsert(Map<String, Object> pMap) {
		logger.info("SDao-boardInsert 호출성공");
		int result = 0;
		try {
			result = sqlSessionTemplate.update("boardSInsert", pMap);	
			logger.info("result : " + result); //1
		} catch (Exception e) {
			logger.info("Exception : " +e.toString());
		}
		return result;
	}
	public void boardSDelete(Map<String, Object> pMap) {
		int result = 0;
		try {
			result = sqlSessionTemplate.delete("boardSDelete", pMap);	
			logger.info("result : " + result); //1
		} catch (Exception e) {
			logger.info("Exception : " +e.toString());
		}
		
	}
}

