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
		//context���g���悤�ɂ��Ă����C���^�[�t�F�[�X
		//ClassPathXmlApplicationContext�A FileSystemXmlApplicationContext
		//�ȂǂŊ��p�ł���
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext();
		context.register(NewIceExamAppConfig.class);
		context.register(ExamConsoleAppConfig.class);
		context.refresh();
				//new ClassPathXmlApplicationContext("main/setting.xml");
		
		Exam exam = (Exam) context.getBean("exam");
		System.out.println(exam.toString());
		//���O�ŌĂяo���B
		ExamConsole console = (ExamConsole)context.getBean("console");
		//�����炪�悭�g����B
		//ExamConsole console = context.getBean(ExamConsole.class);
		//java.util.ArrayList.class
		//�Ȃ����킩��Ȃ����ǃ��X�gUtil���g���ƃN���X�ŃR���X�g���N�^���ł��Ȃ��B
		
		console.print();
		
	}

}
