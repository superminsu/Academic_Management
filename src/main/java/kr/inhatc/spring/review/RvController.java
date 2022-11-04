package kr.inhatc.spring.review;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.inhatc.spring.cr.entity.CrRegVo;
import kr.inhatc.spring.cr.service.CrRegService;
import kr.inhatc.spring.user.entity.CourseVo;
import kr.inhatc.spring.user.entity.MemberVo;
import kr.inhatc.spring.user.repository.CourseRepository;
import kr.inhatc.spring.user.service.CourseService;


@Controller
public class RvController {
	@Autowired
	private RvService rvService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CrRegService crRegService;
	
	@GetMapping("/cis_review")
	public String review(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		List<CrRegVo> crreg = crRegService.findById(id);
		String[] crcode = new String[20];
		
		for(int i=0; i < crreg.size(); i++) {
			crcode[i] = crreg.get(i).getCrcode();
			System.out.println(crcode[i]);
		}
	  
		List<CourseVo> list = courseService.seleteCrcode(crcode);
		model.addAttribute("Course", list);
		
		return "/cis_review";
	}
	@PostMapping("/review_s")
	public String reviewSearch(HttpServletRequest request, Model model, RvVo rvVo) throws Exception{
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String crname = request.getParameter("crname");
	
		List<CrRegVo> crreg = crRegService.findById(id);
		String[] crrcode = new String[20];
		
		for(int i=0; i < crreg.size(); i++) {
			crrcode[i] = crreg.get(i).getCrcode();
			System.out.println(crrcode[i]);
		}
	  
		List<CourseVo> list = courseService.seleteCrcode(crrcode);
		model.addAttribute("Course", list);
		
		List<RvVo> sReview = rvService.findByIdAndCrname(id, crname);
		model.addAttribute("Review", sReview);
		
		return "/cis_review";
	}
	@PostMapping("/review_update")
	public String updateReview(RvVo rvVo, Model model) throws Exception {
		rvService.updateById(rvVo.getRvNo(), rvVo);
		List<RvVo> course = rvService.findById(rvVo.getId());
		model.addAttribute("Course", course);
		
		return "/cis_review";
	}
	@GetMapping("/reviewpage")
	public String reviewpage(Model model, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
		Page<RvVo> List = rvService.list(page);
		int totalPage = List.getTotalPages();	//총 페이지 수
		model.addAttribute("list", List.getContent());	//선택된 페이지에서 검색된 데이터만 List형태로 반환
		model.addAttribute("totalPage", totalPage);

		return "/reviewpage";
	}
	@PostMapping("/goreview")
	public String reviewpages(Model model, RvVo rvVo) {
		List<RvVo> List = rvService.findByCrnameAndCrprofessor(rvVo.getCrname(), rvVo.getCrprofessor());
		model.addAttribute("list", List);	//선택된 페이지에서 검색된 데이터만 List형태로 반환

		return "/reviewpage";
	}
}
