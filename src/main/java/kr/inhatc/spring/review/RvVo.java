package kr.inhatc.spring.review;

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
@Table(name="review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class RvVo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long rvNo;
	
	@Column
	private String id;
	@Column
	private String crname;
	@Column
	private String crcode;
	@Column
	private String crprofessor;
	@Column
	private String rating;
	@Column
	private String crreview;
	
	@Builder
	public RvVo(String id, String crname, String crcode, String crprofessor, String rating, String crreview) {
		this.id = id;
		this.crname = crname;
		this.crcode = crcode;
		this.crprofessor = crprofessor;
		this.rating = rating;
		this.crreview = crreview;
	}
}
