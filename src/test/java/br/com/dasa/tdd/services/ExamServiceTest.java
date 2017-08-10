package br.com.dasa.tdd.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.dasa.tdd.dao.ExamDao;
import br.com.dasa.tdd.dao.builders.ExamBuilder;
import br.com.dasa.tdd.model.Exam;
import br.com.dasa.tdd.service.ExamService;

@RunWith(MockitoJUnitRunner.class)
public class ExamServiceTest {
	
	@Mock
	private ExamDao examDao;
	
	@InjectMocks
	private ExamService examService;
	
	private Exam exam;
	
	@Before
	public void init() {
		exam = createSimpleExam();
	}
	
	@Test
	public void creates_exam_success() {
		Mockito.doNothing().when(examDao).create(exam);
		
		examService.createExam(exam);
		Mockito.verify(examDao, Mockito.times(1)).create(exam);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void creates_exam_invalid_name() {
		exam.setName(null);
		
		examService.createExam(exam);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void creates_exam_invalid_gender_when_null() {
		exam.setGender(null);
		
		examService.createExam(exam);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void creates_exam_invalid_gender_when_diff_domain() {
		exam.setGender("maleee");
		
		examService.createExam(exam);
	}
	
	@Test(expected=RuntimeException.class)
	public void creates_exam_error() {
		Mockito.doThrow(new RuntimeException()).when(examDao).create(exam);
		
		examService.createExam(exam);
	}
	
	@Test
	public void gets_exam_when_null() {
		Exam result = examService.getExam(null);
		
		Assert.assertNull(result);
		Mockito.verify(examDao, Mockito.never()).getExam(Mockito.any());
	}
	
	@Test
	public void gets_exam_success() {
		Mockito.when(examDao.getExam(exam.getId())).thenReturn(exam);
		
		Exam result = examService.getExam(exam.getId());
		
		Assert.assertEquals(exam, result);
		Mockito.verify(examDao, Mockito.times(1)).getExam(Mockito.any());
	}
	
	@Test
	public void gets_exam_not_found() {
		Mockito.when(examDao.getExam(exam.getId())).thenReturn(null);
		
		Exam result = examService.getExam(exam.getId());
		
		Assert.assertNull(result);
		Mockito.verify(examDao, Mockito.times(1)).getExam(Mockito.any());
	}
	
	@Test(expected=RuntimeException.class)
	public void gets_exam_error() {
		Mockito.when(examDao.getExam(exam.getId())).thenThrow(new RuntimeException());
		
		examService.getExam(exam.getId());
	}
	
	private Exam createSimpleExam() {
		return new ExamBuilder().withId(1l).withExamName("Test").withName("Tester").withGender("male").build();
	}
}
