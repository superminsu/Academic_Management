package kr.inhatc.spring.transcript;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.inhatc.spring.Privacy.PrVo;

@Controller
public class TranscriptController {
	@Autowired
	TranscriptService transcriptService;
	
	@GetMapping("/cis_reportcard")
	public String reportcard(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		List<TranscriptVo> list = transcriptService.findById(id);
		
		List<String> l = new ArrayList<>();			//중복제거를 위해
		for(int i=0 ; i<list.size(); i++) {
			l.add(list.get(i).getYgt());
		}
		HashSet<String> h = new HashSet<String>(l); //중복제거
		model.addAttribute("yeargradeterm", h);
		model.addAttribute("transcript", list);
		
		int credit = 0; //신청, 취득
		int ge = 0; //교양
		int major = 0; //전공
		double allscore = 0; //총점
		double GPA = 0;
		double cScore = 0;
		double PASS = 0;
		DecimalFormat df1 = new DecimalFormat("0.0");
		DecimalFormat df2 = new DecimalFormat("0.00");
		
		for(int i=0 ; i<list.size(); i++) {
			credit = credit + list.get(i).getCredit();		//신청, 취득
			if(list.get(i).getDivision().equals("교양")) {	//교양
				ge = ge + list.get(i).getCredit();
				if(list.get(i).getGrade().equals("P")) {	//PASS과목은 성적계산에서 제외
					PASS = PASS + 2;
				}else {										//성적 계산
					allscore = allscore + 2*list.get(i).getScore();
				}
			}else { 										//전공이론, 전공실습, 전공필수
				major = major + list.get(i).getCredit();
				allscore = allscore + 3*list.get(i).getScore();
			}
			model.addAttribute("GE", ge);
			model.addAttribute("MAJOR", major);
			model.addAttribute("Credit", credit);
		}
		GPA = allscore/(credit-PASS);						//총점 나누기 이수학점으로 평점평균을 구함(PASS과목은 계산에서 제외)
		cScore = GPA/4.5*100;
		String allscore_s = df1.format(allscore);
		String GPA_s = df2.format(GPA);
		String cScore_s = df1.format(cScore);
		model.addAttribute("ALL", allscore_s);
		model.addAttribute("GPA", GPA_s);
		model.addAttribute("CSCORE", cScore_s);
		
		return "/cis_reportcard";
	}
	@PostMapping("/s_transcript")
	public String searchtranscript(HttpServletRequest request, Model model) throws Exception{
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String ygt = request.getParameter("ygt");
		
		List<TranscriptVo> y = transcriptService.findById(id);
		List<String> l = new ArrayList<>();
		for(int i=0 ; i<y.size(); i++) {
			l.add(y.get(i).getYgt());
		}
		HashSet<String> h = new HashSet<String>(l); //중복제거
		model.addAttribute("yeargradeterm", h);
		
		if(ygt.equals("전체")) {
			List<TranscriptVo> list = transcriptService.findById(id);
			model.addAttribute("transcript", list);
			
			int credit = 0; //신청, 취득
			int ge = 0; //교양
			int major = 0; //전공
			double allscore = 0; //총점
			double GPA = 0;
			double cScore = 0;
			double PASS = 0;
			DecimalFormat df1 = new DecimalFormat("0.0");
			DecimalFormat df2 = new DecimalFormat("0.00");
			
			for(int i=0 ; i<list.size(); i++) {
				credit = credit + list.get(i).getCredit();		//신청, 취득
				if(list.get(i).getDivision().equals("교양")) {	//교양
					ge = ge + list.get(i).getCredit();
					if(list.get(i).getGrade().equals("P")) {	//PASS과목은 성적계산에서 제외
						PASS = PASS + 2;
					}else {										//성적 계산
						allscore = allscore + 2*list.get(i).getScore();
					}
				}else { 										//전공이론, 전공실습
					major = major + list.get(i).getCredit();
					allscore = allscore + 3*list.get(i).getScore();
				}
				model.addAttribute("GE", ge);
				model.addAttribute("MAJOR", major);
				model.addAttribute("Credit", credit);
			}
			GPA = allscore/(credit-PASS);						//총점 나누기 이수학점으로 평점평균을 구함(PASS과목은 계산에서 제외)
			cScore = GPA/4.5*100;
			String allscore_s = df1.format(allscore);
			String GPA_s = df2.format(GPA);
			String cScore_s = df1.format(cScore);
			model.addAttribute("ALL", allscore_s);
			model.addAttribute("GPA", GPA_s);
			model.addAttribute("CSCORE", cScore_s);
			
			return "/cis_reportcard";
		}else {
			List<TranscriptVo> list = transcriptService.findByIdAndYgt(id, ygt);
			model.addAttribute("transcript", list);
			
			int credit = 0; //신청, 취득
			int ge = 0; //교양
			int major = 0; //전공
			double allscore = 0; //총점
			double GPA = 0;
			double cScore = 0;
			double PASS = 0;
			DecimalFormat df1 = new DecimalFormat("0.0");
			DecimalFormat df2 = new DecimalFormat("0.00");
			
			for(int i=0 ; i<list.size(); i++) {
				credit = credit + list.get(i).getCredit();		//신청, 취득
				if(list.get(i).getDivision().equals("교양")) {	//교양
					ge = ge + list.get(i).getCredit();
					if(list.get(i).getGrade().equals("P")) {	//PASS과목은 성적계산에서 제외
						PASS = PASS + 2;
					}else {										//성적 계산
						allscore = allscore + 2*list.get(i).getScore();
					}
				}else { 										//전공이론, 전공실습
					major = major + list.get(i).getCredit();
					allscore = allscore + 3*list.get(i).getScore();
				}
				model.addAttribute("GE", ge);
				model.addAttribute("MAJOR", major);
				model.addAttribute("Credit", credit);
			}
			GPA = allscore/(credit-PASS);						//총점 나누기 이수학점으로 평점평균을 구함(PASS과목은 계산에서 제외)
			cScore = GPA/4.5*100;
			String allscore_s = df1.format(allscore);
			String GPA_s = df2.format(GPA);
			String cScore_s = df1.format(cScore);
			model.addAttribute("ALL", allscore_s);
			model.addAttribute("GPA", GPA_s);
			model.addAttribute("CSCORE", cScore_s);
			
			System.out.println(ygt);
			return "/cis_reportcard";
		}
	}
}
