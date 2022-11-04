package kr.inhatc.spring.cr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.inhatc.spring.cr.entity.CrRegVo;

@Repository
@Transactional
public interface CrRegRepository extends JpaRepository<CrRegVo, Long>{
	
	static final String DELETE_CRREG = "DELETE FROM CRREG " + "WHERE CRCODE IN (:deleteList)";

	//코드 조회
	public CrRegVo findByCrcode(String crcode);
	
	//id, 코드조회
	public CrRegVo findByIdAndCrcode(String id, String crcode);
	
	//id조회
	public List<CrRegVo> findById(String id);
	
	//검색 기능 - Containing(Like 검색)
	public Page<CrRegVo> findByCrcodeContaining(String crcode, Pageable pageable);
	
	//삭제 기능 sql
	@Transactional
	@Modifying
	@Query(value = DELETE_CRREG, nativeQuery = true)
	public void deleteCrreg(@Param("deleteList") String[] deleteList);
}
