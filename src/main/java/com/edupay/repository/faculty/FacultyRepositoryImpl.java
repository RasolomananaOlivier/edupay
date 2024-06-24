package com.edupay.repository.faculty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edupay.database.Database;
import com.edupay.model.Faculty;

public class FacultyRepositoryImpl implements FacultyRepository {
	private final Connection connection;

	public FacultyRepositoryImpl() {
		// Connection object
		connection = Database.getConnection();
	}

	@Override
	public List<Faculty> getFaculties() {
		List<Faculty> faculties = new ArrayList<Faculty>();

		try {
			String sql = "SELECT * FROM \"Faculty\"";

			Statement st = connection.createStatement();

			ResultSet resultSet = st.executeQuery(sql);

			while (resultSet.next()) {
				Faculty faculty = Faculty.fromResultSet(resultSet);

				faculties.add(faculty);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return faculties;
	}

}
