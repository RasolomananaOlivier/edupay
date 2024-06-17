package repository.amount;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.Database;
import model.MonthAmount;

public class AmountRepositoryImpl implements AmountRepository {

	@Override
	public List<MonthAmount> getMonthAmounts() {
		try (Connection connection = Database.getConnection()) {

			String sql = "SELECT * FROM \"MonthAmount\" "
					+ "INNER JOIN \"Level\" ON \"Level\".\"id\" = \"MonthAmount\".\"levelId\"";

			Statement st = connection.createStatement();

			ResultSet result = st.executeQuery(sql);

			List<MonthAmount> amounts = new ArrayList<MonthAmount>();

			while (result.next()) {
				amounts.add(MonthAmount.fromResultSet(result));
			}

			return amounts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public MonthAmount getMonthAmount(int id) {
		try (Connection connection = Database.getConnection()) {

			String sql = "SELECT * FROM \"MonthAmount\" "
					+ "INNER JOIN \"Level\" ON \"Level\".\"id\" = \"MonthAmount\".\"levelId\" "
					+ "WHERE \"MonthAmount\".\"id\" = ? ";

			PreparedStatement st = connection.prepareStatement(sql);

			st.setInt(1, id);

			ResultSet result = st.executeQuery();

			MonthAmount amount = null;

			while (result.next()) {
				amount = MonthAmount.fromResultSet(result);
			}

			return amount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void addMonthAmount(MonthAmount amount) {
		try (Connection connection = Database.getConnection()) {

			String sql = "INSERT INTO \"MonthAmount\" "
					+ "(\"value\", \"levelId\", \"createdAt\", \"updatedAt\") "
					+ "VALUES (?, ?, ?, ?)";

			PreparedStatement st = connection.prepareStatement(sql);

			//st.setInt(1, amount.getId());
			st.setInt(1, amount.getValue());
			st.setInt(2, amount.getLevelId());
			st.setDate(3, new Date(amount.getCreatedAt().getTime()));
			st.setDate(4, new Date(amount.getUpdatedAt().getTime()));

			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateMonthAmount(MonthAmount amount) {
		try (Connection connection = Database.getConnection()) {
			
			String sql = "UPDATE \"MonthAmount\" SET "
					+ "\"value\" = ?, "
					+ "\"levelId\" = ?, "
					+ "\"createdAt\" = ? "
					+ "WHERE \"id\" = ?";
					
			PreparedStatement st = connection.prepareStatement(sql);

			st.setInt(1, amount.getValue());
			st.setInt(2, amount.getLevelId());
			st.setDate(3, new Date(System.currentTimeMillis()));
			st.setInt(4, amount.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMonthAmount(int id) {
		try (Connection connection = Database.getConnection()) {

			String sql = "DELETE FROM \"MonthAmount\" "
					+ "WHERE \"id\" = ?";

			PreparedStatement st = connection.prepareStatement(sql);

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
