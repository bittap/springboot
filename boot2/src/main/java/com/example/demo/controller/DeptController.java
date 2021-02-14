package com.example.demo.controller;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dept/*")
public class DeptController {
	Logger logger = LogManager.getLogger(this.getClass().toString());
	
	@GetMapping("deptList")
	public String deptList() {
		logger.info("deptList 호출 성공");
		//응답페이지는 application.properties를 사용하여 /WEB-INF/jsp/?.jsp를 호출
		return "dept/deptList";
	}
}
