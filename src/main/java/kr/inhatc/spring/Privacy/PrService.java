package kr.inhatc.spring.Privacy;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.inhatc.spring.personalSchedule.ScheduleVo;
import kr.inhatc.spring.user.entity.MemberVo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrService {
	@Autowired
	private PrRepository prRepository;
	
	public List<PrVo> findById(String id) {
		List<PrVo> privacy = prRepository.findById(id);
		return privacy;
	}
	public PrVo save(PrVo privacy) {
		prRepository.save(privacy);
		return privacy;
	}
	public void deleteById(String id) {
		prRepository.deleteById(id);
	}
	//회원 정보 수정
	public void updateById(Long pNo, PrVo privacy) {
		Optional<PrVo> e = prRepository.findById(pNo);

		if (e.isPresent()) {
			e.get().setId(privacy.getId());
			e.get().setKname(privacy.getKname());
			e.get().setEname(privacy.getEname());
			e.get().setAddress(privacy.getAddress());
			e.get().setPhone(privacy.getPhone());
			e.get().setEmail(privacy.getEmail());
			e.get().setFkname(privacy.getFkname());
			e.get().setFrelation(privacy.getFrelation());
			e.get().setFphone(privacy.getFphone());
			e.get().setFemail(privacy.getFemail());
			e.get().setBname(privacy.getBname());
			e.get().setBank(privacy.getBank());
			e.get().setBnum(privacy.getBnum());
			prRepository.save(privacy);
		}else {
			throw new NullPointerException();
		}
	}
}
