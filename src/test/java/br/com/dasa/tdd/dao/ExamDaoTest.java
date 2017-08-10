package br.com.dasa.tdd.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import br.com.dasa.tdd.dao.ExamDao.ExamMapper;
import br.com.dasa.tdd.dao.builders.ExamBuilder;
import br.com.dasa.tdd.model.Exam;

@RunWith(MockitoJUnitRunner.class)
public class ExamDaoTest {

	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	private ExamDao examDao;
	
	@Test
	public void get_exam_success() {
		Exam expected = new ExamBuilder().withId(1l).build();
	
		Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(ExamMapper.class))).thenReturn(expected);
		
		Exam exam = examDao.getExam(expected.getId());
		
		Assert.assertEquals(expected, exam);
		Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForObject(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(ExamMapper.class));
	}

	@Test
	public void get_exam_not_found() {
		Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(ExamMapper.class))).thenThrow(new EmptyResultDataAccessException(0));
		
		Exam exam = examDao.getExam(1l);
		
		Assert.assertNull(exam);
		Mockito.verify(jdbcTemplate, Mockito.times(1)).queryForObject(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(ExamMapper.class));
	}
	
	@Test(expected=RuntimeException.class)
	public void get_exam_error() {
		Mockito.doThrow(new RuntimeException()).when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(ExamMapper.class)));
		
		examDao.getExam(1l);
	}

	@Test
	public void create_exam_success() {
		Exam exam = buildSimpleExam();
		Mockito.when(jdbcTemplate.update(ExamDao.SQL_CREATE, exam.getName(), exam.getGender(), exam.getExamName())).thenReturn(1);

		examDao.create(exam);
		Mockito.verify(jdbcTemplate, Mockito.times(1)).update(ExamDao.SQL_CREATE, exam.getName(), exam.getGender(), exam.getExamName(), exam.getResult());
	}

	@Test(expected=RuntimeException.class)
	public void create_exam_error() {
		Exam exam = buildSimpleExam();
		Mockito.doThrow(new RuntimeException()).when(jdbcTemplate).update(ExamDao.SQL_CREATE, exam.getName(), exam.getGender(), exam.getExamName(), exam.getResult());

		examDao.create(exam);
	}
	
	private Exam buildSimpleExam() {
		return new ExamBuilder().withId(1l).withName("Teste").withGender("male").build();
	}
}
