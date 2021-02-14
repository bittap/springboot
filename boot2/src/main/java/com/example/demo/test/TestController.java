package com.example.demo.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/*
 * spring4부터는 서블릿에 의존적이지 않은 프레임워크가 되었다.
 * 다시말해 req, res가 없더라도 웹서비스를 구현할 수 있는 프레임워크로 
 * 변경되었다. 
 * 
 */
@Controller
@RequestMapping("/test/*")
public class TestController {
	Logger logger = LogManager.getLogger(this.getClass().toString());
  
   @Autowired
   public TestLogic testLogic = null;
   
   @GetMapping("cookieMake.sp4")
   public String cookieMake(HttpServletResponse res) {
	   Cookie c1 = new Cookie("mem_id", "apple");
	   c1.setMaxAge(60*60);
	   c1.setPath("/test/test");
	   Cookie c2 = new Cookie("good_name", "노트북");
	   c2.setMaxAge(60*60);
	   c2.setPath("/test/test");
	   res.addCookie(c1);
	   res.addCookie(c2);
	   return "redirect:cookieList2.sp4";
   }
   @GetMapping("cookieList.sp4")
   public String cookieList(@CookieValue(value="mem_id", required=false) String mem_id, @CookieValue(value="good_name", required=false) String good_name) {
	   logger.info("쿠키에서 꺼낸 아이디 : " + mem_id);
	   logger.info("쿠키에서 꺼낸 상품명: " + good_name);
	   return "redirect:cookieList.jsp";
   }
   
   @GetMapping("cookieList2.sp4")
   public String cookieList2(HttpServletRequest req, HttpServletResponse res) {
		Cookie[] cs = req.getCookies();
		int result = 0;
		for(int i =0; i<cs.length; i++) {
			String cName = cs[i].getName();
			if("mem_id".equals(cName)) {
				Cookie c = new Cookie(cName, "");
			    c.setMaxAge(0);
			    res.addCookie(c);
			    result = 1;
			}
		
		}
		logger.info("result : " + result);
			
			/* if(c1 != null) {
		   //쿠키값 삭제 처리하기
		   String cName = c1.getName();//mem_id
		   logger.info("cName : " + cName);
		   Cookie c = new Cookie(cName, "");
		   c.setMaxAge(0);
		   res.addCookie(c);
	   }*/
	   return "redirect:cookieList.jsp";
   }
   
   @GetMapping("sessionMake.sp4")
   public String sessionMake(HttpSession session) {
	   session.setAttribute("mem_name", "김유신");
	   session.setAttribute("mem_age", 35);
	   return "redirect:sessionList.sp4";
   }
   @GetMapping("sessionList.sp4")
   public String sessionList(HttpSession session) {
	   
	   String smem_name = (String) session.getAttribute("mem_name");
	   Integer sage = (Integer) session.getAttribute("mem_age");
	   logger.info("이름 :" +smem_name);
	   logger.info("나이 :" +sage);
	   return "redirect:sessionList.jsp";
   }
   
   //@GetMapping("testInsert.sp4")
   //@PostMapping("testInsert.sp4")
   @RequestMapping(value="testInsert.sp4", method=RequestMethod.GET)
   public String testInsert(@RequestParam Map<String, Object> pMap, HttpServletRequest req) {
      logger.info("testInsert호출 성공 ==>" + req);
      String mem_id = req.getParameter("mem_id");
      mem_id = pMap.get("mem_id").toString();
      String mem_pw = null;
      mem_pw = pMap.get("mem_pw").toString();  
      String mem_name = null;
      mem_name = pMap.get("mem_name").toString();
      logger.info("사용자가 입력한 아이디: " + mem_id);
      logger.info("사용자가 입력한 비밀번호: " + mem_pw);
      logger.info("사용자가 입력한 이름: " + mem_name);
      int result = 0;
      result = testLogic.testInsert();
      return "redirect:testInsertOk.jsp";
   }
   @GetMapping("testList.sp4")
   public String testList() {
      logger.info("testController.testList() 호출");
      //오라클 서버에서 조회된 결과를 담을 변수 선언
      String time = testLogic.testList();
      return "forward:getTestList.jsp";
   }
   @GetMapping("mTestList.sp4")
   public ModelAndView mTestList() {
      logger.info("mTestList 호출");
      logger.info("mTestList 호출");
      
      //오라클 서버에서 조회된 결과를 담을 변수 선언
      String time = testLogic.testList();
      ModelAndView mav = new ModelAndView();
      mav.addObject("time", time);
      mav.setViewName("test/getTestList");
      return mav;
   }
   @GetMapping("testUpdate.sp4")
   public String testUpdate(@RequestParam(value="name", required=false) String name, @RequestParam(value="name", required=false) String mem_id) {
	   logger.info("testUpdate 호출  성공");
	   logger.info("name : " + name);
	   logger.info("mem_id : " + mem_id);
	   
	   return "redirect:testUpdateOk.jsp";
   }
   
   @GetMapping("mTestUpdate.sp4")
   public ModelAndView mTestList(@RequestParam(value="name", required=false) String name, @RequestParam(value="name", required=false) String mem_id) {
      logger.info("mTestList 호출");
      logger.info("name : " + name);
      logger.info("mem_id : " + mem_id);
      ModelAndView mav = new ModelAndView();
      mav.setViewName("test/testUpdateOk");
      List<String> nameList = new ArrayList<>();
      nameList.add("김진호");
      nameList.add("이한샘");
      nameList.add("장민");
      //session.setAttribute("nameList", nameList);
      //request.setAttribute("nameList", nameList);
      //하는 일은 동일하다 - 저장하기 - 주소번지 - nameList
      mav.addObject("nameList", nameList);
      return mav;
   }
   
   @GetMapping("hello.sp4")
   public String hello(Model model) {
      List<String> nameList = new ArrayList<>();
      nameList.add("김진호");
      nameList.add("이한샘");
      nameList.add("장민");
      /*
       * Model 클래스는 스프링에서 화면에 필요한 값들을 서버측에서
       * 사용자측에 값을 전달하기 위한 목적으로 제공되는 클래스
       * 객체의 scope에는 4가지가 있는데
       * page - 현재페이지에서만 사용가능
       * request - 요청이 유지되는 경우만 사용가능
       * session - 그 시간 동안만 유지가능(redirect도 사용가능)
       * application - 서버가 꺼지지 않으면 계속 유지됨
       * Model은 ModelAndView와 마찬가지로 request scope를 갖음
       * 
       */
      model.addAttribute("nameList2",nameList);
	  return "test/hello";
   }
   
}


   
