package console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import exam.Exam;

@Component(value = "console")
public class GridExamConsole implements ExamConsole {
	@Autowired
	Exam exam;
	
	
	public GridExamConsole() {
		
	}
	

	public GridExamConsole(Exam exam) {
		this.exam = exam;
	}



	@Override
	public void print() {
		if(exam == null) {
			System.out.printf("==================");
			System.out.printf("Your socre is null");
			System.out.printf("==================");
			System.out.printf("==================");
			System.out.printf("==================");
			System.out.printf("==================");
		}else {
			System.out.printf("==================");
			System.out.printf("Your socre is %d\n",exam.totalScore());
			System.out.printf("==================");
			System.out.printf("==================");
			System.out.printf("==================");
			System.out.printf("==================");
		}

	}
	

	@Override
	public void setExam(Exam exam) {
		this.exam = exam;
		
	}
	
	
}