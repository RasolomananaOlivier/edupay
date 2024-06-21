package repository.paymentItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import database.Database;
import model.PaymentItem;

public class PaymentItemRepositoryImpl implements PaymentItemRepository {

	private Connection connection;

	public PaymentItemRepositoryImpl() {
		connection = Database.getConnection();
	}

	@Override
	public void addMany(List<PaymentItem> paymentItems) {
		String sql = """
				INSERT INTO "public"."PaymentItem" ("period", "paymentId", "amount")
				VALUES (CAST(?::text AS "public"."PaymentPeriod"), ?, ?)
				RETURNING "id"
				""";

		try (PreparedStatement st = connection.prepareStatement(sql)) {
			for (PaymentItem paymentItem : paymentItems) {
				st.setString(1, paymentItem.getPeriod().toString());
				st.setString(2, paymentItem.getPaymentId());
				st.setDouble(3, paymentItem.getAmount());
				st.addBatch();
			}

			st.executeBatch();
			try (ResultSet rs = st.getGeneratedKeys()) {
				int index = 0;
				while (rs.next()) {
					int id = rs.getInt(1);
					paymentItems.get(index++).setId(id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PaymentItem> getBySessionIdAndStudentId(int sessionId, String studentId) {
		ArrayList<PaymentItem> result = new ArrayList<>();

		String sql = """
				SELECT "public"."PaymentItem"."id" AS "PaymentItem.id",
				    "public"."PaymentItem"."paymentId" AS "PaymentItem.paymentId",
				    "public"."PaymentItem"."amount" AS "PaymentItem.amount",
				    "public"."PaymentItem"."period"::text AS "PaymentItem.period",
				    "public"."Payment"."studentId"
				FROM "public"."PaymentItem"
				    INNER JOIN "public"."Payment" ON "public"."PaymentItem"."paymentId" = "public"."Payment"."id"
				WHERE (
				        "public"."Payment"."academicSessionId" = ?
				        AND "public"."Payment"."studentId" = ?
				    )
				ORDER BY "public"."PaymentItem"."id" ASC;
				""";

		try {
			PreparedStatement st = connection.prepareStatement(sql);
			st.setInt(1, sessionId);
			st.setString(2, studentId);

			ResultSet resultSet = st.executeQuery();

			while (resultSet.next()) {
				PaymentItem paymentItem = PaymentItem.fromResultSet(resultSet);

				result.add(paymentItem);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void deleteMany(List<Integer> ids) {
		if (ids == null || ids.isEmpty()) {
			return; // No IDs to delete
		}

		// Build the SQL statement with the correct number of placeholders
		String placeholders = ids.stream().map(id -> "?").collect(Collectors.joining(", "));
		String sql = """
				DELETE FROM "public"."PaymentItem"
				WHERE "id" IN (%s)
				""".formatted(placeholders);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			// Set the parameters for the placeholders
			for (int i = 0; i < ids.size(); i++) {
				preparedStatement.setInt(i + 1, ids.get(i));
			}

			// Execute the statement
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle exception (log it, rethrow it, etc.)
		}
	}

}
