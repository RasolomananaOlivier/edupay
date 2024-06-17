package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;

public class MonthAmount {
	private Integer id;
	private Integer value;
	private Integer levelId;
	private Date createdAt;
	private Date updatedAt;
	private Level level;

	public MonthAmount() {
	}

	public MonthAmount(Integer id, Integer value, Integer levelId, Date createdAt, Date updatedAt) {
		this.id = id;
		this.value = value;
		this.levelId = levelId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
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

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public static MonthAmount fromResultSet(ResultSet resultSet) {
		MonthAmount equipment = new MonthAmount();

		try {
			equipment.setId(Integer.parseInt(resultSet.getString(1)));
			equipment.setValue(resultSet.getInt(2));
			equipment.setLevelId(resultSet.getInt(3));
			equipment.setCreatedAt(resultSet.getDate(4));
			equipment.setUpdatedAt(resultSet.getDate(5));

			equipment.setLevel(new Level(resultSet.getInt(6), resultSet.getString(7)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return equipment;
	}
	
	public static MonthAmount fromRequest(HttpServletRequest request) {
		MonthAmount equipment = new MonthAmount();

		try {
			//equipment.setId(Integer.parseInt(request.getParameter("amountId")));
			equipment.setValue(Integer.parseInt(request.getParameter("amountValue")));
			equipment.setLevelId(Integer.parseInt(request.getParameter("level")));
			
			Date now = new Date();
			equipment.setCreatedAt(now);
			equipment.setUpdatedAt(now);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return equipment;
	}
}
