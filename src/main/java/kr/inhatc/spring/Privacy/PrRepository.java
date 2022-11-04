package kr.inhatc.spring.Privacy;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface PrRepository extends JpaRepository<PrVo, Long>{
	//findBy 뒤에 컬럼명을 붙여주면 이를 이용한 검색이 가능하다
	public List<PrVo> findById(String id);	//id 조회
	public void deleteById(String id);
}
