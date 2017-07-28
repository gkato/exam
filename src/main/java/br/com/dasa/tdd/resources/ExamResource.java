package br.com.dasa.tdd.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exams")
public class ExamResource {

	@RequestMapping(method=RequestMethod.GET)
	public String getExam(Long examId) {
		return null;
	}
}
