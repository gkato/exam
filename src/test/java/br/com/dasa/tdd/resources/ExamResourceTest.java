package br.com.dasa.tdd.resources;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.dasa.tdd.dao.builders.ExamBuilder;
import br.com.dasa.tdd.model.Exam;
import br.com.dasa.tdd.service.ExamService;

@RunWith(MockitoJUnitRunner.class)
public class ExamResourceTest {

	@Mock
	private ExamService service;
	
	@InjectMocks
	private ExamResource resource;
	
	private Exam exam;
	
	@Before
	public void init() {
		exam = createSimpleExam();
	}
	
	@Test
	public void creates_exam_success() {
		Mockito.doNothing().when(service).createExam(exam);
		
		Response response = resource.createExam(exam);
		
		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		Mockito.verify(service, Mockito.times(1)).createExam(exam);
	}
	
	@Test
	public void creates_exam_error() {
		Mockito.doThrow(new RuntimeException()).when(service).createExam(exam);
		
		Response response = resource.createExam(exam);
		
		Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
		Mockito.verify(service, Mockito.times(0)).getExam(exam.getId());
	}
	
	@Test
	public void creates_exam_invalid_gender() {
		exam.setGender("maleee");
		Mockito.doThrow(new IllegalArgumentException()).when(service).createExam(exam);
		
		Response response = resource.createExam(exam);
		
		Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		Mockito.verify(service, Mockito.times(0)).getExam(exam.getId());
	}
	
	@Test
	public void gets_exam_success() {
		Mockito.when(service.getExam(exam.getId())).thenReturn(exam);
		
		Response response = resource.getExam(exam.getId());
		
		Exam result = (Exam)response.getEntity();
		Assert.assertEquals(exam, result);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		Mockito.verify(service, Mockito.times(1)).getExam(exam.getId());
	}
	
	@Test
	public void gets_exam_not_found() {
		Mockito.when(service.getExam(exam.getId())).thenReturn(null);
		
		Response response = resource.getExam(exam.getId());
		
		Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void gets_exam_error() {
		Mockito.when(service.getExam(exam.getId())).thenThrow(new RuntimeException());
		
		Response response = resource.getExam(exam.getId());
		
		Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	private Exam createSimpleExam() {
		return new ExamBuilder().withId(1l).withExamName("Test").withName("Tester").withGender("male").build();
	}
}
