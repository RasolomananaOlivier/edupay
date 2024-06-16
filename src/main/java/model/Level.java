package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.Gender;

public class Level {
	private Integer id;
	private String name;
	
	public Level() {
	}

	public Level(Integer id, String name) {
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
	
	public static Level fromResultSet(ResultSet resultSet) {
		Level level = new Level();

		try {
			level.setId(Integer.parseInt(resultSet.getString(1)));
			level.setName(resultSet.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return level;
	}
}
