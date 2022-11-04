package kr.inhatc.spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AcademicmanagementController {
	
	@GetMapping("cis_main")
	public String hello() {
		return "cis_main";
	}
	@GetMapping("cis_schedule")
	public String schedule() {
		return "cis_schedule";
	}
	@GetMapping("cr_main")
	public String cr_main() {
		return "cr_main";
	}
	@GetMapping("login_cis")
	public String login_cis() {
		return "login_cis";
	}
	@GetMapping("login_cr")
	public String login_cr() {
		return "login_cr";
	}
	@GetMapping("login_mg")
	public String login_mg() {
		return "login_mg";
	}
	@GetMapping("admin_main")
	public String admin_main() {
		return "admin_main";
	}
	@GetMapping("admin_usercreate")
	public String admin_usercreate() {
		return "admin_usercreate";
	}
}
