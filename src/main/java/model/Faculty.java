package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Faculty {
	private Integer id;
	private String name;
	
	
	public Faculty() {

	}

	public Faculty(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static Faculty fromResultSet(ResultSet resultSet) {
		Faculty faculty = new Faculty();

		try {
			faculty.setId(Integer.parseInt(resultSet.getString(1)));
			faculty.setName(resultSet.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return faculty;
	}
}
