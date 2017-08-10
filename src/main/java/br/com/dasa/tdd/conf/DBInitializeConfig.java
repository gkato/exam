package br.com.dasa.tdd.conf;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBInitializeConfig {

	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void initialize(){
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute("DROP TABLE IF EXISTS exams");
			statement.executeUpdate(
					"CREATE TABLE exams(" +
					"id INTEGER Primary key, " +
					"name varchar(30) ," +
					"gender varchar(10) ," +
					"exam_name varchar(130) , " +
					"result varchar(150))" 
					);
			statement.executeUpdate(
					"INSERT INTO exams " +
					"(name,gender,exam_name,result) " +
					"VALUES " + "('kato','male','Hemograma','150ml')"
					);
			statement.close();
			connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
