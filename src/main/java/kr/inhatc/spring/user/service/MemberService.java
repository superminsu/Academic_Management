package kr.inhatc.spring.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.user.repository.MemberRepository;
import kr.inhatc.spring.user.utils.ScriptUtils;
import lombok.RequiredArgsConstructor;
import kr.inhatc.spring.Privacy.PrRepository;
import kr.inhatc.spring.Privacy.PrVo;
import kr.inhatc.spring.personalSchedule.ScheduleVo;
import kr.inhatc.spring.user.dto.MemberDTO;
import kr.inhatc.spring.user.entity.MemberVo;

@Service
//@Transactional
@RequiredArgsConstructor
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;
	
	ScriptUtils scriptUtils;

	//모든 정보 검색
	public List<MemberVo> findAll() {
		List<MemberVo> members = new ArrayList<>();
		memberRepository.findAll().forEach(e -> members.add(e));
		return members;
	}

	//회원 mbrNo 찾기
	public Optional<MemberVo> findById(Long mbrNo) {
		Optional<MemberVo> member = memberRepository.findById(mbrNo);
		return member;
	}
	
	//회원 id 찾기
	public boolean findById(MemberVo member) {
		try {
			MemberVo findMember = memberRepository.findById(member.getId());
			if(findMember.getId() != null) return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	//회원 삭제
	public void deleteById(Long mbrNo) {
		memberRepository.deleteById(mbrNo);
	}
	
	//선택 회원 삭제
	public void deleteAll(Long[] deleteId) {
		memberRepository.deleteMember(deleteId);
	}

	//회원 가입
	public MemberVo save(MemberVo member) {
		memberRepository.save(member);
		return member;
	}
	
	//로그인 기능
	public boolean login(MemberVo member) {	
		try {
			MemberVo findMember = memberRepository.findByIdAndPw(member.getId(), member.getPw());
			if(findMember.getId() != null && findMember.getPw() !=null) return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	//검색 - 페이징 처리
	public Page<MemberVo> list(String keyword, int page){
		
		//keyword가 없을 경우
		if(keyword.isEmpty()) {
			//RageRequest.of(우리가 보는 페이지, n개씩 페이징 처리, 내림차순 정렬)
			return memberRepository.findAll(PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "mbrNo")));
		}
		else { //keyword가 있을 경우
			return memberRepository.findByIdContaining(keyword, PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "mbrNo")));
		}
	}
	
	//회원 정보 상세보기
	public MemberVo getDetail(Long mbrNo) {
		Optional<MemberVo> optional = memberRepository.findById(mbrNo);
		if(optional.isPresent()) {
			MemberVo member = optional.get();
			return member;
		}
		else {
			throw new NullPointerException();
		}
	}
	
	//회원 정보 수정
	public void updateById(Long mbrNo, MemberVo member) {
		Optional<MemberVo> e = memberRepository.findById(mbrNo);

		if (e.isPresent()) {
			e.get().setId(member.getId());
			e.get().setPw(member.getPw());
			e.get().setDept(member.getDept());
			e.get().setBan(member.getBan());
			memberRepository.save(member);
		}
	}
}
