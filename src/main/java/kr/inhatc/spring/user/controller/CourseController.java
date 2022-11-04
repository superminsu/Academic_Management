package kr.inhatc.spring.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.inhatc.spring.cr.service.CrRegService;
import kr.inhatc.spring.user.entity.CourseVo;
import kr.inhatc.spring.user.service.CourseService;
import kr.inhatc.spring.user.utils.ScriptUtils;

@Controller
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	@Autowired
	private CrRegService crRegService;
	
	ScriptUtils scriptUtils;
	
	//강의등록
	@PostMapping("/signCourse")
	public String signCourse(HttpServletResponse response, CourseVo course) throws IOException {
		if(courseService.findByCode(course)) {
			scriptUtils.alertAndMovePage(response, "중복된 코드입니다!", "/admin_course");
		}
		else courseService.save(course);
		return "redirect:/admin_course";
	}
	
	//검색 - 삭제 회원리스트
	@GetMapping("/admin_course")
	public String list(Model model, @RequestParam(required = false, defaultValue = "", value = "keyword") String keyword, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
		Page<CourseVo> searchList = courseService.list(keyword, page);	//불러올 페이지의 데이터 1페이지는 0부터 시작
		int totalPage = searchList.getTotalPages();	//총 페이지 수
		model.addAttribute("list", searchList.getContent());	//선택된 페이지에서 검색된 데이터만 List형태로 반환
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("keyword", keyword);					//검색 키워드
		return "/admin_course";
	}
	
	//강의삭제(한번에 선택해서 여러번 삭제 가능 Long[])
	@PostMapping("/deleteCourse")
	public String deleteMember(Model model, @RequestParam() String[] deleteId) throws Exception {
		try {
			courseService.deleteAll(deleteId);
			crRegService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/admin_course";
	}
}
