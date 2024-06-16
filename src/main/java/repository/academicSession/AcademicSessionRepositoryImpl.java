package repository.academicSession;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.Database;
import model.AcademicSession;

public class AcademicSessionRepositoryImpl implements AcademicSessionRepository {
	private Connection connection;

	public AcademicSessionRepositoryImpl() {
		// Connection object
		connection = Database.getConnection();
	}

	@Override
	public List<AcademicSession> getSessions() {
		List<AcademicSession> sessions = new ArrayList<AcademicSession>();

		try {
			String sql = "SELECT * FROM \"AcademicSession\"";

			Statement st = connection.createStatement();

			ResultSet resultSet = st.executeQuery(sql);

			while (resultSet.next()) {
				AcademicSession session = AcademicSession.fromResultSet(resultSet);

				sessions.add(session);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return sessions;
	}

}
