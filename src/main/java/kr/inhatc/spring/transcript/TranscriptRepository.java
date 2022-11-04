package kr.inhatc.spring.transcript;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TranscriptRepository extends JpaRepository<TranscriptVo, Long>{
	public List<TranscriptVo> findById(String id);	//id 조회
	public List<TranscriptVo> findByIdAndYgt(String id, String ygt);	//id 년도학년학기 조회
	public void deleteById(String id);
}
