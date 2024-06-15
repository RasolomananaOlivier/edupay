package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import util.Gender;

public class Student {
	private String id;
	private String name;
	private Gender gender;
	private Date birthDate;
	private String email;
	private Integer facultyId;
	private Integer levelId;
	private Integer academicSessionId;
	private Date createdAt;
	private Date updatedAt;
	private Level level;
	private AcademicSession session;
	private Faculty faculty;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(Integer facultyId) {
		this.facultyId = facultyId;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public Integer getAcademicSessionId() {
		return academicSessionId;
	}

	public void setAcademicSessionId(Integer academicSessionId) {
		this.academicSessionId = academicSessionId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
	public AcademicSession getSession() {
		return session;
	}

	public void setSession(AcademicSession session) {
		this.session = session;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public static Student formResultSet(ResultSet resultSet) {
		Student student = new Student();

		try {
			student.setId(resultSet.getString(1));
			student.setName(resultSet.getString(2));
			student.setGender(Gender.valueOf(resultSet.getString(3)));
			student.setBirthDate(resultSet.getDate(4));
			student.setEmail(resultSet.getString(5));
			student.setFacultyId(resultSet.getInt(6));
			student.setLevelId(resultSet.getInt(7));
			student.setAcademicSessionId(resultSet.getInt(8));
			student.setCreatedAt(resultSet.getDate(9));
			student.setUpdatedAt(resultSet.getDate(10));
			
			Level level = new Level(resultSet.getInt(11), resultSet.getString(12));
			student.setLevel(level);
			
			AcademicSession session = new AcademicSession(resultSet.getInt(13), resultSet.getInt(14));
			student.setSession(session);
			
			Faculty faculty = new Faculty(resultSet.getInt(15), resultSet.getString(16));
			student.setFaculty(faculty);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return student;
	}
}
