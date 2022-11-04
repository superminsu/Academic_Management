package kr.inhatc.spring.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")
@NoArgsConstructor(access = AccessLevel.PROTECTED)	//디폴트 생성자 생성
@Data	//getter, setter, ToString 생성
public class CourseVo {
	//기본키 설정
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long crNo;		//강의번호
	
	@Column
	private String crgrade;	//학년
	@Column
	private String crcode;	//코드
	@Column
	private String crban;	//분반
	@Column
	private String crname;	//교과목명
	@Column
	private String crclass;	//이수구분
	@Column
	private double credit;	//학점
	@Column
	private String crprofessor;	//담당교수
	@Column
	private String crdate;	//강의시간
	@Column
	private int crlimit; //인원제한
	@Column
	private int crperson; //수강인원
	@Column
	private String crdept;	//개설학과
	@Column
	private String crroom;	//강의실
	
	@Builder
	public CourseVo(String crgrade, String crcode, String crban, String crname, String crclass, double credit, String crprofessor, String crdate, int crlimit, int crperson, String crdept, String crroom) {
		this.crgrade = crgrade;
		this.crcode = crcode;
		this.crban = crban;
		this.crname = crname;
		this.crclass = crclass;
		this.credit = credit;
		this.crprofessor = crprofessor;
		this.crdate = crdate;
		this.crlimit = crlimit;
		this.crperson = crperson;
		this.crdept = crdept;
		this.crroom = crroom;
	}
}
