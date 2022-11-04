package kr.inhatc.spring.cr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.cr.entity.CrRegVo;
import kr.inhatc.spring.cr.repository.CrRegRepository;
import kr.inhatc.spring.user.entity.CourseVo;
import kr.inhatc.spring.user.repository.CourseRepository;
import kr.inhatc.spring.user.utils.ScriptUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrRegService {

	@Autowired
	private CrRegRepository crRegRepository;
	@Autowired
	private CourseRepository courseRepository;
	
	ScriptUtils scriptUtils;
	
	//모든 정보 검색
	public List<CrRegVo> findAll(){
		List<CrRegVo> crreg = new ArrayList<>();
		crRegRepository.findAll().forEach(e -> crreg.add(e));
		return crreg;
	}
	
	//수강신청
	public CrRegVo save(CrRegVo crreg) {
		CourseVo e = courseRepository.findByCrcode(crreg.getCrcode());
		String crname = e.getCrname();
		String crprofessor = e.getCrprofessor();
		String crdate = e.getCrdate();
		crreg.setCrname(crname);
		crreg.setCrprofessor(crprofessor);
		crreg.setCrdate(crdate);
		crRegRepository.save(crreg);
		return crreg;
	}
	
	//강의 코드 찾기
	public boolean findByCode(CrRegVo crreg) {
		try {
			CrRegVo findCode = crRegRepository.findByCrcode(crreg.getCrcode());
			if(findCode.getCrcode() != null) return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	//강의코드, 회원아이디 찾기
	public boolean findByIdAndCrcode(CrRegVo crreg) {	
		try {
			CrRegVo findCr = crRegRepository.findByIdAndCrcode(crreg.getId(), crreg.getCrcode());
			if(findCr.getId() != null && findCr.getCrcode() !=null) return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public CrRegVo findAppNo(String id, String crcode) {
		try {
			CrRegVo findCr = crRegRepository.findByIdAndCrcode(id, crcode);
			if(findCr.getId() != null && findCr.getCrcode() !=null) return findCr;
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	//해당 회원 아이디의 신청한 강의코드 찾기
	public List<CrRegVo> findById(String id) {
		List<CrRegVo> crreg = crRegRepository.findById(id);
		return crreg;
	}
	
	//수강신청 삭제
	public void deleteCourse(Long appNo) {
		crRegRepository.deleteById(appNo);
	}
	//강의관리에서 강의 삭제 시 신청되어 있던 수강들도 함께 삭제
	public void deleteAll(String[] deleteId) {
		crRegRepository.deleteCrreg(deleteId);
	}
	
	//검색 - 수강신청 페이징 처리
	public Page<CourseVo> list(String keyword, String searchType,int page){
		if(searchType.isEmpty() && keyword.isEmpty()) { //searchType,keyword가 없을 경우
			//RageRequest.of(우리가 보는 페이지, n개씩 페이징 처리, 내림차순 정렬)
			return courseRepository.findAll(PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "crNo")));
		}
		else if(searchType.isEmpty() && !(keyword.isEmpty())) {	//searchType는 없고 keyword는 있을 경우
			return courseRepository.findByCrnameContaining(keyword, PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "crNo")));
		}
		else if(!(searchType.isEmpty()) && keyword.isEmpty()){ //searchType는 있고 keyword는 없을 경우
			return courseRepository.findByCrclassContaining(searchType, PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "crNo")));
		}
		else if(!(searchType.isEmpty()) && !(keyword.isEmpty())) {	//둘 다 있는 경우
			return courseRepository.findByCrnameContainingAndCrclassContaining(keyword, searchType,PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "crNo")));
		}
		return courseRepository.findByCrnameContainingAndCrclassContaining(keyword, searchType,PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "crNo")));
	}
}
