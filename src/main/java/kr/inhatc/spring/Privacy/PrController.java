package kr.inhatc.spring.Privacy;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.inhatc.spring.Privacy.PrService;
import kr.inhatc.spring.personalSchedule.ScheduleVo;
import kr.inhatc.spring.user.entity.MemberVo;

@Controller
public class PrController {
	@Autowired
	private PrService prService;
	
	@GetMapping("/cis_myinfo")
	public String Privacy(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println("접속중인 ID: "+id);
		
		List<PrVo> list = prService.findById(id);
		model.addAttribute("info", list);
		
		return "/cis_myinfo";
	}
	@GetMapping("/cis_faminfo")
	public String Fam(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println("접속중인 ID: "+id);
		
		List<PrVo> list = prService.findById(id);
		model.addAttribute("info", list);
		return "/cis_faminfo";
	}
	@GetMapping("/cis_accinfo")
	public String Acc(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println("접속중인 ID: "+id);
		
		List<PrVo> list = prService.findById(id);
		model.addAttribute("info", list);
		return "/cis_accinfo";
	}
	@PostMapping("/myinfo")
	public String CreateMyinfo(PrVo prvo) throws IOException {
		prService.updateById(prvo.getPNo(), prvo);
		
		return "redirect:/cis_myinfo";
	}
	@PostMapping("/faminfo")
	public String CreateFaminfo(PrVo prvo) throws IOException {
		prService.updateById(prvo.getPNo(), prvo);
		
		return "redirect:/cis_faminfo";
	}
	@PostMapping("/accinfo")
	public String CreateAccinfo(PrVo prvo) throws IOException {
		prService.updateById(prvo.getPNo(), prvo);
		
		return "redirect:/cis_accinfo";
	}
}
