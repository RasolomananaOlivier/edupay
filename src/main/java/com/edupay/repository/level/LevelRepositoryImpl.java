package com.edupay.repository.level;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edupay.database.Database;
import com.edupay.model.Level;

public class LevelRepositoryImpl implements LevelRepository {
	private final Connection connection;

	public LevelRepositoryImpl() {
		// Connection object
		connection = Database.getConnection();
	}
	
	@Override
	public List<Level> getLevels() {
		List<Level> levels = new ArrayList<Level>();

		try {
			String sql = "SELECT * FROM \"Level\"";

			Statement st = connection.createStatement();

			ResultSet resultSet = st.executeQuery(sql);

			while (resultSet.next()) {
				Level level = Level.fromResultSet(resultSet);

				levels.add(level);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return levels;
	}

}
