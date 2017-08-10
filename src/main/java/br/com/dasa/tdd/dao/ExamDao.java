package br.com.dasa.tdd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.com.dasa.tdd.model.Exam;

@Repository
@Produces("application/json")
public class ExamDao {

	public static String SQL_GET = "select * from exams where id = ?";
	public static String SQL_CREATE = "insert into exams (name, gender, exam_name, result) values (?,?,?,?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Exam getExam(Long id) {
		try {
			return jdbcTemplate.queryForObject(SQL_GET, new Object[] {id}, new ExamMapper());
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void create(Exam exam) {
		jdbcTemplate.update(SQL_CREATE, exam.getName(), exam.getGender(), exam.getExamName(), exam.getResult());
	}
	
	public class ExamMapper implements RowMapper<Exam> {
		@Override
		public Exam mapRow(ResultSet rs, int index) throws SQLException {
			Exam exam = new Exam();
			exam.setId(rs.getLong("id"));
			exam.setName(rs.getString("name"));
			exam.setGender(rs.getString("gender"));
			
			exam.setExamName(rs.getString("exam_name"));
			exam.setResult(rs.getString("result"));
			return exam;
		}
	}

}
