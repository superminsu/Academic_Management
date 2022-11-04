package kr.inhatc.spring.cis.notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.user.service.Notice;

@Service
public class cisNoticeService {
	@PostConstruct
	public List<Notice> getnotice() throws IOException{
		String noticeURL = "https://www.inhatc.ac.kr/kr/460/subview.do";
		Document noticeDoc = Jsoup.connect(noticeURL).get();
		List<Notice> noticeList = new ArrayList<>();
		Elements contents = noticeDoc.select("table tbody tr");
		for(Element content : contents) {
			Notice notice = Notice.builder()
					.subject(content.select("a").text())
					.date(content.select(".td-date").text())
					.build();
			noticeList.add(notice);
		}
		return noticeList;
	}
}
