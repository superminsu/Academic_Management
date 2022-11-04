package kr.inhatc.spring.transcript;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TranscriptService {
	@Autowired
	private TranscriptRepository transcriptRepository;
	
	public List<TranscriptVo> findById(String id) {
		List<TranscriptVo> info = transcriptRepository.findById(id);
		return info;
	}
	public List<TranscriptVo> findByIdAndYgt(String id, String ygt) {
		List<TranscriptVo> info = transcriptRepository.findByIdAndYgt(id, ygt);
		return info;
	}
	public void deleteById(String id) {
		transcriptRepository.deleteById(id);
	}
}
