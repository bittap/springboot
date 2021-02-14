package main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import console.ExamConsole;
import console.ExamConsoleAppConfig;
import console.GridExamConsole;
import console.InlineExamConsole;
import exam.Exam;
import exam.NewIceExamAppConfig;
import exam.NewlecExam;

public class Main {

	public static void main(String[] args) {
		/*
		Exam exam = new Kor(50,30);
		ExamConsole console = new GridExamConsole();
		console.setExam(exam);
		*/
		//contextを使うようにしてくれるインターフェース
		//ClassPathXmlApplicationContext、 FileSystemXmlApplicationContext
		//などで活用できる
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext();
		context.register(NewIceExamAppConfig.class);
		context.register(ExamConsoleAppConfig.class);
		context.refresh();
				//new ClassPathXmlApplicationContext("main/setting.xml");
		
		Exam exam = (Exam) context.getBean("exam");
		System.out.println(exam.toString());
		//名前で呼び出す。
		ExamConsole console = (ExamConsole)context.getBean("console");
		//こちらがよく使われる。
		//ExamConsole console = context.getBean(ExamConsole.class);
		//java.util.ArrayList.class
		//なぜかわからないけどリストUtilを使うとクラスでコンストラクタができない。
		
		console.print();
		
	}

}
