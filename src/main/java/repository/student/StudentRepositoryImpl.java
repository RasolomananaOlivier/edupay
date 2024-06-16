/**
 * 
 */
package repository.student;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.Database;
import model.Student;

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
	        String sql =  "INSERT INTO \"Student\" "
	        		+ "(\"id\", \"name\", \"gender\", \"birthDate\", \"email\", \"facultyId\", \"levelId\", \"academicSessionId\", \"createdAt\", \"updatedAt\") "
	        		+ "VALUES (?, ?, CAST(?::text AS \"public\".\"Gender\"), ?, ?, ?, ?, ?, ?, ?)";


	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, student.getId());
	        preparedStatement.setString(2, student.getName());
	        preparedStatement.setString(3, student.getGender().toString());
	        preparedStatement.setDate(4, new Date(student.getBirthDate().getTime())); // Convert java.util.Date to java.sql.Date
	        preparedStatement.setString(5, student.getEmail());
	        preparedStatement.setInt(6, student.getFacultyId());
	        preparedStatement.setInt(7, student.getLevelId());
	        preparedStatement.setInt(8, student.getAcademicSessionId());
	        preparedStatement.setDate(9, new Date(student.getCreatedAt().getTime()));
	        preparedStatement.setDate(10, new Date(student.getUpdatedAt().getTime()));

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
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<Student>();
		
		try {
			String sql = "SELECT * FROM \"Student\" "
					+ "INNER JOIN \"Level\" ON \"Student\".\"levelId\" = \"Level\".\"id\" "
					+ "INNER JOIN \"AcademicSession\" ON \"Student\".\"academicSessionId\" = \"AcademicSession\".\"id\" "
					+ "INNER JOIN \"Faculty\" ON \"Student\".\"facultyId\" = \"Faculty\".\"id\"";
			
			Statement st = connection.createStatement();
			
			ResultSet resultSet = st.executeQuery(sql);
			
			while (resultSet.next()) {
				Student student = Student.fromResultSet(resultSet);
				
				students.add(student);
			}
		} catch (Exception e) {
			// TODO: handle exception
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
	        preparedStatement.setDate(3, new Date(student.getBirthDate().getTime())); // Convert java.util.Date to java.sql.Date
	        preparedStatement.setString(4, student.getEmail());
	        preparedStatement.setInt(5, student.getFacultyId());
	        preparedStatement.setInt(6, student.getLevelId());
	        preparedStatement.setInt(7, student.getAcademicSessionId());
	        preparedStatement.setDate(8, new Date(System.currentTimeMillis()));
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

}
