package kr.inhatc.spring.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.inhatc.spring.Privacy.PrService;
import kr.inhatc.spring.Privacy.PrVo;
import kr.inhatc.spring.personalSchedule.ScheduleService;
import kr.inhatc.spring.personalSchedule.ScheduleVo;
import kr.inhatc.spring.transcript.TranscriptService;
import kr.inhatc.spring.user.constants.SessionConstants;
import kr.inhatc.spring.user.dto.MemberDTO;
import kr.inhatc.spring.user.entity.MemberVo;
import kr.inhatc.spring.user.repository.MemberRepository;
import kr.inhatc.spring.user.service.MemberService;
import kr.inhatc.spring.user.utils.ScriptUtils;

@Controller
public class MemberController {

	// ?????????(??????????????????)
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MemberService memberService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private PrService prService;
	@Autowired
	private TranscriptService transcriptService;

	ScriptUtils scriptUtils;

	// ????????? ??????
	@PostMapping("/signInCis")
	public String signInCis(HttpServletRequest request, HttpServletResponse response, MemberVo member)
			throws IOException {
		if (memberService.login(member)) {
			HttpSession session = request.getSession();
			session.setAttribute("id", member.getId());
			return "redirect:/cis_main";
		}
		scriptUtils.alertAndMovePage(response, "????????? ?????? ??????????????? ???????????????!!", "/login_cis");
		return "/login_cis";
	}

	// ????????? ??????
	@PostMapping("/signInCr")
	public String signInCr(HttpServletRequest request,HttpServletResponse response, MemberVo member) throws IOException {
		if (memberService.login(member)) {
			HttpSession session = request.getSession();
			session.setAttribute("id", member.getId());
			return "redirect:/cr_main";
		}
		scriptUtils.alertAndMovePage(response, "????????? ?????? ??????????????? ???????????????!!", "/login_cr");
		return "/login_cr";
	}

	// ????????????
	@PostMapping("/signUp")
	public String joinMember(HttpServletResponse response, MemberVo member, PrVo privacy) throws IOException {
		if(memberService.findById(member)) {
			scriptUtils.alertAndMovePage(response, "????????? ??????????????????!", "/admin_usercreate");
		}
		else {
			memberService.save(member);
			prService.save(privacy);
		}
		return "/admin_usercreate";	
	}

	// ????????????
	@PostMapping("/logoutbtn")
	public String logout(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate(); // ?????? ??????
		}

		return "redirect:/";
	}
	
	//?????? - ?????? ???????????????
	@GetMapping("/admin_userdelete")
	public String list(Model model, @RequestParam(required = false, defaultValue = "", value = "keyword") String keyword, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
		Page<MemberVo> searchList = memberService.list(keyword, page);	//????????? ???????????? ????????? 1???????????? 0?????? ??????
		int totalPage = searchList.getTotalPages();	//??? ????????? ???
		model.addAttribute("list", searchList.getContent());	//????????? ??????????????? ????????? ???????????? List????????? ??????
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("keyword", keyword);					//?????? ?????????
		return "/admin_userdelete";
	}
	
	//????????????(????????? ???????????? ????????? ?????? ?????? Long[])
	@PostMapping("/deleteMember")
	public String deleteMember(Model model, @RequestParam() Long[] deleteId, String id) throws Exception {
		try {
			memberService.deleteAll(deleteId);
			scheduleService.deleteById(id);
			prService.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/admin_userdelete";
	}
	
	//????????????(????????????????????? ??????)
	@PostMapping("/detailDelete")
	public String detailDelete(Long mbrNo, String id) throws Exception {
		try {
			memberService.deleteById(mbrNo);
			scheduleService.deleteById(id);
			prService.deleteById(id);
			transcriptService.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/admin_userdelete";
	}
	
	//?????? ???????????????
	@GetMapping("/detailMember/{mbrNo}")
	public String detailMember(@PathVariable("mbrNo") Long mbrNo, Model model) {
		MemberVo member = memberService.getDetail(mbrNo);
		model.addAttribute("member", member);
		return "/admin_userupdate";
	}
	
	//?????? ??????
	@PostMapping("/updateMember")
	public String updateMember(MemberVo member) {
		memberService.updateById(member.getMbrNo(), member);
		return "redirect:/admin_userdelete";
	}
	
	//????????? ?????????
	@PostMapping("/signInMg")
	public String signInMg(HttpServletResponse response, String pw) throws IOException {
		if(pw.equals("1234")) {	//????????? ????????????
			return "redirect:/admin_main";
		}
		scriptUtils.alertAndMovePage(response, "??????????????? ???????????????!!", "/login_mg");
		return "/login_mg";
	}
}
