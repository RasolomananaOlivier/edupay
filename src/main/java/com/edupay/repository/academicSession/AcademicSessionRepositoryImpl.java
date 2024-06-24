package com.edupay.repository.academicSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edupay.database.Database;
import com.edupay.model.AcademicSession;

public class AcademicSessionRepositoryImpl implements AcademicSessionRepository {
	private final Connection connection;

	public AcademicSessionRepositoryImpl() {
		// Connection object
		connection = Database.getConnection();
	}

	@Override
	public List<AcademicSession> getSessions() {
		List<AcademicSession> sessions = new ArrayList<>();

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

	@Override
	public AcademicSession getLatest() {
		AcademicSession result = null;

		try {
			String sql = """
						SELECT * FROM "AcademicSession"
						ORDER BY "public"."AcademicSession"."id" DESC
						LIMIT 1
					""";

			Statement st = connection.createStatement();

			ResultSet resultSet = st.executeQuery(sql);

			while (resultSet.next()) {
                result = AcademicSession.fromResultSet(resultSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public AcademicSession getById(Integer id) {
		AcademicSession result = null;

		try {
			String sql = """
						SELECT * FROM "AcademicSession"
						WHERE "public"."AcademicSession"."id" = ?
					""";

			PreparedStatement st = connection.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet resultSet = st.executeQuery();

			while (resultSet.next()) {
                result = AcademicSession.fromResultSet(resultSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
