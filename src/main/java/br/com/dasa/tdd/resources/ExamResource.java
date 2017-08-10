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

@RestController
@RequestMapping(value="/exams")
@Api(value="ExamApi")
public class ExamResource {

	@Autowired
	private ExamService examService; 
	
	@RequestMapping(value="/{examId}", method=RequestMethod.GET)
	public Response getExam(@PathVariable("examId") Long examId) {
		try {
			Exam exam = examService.getExam(examId);
			if(exam != null) {
				return Response.status(Response.Status.OK).entity(exam).build();
			}
			return Response.status(Response.Status.NOT_FOUND).entity("Exame nao encontrado").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Erro ao buscar exame").build();
		} 
	}

	@RequestMapping(method=RequestMethod.PUT)
	public Response createExam(@RequestBody Exam exam) {
		try {
			examService.createExam(exam);
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Dados do Exame inválidos").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.status(Response.Status.CREATED).entity("Erro ao criar exame").build();
	}
}
