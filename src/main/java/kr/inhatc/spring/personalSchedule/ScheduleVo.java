package kr.inhatc.spring.personalSchedule;

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
@Table(name="pschedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class ScheduleVo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long scdNo;
	
	@Column
	private String id;
	@Column
	private String term;
	@Column
	private String event;
	@Column
	private String edate;
	
	@Builder
	public ScheduleVo(String id, String term, String event, String edate) {
		this.id=id;
		this.term=term;
		this.event=event;
		this.edate=edate;
	}
}
