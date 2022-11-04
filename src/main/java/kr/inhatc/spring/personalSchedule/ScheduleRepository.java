package kr.inhatc.spring.personalSchedule;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.inhatc.spring.personalSchedule.*;
import kr.inhatc.spring.user.entity.MemberVo;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<ScheduleVo, Long> {
	static final String DELETE_SCHEDULE = "DELETE FROM PSCHEDULE " + "WHERE SCD_NO IN (:deleteList)";
	//findBy 뒤에 컬럼명을 붙여주면 이를 이용한 검색이 가능
	public List<ScheduleVo> findById(String id);
	public List<ScheduleVo> findByTerm(String term);
	public List<ScheduleVo> findByEvent(String event);
	public List<ScheduleVo> findByEdate(String edate);
	
	public List<ScheduleVo> findByIdAndTerm(String id, String term);
	
	@Transactional
	@Modifying
	@Query(value = DELETE_SCHEDULE, nativeQuery = true)
	public void deleteSchedule(@Param("deleteList") Long[] deleteList);
	public void deleteById(String id);
}
