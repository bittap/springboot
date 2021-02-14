package exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jdk.jfr.Name;

@Component(value = "exam")
public class NewlecExam implements Exam {
	@Value("20")
	int kor;
	@Value("30")
	int eng;
	@Value("40")
	int math;
	
	public NewlecExam() {
		// TODO Auto-generated constructor stub
	}

	
	public NewlecExam(int kor, int eng, int math) {
		super();
		this.kor = kor;
		this.eng = eng;
		this.math = math;
	}


	public int getKor() {
		return kor;
	}


	public void setKor(int kor) {
		this.kor = kor;
	}


	public int getEng() {
		return eng;
	}


	public void setEng(int eng) {
		this.eng = eng;
	}


	public int getMath() {
		return math;
	}


	public void setMath(int math) {
		this.math = math;
	}


	@Override
	public int totalScore() {
		// TODO Auto-generated method stub
		return kor+eng+math;
	}


	@Override
	public String toString() {
		return "NewlecExam [kor=" + kor + ", eng=" + eng + ", math=" + math + "]";
	}
	
	
	
	
	
	

}