package com.example.demo.test;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestLogic {
	Logger logger = LogManager.getLogger(this.getClass().toString());
   
   @Autowired
   public TestDao testDao = null;
   
   public String testList() {
      logger.info("testList 호출 성공");
      String time = testDao.currentTime();
      logger.info("time: " + time);
      return time;
   }

   public int testInsert() {
      logger.info("testInsert 호출 성공");
      int result = 0;
      result = testDao.testInsert();
      return result;
   }
   
}