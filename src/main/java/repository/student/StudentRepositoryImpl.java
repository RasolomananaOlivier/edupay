/**
 * 
 */
package repository.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import database.Database;
import model.Student;
import util.PaymentPeriod;

/**
 * 
 */
public class StudentRepositoryImpl implements StudentRepository {
	private Connection connection;

	public StudentRepositoryImpl() {
		// Connection object
		connection = Database.getConnection();
	}

	@Override
	public void addStudent(Student student) {
		try {
			String sql = "INSERT INTO \"Student\" "
					+ "(\"id\", \"name\", \"gender\", \"birthDate\", \"email\", \"facultyId\", \"levelId\", \"academicSessionId\", \"createdAt\", \"updatedAt\") "
					+ "VALUES (?, ?, CAST(?::text AS \"public\".\"Gender\"), ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, student.getId());
			preparedStatement.setString(2, student.getName());
			preparedStatement.setString(3, student.getGender().toString());
			// Convert java.util.Date to java.sql.Date
			preparedStatement.setTimestamp(4, new java.sql.Timestamp(student.getBirthDate().getTime()));
			preparedStatement.setString(5, student.getEmail());
			preparedStatement.setInt(6, student.getFacultyId());
			preparedStatement.setInt(7, student.getLevelId());
			preparedStatement.setInt(8, student.getAcademicSessionId());
			preparedStatement.setTimestamp(9, new java.sql.Timestamp(student.getCreatedAt().getTime()));
			preparedStatement.setTimestamp(10, new java.sql.Timestamp(student.getUpdatedAt().getTime()));

			preparedStatement.executeUpdate();

			System.out.println("student inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Student getStudent(String id) {
		Student student = null;

		try {
			String sql = "SELECT * FROM \"Student\""
					+ "INNER JOIN \"Level\" ON \"Student\".\"levelId\" = \"Level\".\"id\" "
					+ "INNER JOIN \"AcademicSession\" ON \"Student\".\"academicSessionId\" = \"AcademicSession\".\"id\" "
					+ "INNER JOIN \"Faculty\" ON \"Student\".\"facultyId\" = \"Faculty\".\"id\" "
					+ "WHERE \"Student\".\"id\" = ? ";

			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, id);

			ResultSet resultSet = st.executeQuery();

			while (resultSet.next()) {
				student = Student.fromResultSet(resultSet);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return student;
	}

	@Override
	public List<Student> getAllStudents(String name, Boolean isMinor, Integer levelId, Integer facultyId) {
		List<Student> students = new ArrayList<>();

		StringBuilder sql = new StringBuilder("SELECT * FROM \"Student\" "
				+ "INNER JOIN \"Level\" ON \"Student\".\"levelId\" = \"Level\".\"id\" "
				+ "INNER JOIN \"AcademicSession\" ON \"Student\".\"academicSessionId\" = \"AcademicSession\".\"id\" "
				+ "INNER JOIN \"Faculty\" ON \"Student\".\"facultyId\" = \"Faculty\".\"id\" "
				+ "WHERE 1=1");

		if (name != null && !name.isEmpty()) {
			sql.append(" AND \"Student\".\"name\" ILIKE ?");
		}
		if (isMinor != null) {
			sql.append(" AND \"birthDate\" > ?");
		}
		if (levelId != null) {
			sql.append(" AND \"levelId\" = ?");
		}
		if (facultyId != null) {
			sql.append(" AND \"facultyId\" = ?");
		}

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

			int parameterIndex = 1;

			if (name != null && !name.isEmpty()) {
				preparedStatement.setString(parameterIndex++, "%" + name + "%");
			}
			if (isMinor != null) {
				// Calculate the date 18 years ago from today
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.YEAR, -18);
				java.sql.Date minorDateThreshold = new java.sql.Date(calendar.getTimeInMillis());
				preparedStatement.setDate(parameterIndex++, minorDateThreshold);
			}
			if (levelId != null) {
				preparedStatement.setInt(parameterIndex++, levelId);
			}
			if (facultyId != null) {
				preparedStatement.setInt(parameterIndex++, facultyId);
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Student student = Student.fromResultSet(resultSet);
					students.add(student);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	@Override
	public void updateStudent(Student student) {
		try {
			String sql = "UPDATE \"Student\" SET "
					+ "\"name\" = ?, "
					+ "\"gender\" = CAST(?::text AS \"public\".\"Gender\"), "
					+ "\"birthDate\" = ?, "
					+ "\"email\" = ?, "
					+ "\"facultyId\" = ?, "
					+ "\"levelId\" = ?, "
					+ "\"academicSessionId\" = ?, "
					+ "\"updatedAt\" = ? "
					+ "WHERE \"id\" = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, student.getName());
			preparedStatement.setString(2, student.getGender().toString());
			// Convert java.util.Date to java.sql.Date
			preparedStatement.setTimestamp(3, new java.sql.Timestamp(student.getBirthDate().getTime()));
			preparedStatement.setString(4, student.getEmail());
			preparedStatement.setInt(5, student.getFacultyId());
			preparedStatement.setInt(6, student.getLevelId());
			preparedStatement.setInt(7, student.getAcademicSessionId());
			preparedStatement.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
			preparedStatement.setString(9, student.getId());

			preparedStatement.executeUpdate();

			System.out.println("Student updated");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteStudent(String id) {
		try {
			String sql = "DELETE FROM \"Student\" WHERE \"id\" = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Student deleted");
			} else {
				System.out.println("No student found with the provided ID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Student> getLatecomers(Integer academicSessionId, PaymentPeriod paymentPeriod) {
		String sql = """
				SELECT *
				FROM "Student"
				    INNER JOIN "Level" ON "Student"."levelId" = "Level"."id"
				    INNER JOIN "AcademicSession" ON "Student"."academicSessionId" = "AcademicSession"."id"
				    INNER JOIN "Faculty" ON "Student"."facultyId" = "Faculty"."id"
				WHERE ("public"."Student"."id") NOT IN (
				        SELECT "t1"."studentId"
				        FROM "public"."Payment" AS "t1"
				        WHERE (
				                "t1"."academicSessionId" = ?
				                AND ("t1"."id") IN (
				                    SELECT "t2"."paymentId"
				                    FROM "public"."PaymentItem" AS "t2"
				                    WHERE (
				                            "t2"."period" = CAST(?::text AS "public"."PaymentPeriod")
				                            AND "t2"."paymentId" IS NOT NULL
				                        )
				                )
				                AND "t1"."studentId" IS NOT NULL
				            )
				    )
				""";

		List<Student> students = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, academicSessionId);
			preparedStatement.setString(2, paymentPeriod.toString());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Student student = Student.fromResultSet(resultSet);
				students.add(student);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return students;
	}
}
