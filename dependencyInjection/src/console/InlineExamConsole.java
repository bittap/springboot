package console;

import exam.Exam;

public class InlineExamConsole implements ExamConsole {
	Exam exam;
	
	
	public InlineExamConsole() {
		// TODO Auto-generated constructor stub
	}
	
	public InlineExamConsole(Exam exam) {
		
	}



	@Override
	public void print() {
		System.out.printf("Your socre is %d",exam.totalScore());
	}
	
	@Override
	public void setExam(Exam exam) {
		this.exam = exam;
		
	}
	
	
}
