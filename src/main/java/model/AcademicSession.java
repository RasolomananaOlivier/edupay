package model;

public class AcademicSession {
	private Integer id;
	private Integer year;

	public AcademicSession(Integer id, Integer year) {
		this.id = id;
		this.year = year;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
