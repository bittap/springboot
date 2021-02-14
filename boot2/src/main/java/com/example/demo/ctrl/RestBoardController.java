package com.example.demo.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.BoardLogic;
import com.google.gson.Gson;

@RestController
@RequestMapping("/board/*")
public class RestBoardController {
	Logger logger = Logger.getLogger(RestBoardController.class);
	@Autowired
	public BoardLogic boardLogic = null;
	@GetMapping(value="jsonBoardList.sp4", produces="application/json;charset=UTF-8")
	public String jsonBoardList(@RequestParam Map<String,Object> pMap) {
	   logger.info("jsonBoardList 호출성공");
	   if(pMap == null) {
	      pMap =new HashMap<String, Object>();
	      pMap.put("bm_no", 0);
	   }
	   List<Map<String, Object>> boardList = null;
	   boardList = boardLogic.boardList(pMap);
	   Gson g = new Gson();
	   String temp = null;
	   temp = g.toJson(boardList);
	   
	   return temp;
	}
}

