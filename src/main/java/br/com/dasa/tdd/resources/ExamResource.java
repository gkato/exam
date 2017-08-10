package br.com.dasa.tdd.resources;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.dasa.tdd.model.Exam;
import br.com.dasa.tdd.service.ExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/exams")
@Api(value="ExamApi")
public class ExamResource {

	@Autowired
	private ExamService examService; 
	
	@ApiOperation(value = "View an Exam", response = Response.class)
	@RequestMapping(value="/{examId}", method=RequestMethod.GET)
	public Response getExam(@PathVariable("examId") Long examId) {
		try {
			Exam exam = examService.getExam(examId);
			if(exam != null) {
				return Response.ok(exam).build();
			}
			return Response.status(Response.Status.NOT_FOUND).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		} 
	}

	@ApiOperation(value = "Create an Exam", response = Response.class)
	@RequestMapping(method=RequestMethod.PUT)
	public Response createExam(@RequestBody Exam exam) {
		try {
			examService.createExam(exam);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.status(Response.Status.CREATED).build();
	}
}
