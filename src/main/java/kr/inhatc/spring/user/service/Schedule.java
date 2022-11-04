package kr.inhatc.spring.user.service;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class Schedule {
	private String dt;
	private String dd;
}
