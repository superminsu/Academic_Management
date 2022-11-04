package kr.inhatc.spring.cis.notice;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.inhatc.spring.user.service.Notice;

@Controller
public class cisNoticeController {
	@Autowired
	cisNoticeService cns;
	
	@GetMapping("/cis_notice")
	public String cnCrawling(Model model) throws IOException {
		 List<Notice> noticeList = cns.getnotice();
	     model.addAttribute("noticestat", noticeList);
	     model.addAttribute("link1", "https://www.inhatc.ac.kr/kr/460/subview.do");
	     return "/cis_notice";
	}
}
