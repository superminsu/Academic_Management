package kr.inhatc.spring.personalSchedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.user.entity.MemberVo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	//모든 정보 검색
	public List<ScheduleVo> findAll() {
		List<ScheduleVo> schedule = new ArrayList<>();
		scheduleRepository.findAll().forEach(e -> schedule.add(e));
		return schedule;
	}
	public List<ScheduleVo> findById(String id) {
		List<ScheduleVo> schedule = scheduleRepository.findById(id);
		return schedule;
	}
	//id 리스트 찾기
	public List<ScheduleVo> findByIdAndTerm(String id, String term) {
		List<ScheduleVo> schedule = scheduleRepository.findByIdAndTerm(id, term);
		return schedule;
	}
	//일정 삭제
	public void deleteById(String id) {
		scheduleRepository.deleteById(id);
	}
	public void deleteAll(Long[] deleteId) {
		scheduleRepository.deleteSchedule(deleteId);
	}
	//일정 등록
	public ScheduleVo save(ScheduleVo schedule) {
		scheduleRepository.save(schedule);
		return schedule;
	}
	//일정 수정?
	public void updateById(Long scdNo, ScheduleVo schedule) {
		Optional<ScheduleVo> e = scheduleRepository.findById(scdNo);

		if (e.isPresent()) {
			e.get().setScdNo(schedule.getScdNo());
			e.get().setId(schedule.getId());
			e.get().setTerm(schedule.getTerm());
			e.get().setEvent(schedule.getEvent());
			e.get().setEdate(schedule.getEdate());
			scheduleRepository.save(schedule);
		}
	}
}
