package com.edupay.repository.equipmentAmount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edupay.database.Database;
import com.edupay.model.EquipmentAmount;

public class EquipmentAmountRepositoryImpl implements EquipmentAmountRepository {

	@Override
	public List<EquipmentAmount> getMonthAmounts() {
		try (Connection connection = Database.getConnection()) {

			String sql = "SELECT * FROM \"EquipmentAmount\" "
					+ "INNER JOIN \"Level\" ON \"Level\".\"id\" = \"EquipmentAmount\".\"levelId\"";

            assert connection != null;
            Statement st = connection.createStatement();

			ResultSet result = st.executeQuery(sql);

			List<EquipmentAmount> amounts = new ArrayList<EquipmentAmount>();

			while (result.next()) {
				amounts.add(EquipmentAmount.fromResultSet(result));
			}

			return amounts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public EquipmentAmount getMonthAmount(int id) {
		try (Connection connection = Database.getConnection()) {

			String sql = "SELECT * FROM \"EquipmentAmount\" "
					+ "INNER JOIN \"Level\" ON \"Level\".\"id\" = \"EquipmentAmount\".\"levelId\" "
					+ "WHERE \"EquipmentAmount\".\"id\" = ? ";

            assert connection != null;
            PreparedStatement st = connection.prepareStatement(sql);

			st.setInt(1, id);

			ResultSet result = st.executeQuery();

			EquipmentAmount amount = null;

			while (result.next()) {
				amount = EquipmentAmount.fromResultSet(result);
			}

			return amount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void addMonthAmount(EquipmentAmount amount) {
		try (Connection connection = Database.getConnection()) {

			String sql = "INSERT INTO \"EquipmentAmount\" "
					+ "(\"value\", \"levelId\", \"createdAt\", \"updatedAt\") "
					+ "VALUES (?, ?, ?, ?)";

            assert connection != null;
            PreparedStatement st = connection.prepareStatement(sql);

			// st.setInt(1, amount.getId());
			st.setInt(1, amount.getValue());
			st.setInt(2, amount.getLevelId());
			st.setTimestamp(3, new java.sql.Timestamp(amount.getCreatedAt().getTime()));
			st.setTimestamp(4, new java.sql.Timestamp(amount.getUpdatedAt().getTime()));

			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateMonthAmount(EquipmentAmount amount) {
		try (Connection connection = Database.getConnection()) {

			String sql = "UPDATE \"EquipmentAmount\" SET "
					+ "\"value\" = ?, "
					+ "\"levelId\" = ?, "
					+ "\"createdAt\" = ? "
					+ "WHERE \"id\" = ?";

            assert connection != null;
            PreparedStatement st = connection.prepareStatement(sql);

			st.setInt(1, amount.getValue());
			st.setInt(2, amount.getLevelId());
			st.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
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

			String sql = "DELETE FROM \"EquipmentAmount\" "
					+ "WHERE \"id\" = ?";

            assert connection != null;
            PreparedStatement st = connection.prepareStatement(sql);

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public EquipmentAmount getByLevelId(int levelId) {
		try (Connection connection = Database.getConnection()) {

			String sql = "SELECT * FROM \"EquipmentAmount\" "
					+ "INNER JOIN \"Level\" ON \"Level\".\"id\" = \"EquipmentAmount\".\"levelId\" "
					+ "WHERE \"EquipmentAmount\".\"levelId\" = ? ";

            assert connection != null;
            PreparedStatement st = connection.prepareStatement(sql);

			st.setInt(1, levelId);

			ResultSet result = st.executeQuery();

			EquipmentAmount amount = null;

			while (result.next()) {
				amount = EquipmentAmount.fromResultSet(result);
			}

			return amount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
