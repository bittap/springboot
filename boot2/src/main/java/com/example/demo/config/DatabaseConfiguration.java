package com.example.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {
   @Autowired
   ApplicationContext context;
   
   //setter객체 주입법 코드|생성자 객체주입법-xml문서에서 활용
   @Bean
   @ConfigurationProperties(prefix = "spring.datasource.hikari")
   public HikariConfig hikariConfig() {
      return new HikariConfig();
   }
   /*
    * 여기까지는 물리적으로 떨어져 있는 오라클 서버와 연결통로를 확보하기 처리
    * 두번째는 쿼리문이 등록되어 있는 xml문서의 물리적인 위치를 처리
    */
   @Bean
   public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
	  System.out.println("sqlSessionFactory호출");
      SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      sqlSessionFactoryBean.setDataSource(dataSource);
      sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath:/mapper/**/*.xml"));
      return sqlSessionFactoryBean.getObject();
   }
   //DataSource는 물리적으로 떨어져 있는 오라클 서버에 관련된 정보가 있어야
   //객체 생성이 완성됨(메모리에 로딩이 된다.)
   @Bean
   public DataSource dataSource() {
   /*spring4에서는 xml문서에 db관련된 정보들을 등록하였다.
    * xml문서 사용시 SAX문제 혹은 ParserException문제 등으로
    * 초보자는 오타문제 객체주입 문제로 서버자체가 기동이 안됨
    * 아무것도 테스트 할 수 없다. 
    * 
    */
	  System.out.println("dataSource호출");
      DataSource dataSource = new HikariDataSource(hikariConfig());
      return dataSource;
   }
   /*
    * 위에서 물리적인 연결통로를 확보 했으므로 등록된 쿼리문을 id로 호출하는데
    * 필요한 메소드 즉 sqlSessionTemplate.insert("boardInsert")
    * sqlSessionTemplate.selectList("boardList")
    * SqlSessionTemplate 객체를 빈조립기에 등록해야 필요할 때
    * 객체주입을 받을 수 있다.
    */
   @Bean
   public SqlSessionTemplate sqlSessionTemplate (SqlSessionFactory sqlSessionFactory) {
	   System.out.println("sqlSessionTemplate호출");
      return new SqlSessionTemplate(sqlSessionFactory);
   }
}