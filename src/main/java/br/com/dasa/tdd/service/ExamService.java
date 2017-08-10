package br.com.dasa.tdd.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dasa.tdd.dao.ExamDao;
import br.com.dasa.tdd.model.Exam;

@Service
public class ExamService {
	
	@Autowired
	private ExamDao examDao;

	public void createExam(Exam exam) {
		if(validatesExam(exam)) {
			throw new IllegalArgumentException("Exame com dados invalidos");
		}
		
		examDao.create(exam);
	}

	private boolean validatesExam(Exam exam) {
		return exam.getName() == null || exam.getGender() == null || !Arrays.asList("male", "female").contains(exam.getGender());
	}

	public Exam getExam(Long id) {
		if(id == null) {
			return null;
		}
		return examDao.getExam(id);
	}

}
