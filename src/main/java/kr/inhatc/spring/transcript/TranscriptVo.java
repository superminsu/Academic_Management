package kr.inhatc.spring.transcript;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import kr.inhatc.spring.cr.entity.CrRegVo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transcript")
@NoArgsConstructor(access = AccessLevel.PROTECTED)	//디폴트 생성자 생성
@Data	//getter, setter, ToString 생성
public class TranscriptVo {
	//기본키 설정
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long rNo;		//번호
	
	@Column
	private String division;//이수구분
	@Column
	private String id;		//로그인 아이디
	@Column
	private String crname;	//교과목명
	@Column
	private String ygt;		//년도 학년 학기
	@Column
	private String grade;	//성적
	@Column
	private int credit; 	//시수
	@Column
	private double score;	//학점

	@Builder
	public TranscriptVo(String division, String id, String crname, String ygt, String grade, int credit, double score) {
		this.division = division;
		this.id = id;
		this.crname = crname;
		this.ygt = ygt;
		this.grade = grade;
		this.credit = credit;
		this.score = score;
	}
}
