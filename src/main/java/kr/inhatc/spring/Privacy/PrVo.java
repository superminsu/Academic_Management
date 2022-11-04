package kr.inhatc.spring.Privacy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import kr.inhatc.spring.personalSchedule.ScheduleVo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="privacy")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class PrVo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pNo;
	
	@Column
	private String id;
	@Column
	private String kname;
	@Column
	private String ename;
	@Column
	private String address;
	@Column
	private String phone;
	@Column
	private String email;
	@Column
	private String fkname;
	@Column
	private String frelation;
	@Column
	private String fphone;
	@Column
	private String femail;
	@Column
	private String bname;
	@Column
	private String bank;
	@Column
	private String bnum;
	
	@Builder
	public PrVo(String kname, String ename, String id, String address, String phone, String email,
			String fkname, String frelation, String fphone, String femail,
			String bname, String bank, String bnum) {
		this.id=id;
		this.kname=kname;
		this.ename=ename;
		this.address=address;
		this.phone=phone;
		this.email=email;
		this.fkname=fkname;
		this.frelation=frelation;
		this.fphone=fphone;
		this.femail=femail;
		this.bname=bname;
		this.bank=bank;
		this.bnum=bnum;
	}
}
