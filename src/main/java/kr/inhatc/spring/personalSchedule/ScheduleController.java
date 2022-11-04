package kr.inhatc.spring.personalSchedule;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.inhatc.spring.user.entity.MemberVo;
import kr.inhatc.spring.user.utils.ScriptUtils;

@Controller
public class ScheduleController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ScheduleService scheduleService;
	ScriptUtils scriptUtils;
	
	@GetMapping("/cis_personal_calender")
	public String list(HttpServletRequest request, Model model) throws IOException{
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println("접속중인 ID: "+id);
		
		List<ScheduleVo> list_1 = scheduleService.findByIdAndTerm(id,"1");
		List<ScheduleVo> list_2 = scheduleService.findByIdAndTerm(id,"2");
		
		model.addAttribute("list_1", list_1);
		model.addAttribute("list_2", list_2);
		
		return "/cis_personal_calender";
	}
	@PostMapping("/cschedule")
	public String CreateSchedule(HttpServletResponse response, ScheduleVo schedule) throws IOException {
		if(schedule.getTerm().equals("학기 선택")) {
			scriptUtils.alertAndMovePage(response, "학기를 선택해주세요.", "/cis_personal_calender");
		}else {
			if(schedule.getEvent().equals("")) {
				scriptUtils.alertAndMovePage(response, "행사를 입력하세요.", "/cis_personal_calender");
			}else {
				if(schedule.getEdate().equals("")) {
					scriptUtils.alertAndMovePage(response, "날짜를 입력하세요.", "/cis_personal_calender");
				}else {
					scheduleService.save(schedule);
				}
			}
		}
		
		return "redirect:/cis_personal_calender";
	}
	@PostMapping("/deleteSchedule")
	public String deleteSchedule(Model model, @RequestParam() Long[] deleteId) throws Exception {
		try {
			scheduleService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "redirect:/cis_personal_calender";
	}
	
}
