package kr.inhatc.spring.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
//@DynamicInsert
//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)	//디폴트 생성자 생성
@Data	//getter, setter, ToString 자동 생성
public class MemberVo {

	@Id		//기본키 설정
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long mbrNo;
	
	@Column
	private String id;
	@Column
	private String pw;
	@Column
	private String dept;
	@Column
	private String ban;
	
	@Builder
	public MemberVo(String id, String pw, String dept, String ban) {
		this.id = id;
		this.pw = pw;
		this.dept = dept;
		this.ban = ban;
	}
}
