package kr.inhatc.spring.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.inhatc.spring.user.entity.MemberVo;


@Repository
@Transactional
public interface MemberRepository extends JpaRepository<MemberVo, Long>{
	
	static final String DELETE_MEMBER = "DELETE FROM MEMBER " + "WHERE MBR_NO IN (:deleteList)";
	//long이 아니라 Long으로 작성
	//findBy 뒤에 컬럼명을 붙여주면 이를 이용한 검색이 가능하다
	public MemberVo findById(String id);	//id 조회
	public List<MemberVo> findByPw(String pw);	//pw 조회
	public List<MemberVo> findByDept(String dept);	//dept 조회
	public List<MemberVo> findByBan(String ban);	//ban 조회
	
	public MemberVo findByIdAndPw(String id, String pw);	//id, pw 조회
	
	//삭제 기능 sql
	@Transactional
	@Modifying
	@Query(value = DELETE_MEMBER, nativeQuery = true)
	public void deleteMember(@Param("deleteList") Long[] deleteList);
	
	//검색 기능 - Containing(Like 검색)
	public Page<MemberVo> findByIdContaining(String id, Pageable pageable);
}