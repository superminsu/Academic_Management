package kr.inhatc.spring.cr.entity;

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
@Table(name = "crreg")
@NoArgsConstructor(access = AccessLevel.PROTECTED)	//디폴트 생성자 생성
@Data	//getter, setter, ToString 생성
public class CrRegVo {
	//기본키 설정
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long appNo;		//신청번호
	
	@Column
	private String crcode;	//과목코드
	@Column
	private String crname;	//과목명
	@Column
	private String crprofessor;	//교수명
	@Column
	private String id;	//로그인 아이디
	@Column
	private String crdate;	//강의시간

	@Builder
	public CrRegVo(String crcode, String crname, String crprofessor, String id, String crdate) {
		this.crcode = crcode;
		this.crname = crname;
		this.crprofessor = crprofessor;
		this.id = id;
		this.crdate = crdate;
	}
}
