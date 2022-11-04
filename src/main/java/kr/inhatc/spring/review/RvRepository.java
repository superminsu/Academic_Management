package kr.inhatc.spring.review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RvRepository extends JpaRepository<RvVo, Long> {
	public List<RvVo> findById(String id);//id조회
	public List<RvVo> findByIdAndCrname(String id, String crname);
	public List<RvVo> findByCrnameAndCrprofessor(String crname, String crprofessor);
	public void deleteById(String id);
	public void deleteByIdAndCrcode(String id, String crname);
	public RvVo findByIdAndCrnameAndCrprofessor(String id, String crname, String crprofessor);
}
