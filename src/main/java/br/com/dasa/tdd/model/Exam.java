package br.com.dasa.tdd.model;

public class Exam {

	private Long   id;
	private String name;
	private String gender;
	
	private String exameName;
	private String result;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getExameName() {
		return exameName;
	}
	public void setExameName(String exameName) {
		this.exameName = exameName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
