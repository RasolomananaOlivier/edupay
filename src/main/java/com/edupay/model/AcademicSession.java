package com.edupay.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AcademicSession {
	private Integer id;
	private Integer year;
	

	public AcademicSession() {
	}

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
	
	public static AcademicSession fromResultSet(ResultSet resultSet) {
		AcademicSession session = new AcademicSession();

		try {
			session.setId(Integer.parseInt(resultSet.getString(1)));
			session.setYear(Integer.parseInt(resultSet.getString(2)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return session;
	}

}
