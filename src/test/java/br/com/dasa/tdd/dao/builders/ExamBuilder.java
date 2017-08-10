package br.com.dasa.tdd.dao.builders;

import br.com.dasa.tdd.model.Exam;

public class ExamBuilder {

	private Exam exam;
	
	public ExamBuilder() {
		exam = new Exam();
	}

	public ExamBuilder withId(Long id) {
		this.exam.setId(id);
		return this;
	}

	public Exam build() {
		return exam;
	}

	public ExamBuilder withName(String name) {
		this.exam.setName(name);
		return this;
	}

	public ExamBuilder withGender(String gender) {
		this.exam.setGender(gender);
		return this;
	}
	
	public ExamBuilder withExamName(String examName) {
		this.exam.setExamName(examName);
		return this;
	}
}
