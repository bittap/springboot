package com.example.demo.model;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BoardMDao {
	Logger logger = Logger.getLogger(BoardMDao.class);
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate = null;
	
	public int bmStepUpdate(Map<String, Object> pMap) {
		int result = 0;
		try {
			
			result=sqlSessionTemplate.selectOne("bmStepUpdate", pMap);
		} catch (Exception e) {
			logger.info("Exception : " +e.toString());
		}
		return result;
	}
	
	public int getBmGroup() {
		logger.info("getBmGroup 호출성공");
		int bmGroup = 0;
		try {
			bmGroup=sqlSessionTemplate.selectOne("getBmGroup");
			logger.info("bmGroup : "+ bmGroup);
		} catch (Exception e) {
			logger.info("Exception : " +e.toString());
		}
		return bmGroup;
	}
	
	/***************************************
	 * 글번호 채번하기.
	 * @return
	 ********************************************/
	
	public int getBmNo() {
		logger.info("getBmNo 호출 성공");
		int bmNo = 0;
		try {
			bmNo=sqlSessionTemplate.selectOne("getBmNo");
			logger.info("bmNo : "+ bmNo);
		} catch (Exception e) {
			logger.info("Exception : " +e.toString());
		}
		return bmNo;
	}
	
	public int getTotal(Map<String, Object> pMap) {
		logger.info("getTotal 호출 성공");
		//전체 레코드 수를 담을 변수 선언
		int tot = 0;
		try {
			tot = sqlSessionTemplate.selectOne("getTotal", pMap);
			logger.info("전체 레코드 :" + tot);
		} catch (Exception e) {
			logger.info("Exception : " + e.toString());
		}
		return tot;
	}
	
	public List<Map<String,Object>> boardList(Map<String, Object> pMap){
		logger.info("BoardDao의 boardList호출 성공");
		logger.info("사용자가 선택한 글번호 : " + pMap.get("bm_no"));
		List<Map<String, Object>> boardList = new ArrayList<>();
		try {
			logger.info("sqlSessionTemplate : " + sqlSessionTemplate);
			boardList = sqlSessionTemplate.selectList("boardList", pMap);	
			logger.info("boardList.Size:" + boardList.size());
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("Exception : " + e.toString());
		}
		return boardList;
	}
	
	public int boardMInsert(Map<String, Object> pMap) {
		logger.info("MDao-boardInsert 호출성공");
		int result = 0;
		try {
			result = sqlSessionTemplate.update("boardMInsert", pMap);	
			logger.info("result : " + result); //1
		} catch (Exception e) {
			logger.info("Exception : " +e.toString());
		}
		return result;
	}
	
	public int boardUpdate(Map<String, Object> pMap) {
		logger.info("Mdao-boardUpdate 호출성공");
		int result = 0;
		try {
			result = sqlSessionTemplate.update("boardUpdate", pMap);	
			logger.info("result : " + result); //1
		} catch (Exception e) {
			logger.info("Exception : " +e.toString());
		}
		return result;
	}

	public int boardDelete(Map<String, Object> pMap) {
		logger.info("Mdao-boardDelete 호출성공");
		int result = 0;
		try {
			result = sqlSessionTemplate.delete("boardDelete",pMap);	
			logger.info("result : " + result); //1
		} catch (Exception e) {
			logger.info("Exception : " +e.toString());
		}
		return result;
		}

	public void bmHitUpdate(Map<String, Object> pMap) {
		logger.info("bm_no : " + pMap.get("bm_no"));
		try {
			int result = 0;
			result = sqlSessionTemplate.update("bmHitUpdate", pMap);	
			logger.info("result : " + result); //1
		} catch (Exception e) {
			logger.info("Exception : " +e.toString());
		
	}

	}
	
}
	

