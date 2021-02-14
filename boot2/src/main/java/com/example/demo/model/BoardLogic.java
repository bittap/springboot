package com.example.demo.model;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BoardLogic {
	Logger logger = Logger.getLogger(BoardLogic .class);
	//전체 레코드 수 담을 변수 선언
	int total = 0;
	
	@Autowired
	BoardMDao boardMDao = null;
	@Autowired(required=false)
	BoardSDao boardSDao = null;
	
	//언제 호출하지? 
	public int getTotal(Map<String, Object> pMap) {
		total = boardMDao.getTotal(pMap);
		return total;
	}
	
	public List<Map<String,Object>> boardList(Map<String, Object> pMap){
		logger.info("BoardLogic의 boardList호출 성공");
		List<Map<String, Object>> boardList = null;
		//페이지 네비게이션 추가 코드
		getTotal(pMap);
		logger.info("total : " + total);
		//현재 내가 바라보는 페이지 번호 
		int pageNumber = 0;
		//한 페이지에 뿌려질 로우 수
		int pageSize = 4;
		//시작 번호
		int start = 0;
		//끝 번호
		int end = 0;
		//화면에서 쿼리 스트링으로 넘어오는 페이지 번호는 문자열이므로 
		//임시로 저장할 문자열 변수 선언

		String spageNumber="";
		if(pMap.get("nowPage") != null) {
			spageNumber = pMap.get("nowPage").toString();
			pageNumber = Integer.parseInt(spageNumber) + 1;
		}
		String spageSize="";
		if(pMap.get("pageSize") != null) {
			spageSize = pMap.get("pageSize").toString();
			pageSize = Integer.parseInt(spageSize);
		}
		/*
		 * 한 페이지에 뿌려질 로우의 수 만큼 for문을 처리하는데 
		 * 문제는 더 이상 글이 없는 마지막 페이지의 경우 그 반복문을 
		 * 전체 다 처리할 수가 없다.
		 * end의 값은 전체 레코드 수에 따라 달라져야 한다.
		 */
		if(pageNumber>0) {
			start = ((pageNumber-1)*pageSize)+1;
			end = pageNumber * pageSize;
			pMap.put("start", start); // 1 5 9
			if(end > total) {//total 0
				pMap.put("end", total);
			}else {
				pMap.put("end", end);
			}
			
		}
		logger.info("start : " + start + ", end : " + end);
		boardList = boardMDao.boardList(pMap);
		//혹시 상세 조회에서 넘어온 요청인거니?
		String gubun = ""; //지변은 반드시 초기화 해야함.
		if(pMap.get("gubun") != null) {
			gubun = pMap.get("gubun").toString();	
		}
		if("detail".equals(gubun)) {
			//응. 상세 조회에서 시작된 요청 맞아! //그럼 조회수를 1 증가시켜주세요.
			if(boardList.size()==1) {//조회 성공이야? 응이면 1증가하자.
				boardMDao.bmHitUpdate(pMap);
			}
		}
		return boardList;
	}
	/**********************************************************
	 * 새글쓰기와 댓글쓰기 구현
	 * @param pMap
	 * @return
	 **********************************************************/
	public int boardInsert(Map<String, Object> pMap){
		logger.info("boardInsert호출 성공");
		int bmNo = 0; //오라클에서 채번한 글번호 담기
		bmNo = boardMDao.getBmNo();
		logger.info("새로 채번한 글 번호 : " + bmNo);
		//새로 채번한 글번호는 board.xml까지 전달되어야 하니ㅏ까...
		//pMap에 담아 주어야함.
		pMap.put("bm_no", bmNo);
		int bmGroup = 0; //오라클에서 채번한 글번호 담기
		if(pMap.get("bm_group") != null) {
			bmGroup = Integer.parseInt(pMap.get("bm_group").toString());
		}	
		//너 댓글이니? - read.jsp시작 - bm_group, bm_pos, bm_step
		//가져온 pos값에 1을 더해 pMap에 담기
		//가져온 step값에 1을 더해 pMap에 담기
		if(bmGroup > 0) {
			boardMDao.bmStepUpdate(pMap);
			//pos값 1증가
			pMap.put("bm_pos", Integer.parseInt(pMap.get("bm_pos").toString())+1);
			//step값 1증가
			pMap.put("bm_step", Integer.parseInt(pMap.get("bm_step").toString())+1);
			
		}
		//너 새글쓰기야? - 글번호도 채번하고 그룹번호도 채번해야함.
		else{
			//새글이므로 그룹번호를 새로 채번하였고
			bmGroup = boardMDao.getBmGroup();
			//새로 채번된 값이 유효한지 확인해 보았고
			logger.info("새로 채번한 글 그룹번호 : " + bmGroup);
			//위에서 확인된 값을 pMap에 다시 담아줌.
			//새글이므로 그룹번호 채번해서 담아줌
			pMap.put("bm_group", bmGroup);
			//원글이므로 pos도 0 step도 0이 된다.
			pMap.put("bm_pos", 0);
			pMap.put("bm_step", 0);
			
		}
		//첨부파일이 있을 때 
		int result = 0; //1:성공 0: 실패
		result = boardMDao.boardMInsert(pMap);
		if(pMap.get("bs_file") != null && pMap.get("bs_file").toString().length()>0) {
			//insert here - 첨부파일이 있을 경우 데이터 등록하기
			pMap.get("bm_no : " + bmNo);
			//첨부 파일 추가 성공 여부 담기
			int sresult = 0;
			sresult = boardSDao.boardSInsert(pMap);
			logger.info("sresult : " + sresult);
			
		}
		return result;
	}
	
	
	public int boardUpdate(Map<String, Object> pMap){
		logger.info("boardUpdate호출 성공");
		//새로 채번한 글번호는 board.xml까지 전달되어야 하니ㅏ까...
		//pMap에 담아 주어야함
		int result = 0;
		result = boardMDao.boardUpdate(pMap);
		return result;
	}
	
	public int boardDelete(Map<String, Object> pMap){
		logger.info("boardDelete호출 성공");
		int result = 0;
		//첨부파일이 있을 때 추가로 고려할 내용들 
		try {
			//첨부파일이 있는 물리적인 위치 담을 변수 선언
			String filePath = "C:\\workspace_jsp\\spring4\\src\\main\\webapp\\pds";
			String filename = null;
			String fullPath= null;
			
			filename = pMap.get("bs_file").toString();
			logger.info("filename : " + filename);
			fullPath = filePath+"\\"+filename;
			File file = new File(fullPath);

			result = boardMDao.boardDelete(pMap);
			//파일이 존재하니?
			if(file != null) {
				if(file.exists()) {
					boolean isOk = file.delete();
					//삭제 성공이면 true
					//삭제 실패이면 false
					logger.info("삭제 유무 : " + isOk);
					pMap.put("bs_seq", 1);
					boardSDao.boardSDelete(pMap);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
		
		
		
	}

}
