package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.Gender;

public class Level {
	public Level(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	private Integer id;
	private String name;

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
}
