package com.example.demo.ctrl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.BoardLogic;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	Logger logger = Logger.getLogger(BoardController.class);
	
	@Autowired
	public BoardLogic boardLogic = null;
	
	@GetMapping("boardDelete.sp4")
	public String boardDelete(@RequestParam Map<String, Object> pMap) {
		logger.info("boardDelete 호출 성공 : " + pMap.get("bm_no"));
		int result = 0;
		result = boardLogic.boardDelete(pMap);
		logger.info("result : " + result);
		return "redirect:boardList.sp4?nowPage=0";
	}
			
	
	@PostMapping("boardUpdate.sp4")
	public String boardUpdate(@RequestParam Map<String, Object> pMap) {
		logger.info("boardUpdate 호출 성공");
		int result = 0;
		result = boardLogic.boardUpdate(pMap);
		logger.info("result : " + result);
		return "redirect:boardList.sp4?nowPage=0";
	}
	
	@PostMapping("boardInsert.sp4")
	public String boardInsert(@RequestParam(value="bs_file", required=false) MultipartFile bs_file, @RequestParam Map<String, Object> pMap) {
		logger.info("hidden으로 넘겨진 값 :"+pMap.get("bm_group"));
		logger.info("hidden으로 넘겨진 값 :"+pMap.get("bm_pos"));
		logger.info("hidden으로 넘겨진 값 :"+pMap.get("bm_step"));
		logger.info("hidden으로 넘겨진 값 :"+pMap.get("bm_no"));
		logger.info("첨부파일 정보 : "+ bs_file);
		//첨부 파일을 업로드 할 경로 정보
		String savePath = "C:\\workspace_jsp\\spring4\\src\\main\\webapp\\pds";
		//첨부파일명을 담을 변수 선언
		String filename = null;
		//첨부파일의 fullname 담기
		String fullPath= null;
		if(bs_file !=null) {
			filename = bs_file.getOriginalFilename();
			fullPath= savePath + "\\" + filename;
			logger.info("filename : " + filename);
			logger.info("fullPath : " + fullPath);
			try {
		//파일명을 객체로 만들어주는 작업이 필요함. - 파일 크기 
				//File클래스는 파일명을 객체로 만들어주는 것이지 안에 있는 내용까지 넣어주는 건 아니다.
				File file = new File(fullPath);
				//bytes배열에는 파일 내용이 들어있다.
				byte[] bytes = bs_file.getBytes();
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				//위에서 만든 File 객체 안에 내용쓰기를 처리 해야 함
				bos.write(bytes);//읽은 파일내용을 써줌.
				//io객체는 사용 후 반드시 닫아줌
				bos.close();
				//내용이 복제 되었으니까 파일크기 계산하기 
				long size = file.length();
				double d_size = Math.floor(size/1024);
				pMap.put("bs_file", filename);
				pMap.put("bs_size", d_size);
				logger.info("bs_file : " +pMap.get("bs_file"));
			} catch (Exception e) {
				logger.info(e.toString());
			}
		}
		
		int result = 0; //0이면 등록 실패, 1이면 등록 성공
		result=boardLogic.boardInsert(pMap);
		logger.info("result : " + result);
		return "redirect:boardList.sp4?nowPage=0";
	}
	/*
	 * spring에서는 사용자가 입력한 값을 어노테이션으로 받아줌.
	 * 화면에서 필요한 정보는 request 객체 없이도 값을 유지하기 위해서 
	 * 추가로 Model 객체를 지원함(UI를 지원하기 위한 API)
	 * POJO 프레임워크에서는 지원하지 않음.
	 */
	
	@GetMapping("boardDetail.sp4")
	public String boardDetail(@RequestParam Map<String, Object> pMap, Model model) {
		logger.info("boardDetail 호출 성공");
		List<Map<String, Object>> boardDetail = null;
		pMap.put("gubun", "detail");
		boardDetail = boardLogic.boardList(pMap);
		model.addAttribute("boardDetail", boardDetail);
		return "forward:read.jsp";
		
	}

	@GetMapping("boardList.sp4")
	 public String boardList(HttpSession session,Model model,@RequestParam Map<String,Object> pMap) {
	      logger.info("boardList 호출성공");
	      logger.info("bm_date : " + pMap.get("bm_date"));
	      logger.info("cb_search : " + pMap.get("cb_search"));
	      logger.info("keyword : " + pMap.get("keyword"));
	      //0부터 시작하는 nowPage에 1을 더해줍니다.
	      int pageNumber = 0;
	      logger.info("pageNumber : " + pMap.get("pageNumber"));
	      logger.info("pageSize : " + session.getAttribute("pageSize"));
	      logger.info("nowPage : " + pMap.get("nowPage"));
	      //if(pMap.get("nowPage" != null)) {
	      //	int i = Integer.parseInt(pMap.get("nowPage")).toString();
	      //		pMap.put(pageNumber, i);
	      int total = 0;
	      total=boardLogic.getTotal(pMap);
	      session.setAttribute("stot", total);
	      List<Map<String,Object>> boardList =null;
	      pMap.put("pageNumber", pageNumber);
	      pMap.put("pageSize",session.getAttribute("pageSize"));
	      boardList=boardLogic.boardList(pMap);
	      model.addAttribute("boardList",boardList);
	      return "forward:list2.jsp";
	      
   }
	
	

}
