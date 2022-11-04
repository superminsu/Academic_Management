package kr.inhatc.spring.review;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.user.entity.CourseVo;
import kr.inhatc.spring.user.entity.MemberVo;
import kr.inhatc.spring.user.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RvService {
	@Autowired
	private RvRepository rvRepository;
	@Autowired
	private CourseRepository courseRepository;
	
	public List<RvVo> findById(String id) {
		List<RvVo> rv = rvRepository.findById(id);
		return rv;
	}
	public List<RvVo> findByIdAndCrname(String id, String crname) {
		List<RvVo> rv = rvRepository.findByIdAndCrname(id, crname);
		return rv;
	}
	public List<RvVo> findByCrnameAndCrprofessor(String crname, String crprofessor) {
		List<RvVo> rv = rvRepository.findByCrnameAndCrprofessor(crname, crprofessor);
		return rv;
	}
	public RvVo save(RvVo review) {
		CourseVo e = courseRepository.findByCrcode(review.getCrcode());
		String crname = e.getCrname();
		String crprofessor = e.getCrprofessor();
		review.setCrname(crname);
		review.setCrprofessor(crprofessor);
		rvRepository.save(review);
		return review;
	}
	public void deleteByIdAndCrcode(String id, String crcode) {
		rvRepository.deleteByIdAndCrcode(id, crcode);
	}
	//리뷰 수정
	public void updateById(Long rvNo, RvVo review) {
		Optional<RvVo> e = rvRepository.findById(rvNo);

		if (e.isPresent()) {
			e.get().setRating(review.getRating());
			e.get().setCrreview(review.getCrreview());
			rvRepository.save(review);
		}else {
			throw new NullPointerException();
		}
	}
	//페이징 처리
	public Page<RvVo> list(int page){
		//RageRequest.of(우리가 보는 페이지, n개씩 페이징 처리, 내림차순 정렬)
		return rvRepository.findAll(PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "rvNo")));
	}
	public boolean check(RvVo rvVo) {	
		try {
			RvVo checkrv = rvRepository.findByIdAndCrnameAndCrprofessor(rvVo.getId(), rvVo.getCrname(),rvVo.getCrprofessor());
			if(checkrv.getId() != null && checkrv.getCrname() !=null && checkrv.getCrprofessor() !=null) return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
