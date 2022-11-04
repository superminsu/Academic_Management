package kr.inhatc.spring.cr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import kr.inhatc.spring.review.RvService;
import kr.inhatc.spring.review.RvVo;
import kr.inhatc.spring.user.entity.CourseVo;
import kr.inhatc.spring.user.service.CourseService;
import kr.inhatc.spring.user.utils.ScriptUtils;

@Controller
public class CrRegController {
	
	@Autowired
	private CrRegService crRegService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private RvService rvService;
	
	ScriptUtils scriptUtils;
	
	//수강신청
	@PostMapping("/signCrReg")
	public String signCrReg(HttpServletResponse response, HttpServletRequest request, CrRegVo crreg, RvVo review) throws IOException {
		if(crRegService.findByIdAndCrcode(crreg)) {
			scriptUtils.alertAndMovePage(response, "이미 신청된 강의입니다.", "/cr_registration");
		}
		else {
			if(courseService.crReg(crreg.getCrcode())) {
				scriptUtils.alertAndMovePage(response, "수강신청이 완료되었습니다.", "/cr_registration");
				crRegService.save(crreg);
				rvService.save(review);
			}
			else scriptUtils.alertAndMovePage(response, "수강인원이 초과되었습니다.", "/cr_registration");
		}	
		return "/cr_registration";
	}
	
	//검색 - 수강신청 개인리스트
	@GetMapping("/cr_registration")
	public String list(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "", value = "keyword") String keyword, @RequestParam(required = false, defaultValue = "", value = "searchType") String searchType, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
		Page<CourseVo> searchList = crRegService.list(keyword, searchType, page);	//불러올 페이지의 데이터 1페이지는 0부터 시작
		int totalPage = searchList.getTotalPages();	//총 페이지 수
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println("접속중인 ID: "+id);
		
		model.addAttribute("id", id);
		model.addAttribute("list", searchList.getContent());	//선택된 페이지에서 검색된 데이터만 List형태로 반환
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("keyword", keyword);					//교과목명 키워드
		model.addAttribute("searchType", searchType);			//이수구분 키워드
		return "/cr_registration";
	}
	
	//수강취소 개인리스트
	@GetMapping("/cr_cancle")
	public String cList(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String id =(String)session.getAttribute("id");
		System.out.println("접속중인 ID: "+id);
	  
		List<CrRegVo> crreg = crRegService.findById(id);
		String[] crcode = new String[20];
		
		for(int i=0; i < crreg.size(); i++) {
			crcode[i] = crreg.get(i).getCrcode();
			System.out.println(crcode[i]);
		}
	  
		List<CourseVo> list = courseService.seleteCrcode(crcode);
		
		model.addAttribute("id", id);
		model.addAttribute("list", list);
		  
		return "/cr_cancle";
	  }
	 
	
	//수강신청삭제
	@PostMapping("/deleteCrReg")
	public String deleteCrReg(HttpServletResponse response, String id, String crcode) throws IOException {
		if(courseService.crCancle(crcode)) {
			scriptUtils.alertAndMovePage(response, "해당 강의가 취소되었습니다.", "/cr_cancle");
			CrRegVo findCr = crRegService.findAppNo(id, crcode);
			crRegService.deleteCourse(findCr.getAppNo());
			rvService.deleteByIdAndCrcode(id, crcode);
		}
		
		return "redirect:/cr_cancle";
	}
	
	//개인시간표
	@GetMapping("/student_schedule")
	public String sList(HttpServletRequest request, Model model) throws IOException {
		HttpSession session = request.getSession();
		String id =(String)session.getAttribute("id");
		System.out.println("접속중인 ID: "+id);
	  
		List<CrRegVo> crreg = crRegService.findById(id);
		String[] crcode = new String[20];
		
		for(int i=0; i < crreg.size(); i++) {
			crcode[i] = crreg.get(i).getCrcode();
			System.out.println(crcode[i]);
		}
	  
		List<CourseVo> list = courseService.seleteCrcode(crcode);
		
		model.addAttribute("id", id);
		model.addAttribute("list", list);
		  
		return "/student_schedule";
	  }

	
	//수강신청조회
	@GetMapping("/cis_course")
	public String courseList(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String id =(String)session.getAttribute("id");
		System.out.println("접속중인 ID: "+id);
	  
		List<CrRegVo> crreg = crRegService.findById(id);
		String[] crcode = new String[20];
		
		for(int i=0; i < crreg.size(); i++) {
			crcode[i] = crreg.get(i).getCrcode();
			System.out.println(crcode[i]);
		}
	  
		List<CourseVo> list = courseService.seleteCrcode(crcode);
		
		model.addAttribute("id", id);
		model.addAttribute("list", list);
		  
		return "/cis_course";
}
}
