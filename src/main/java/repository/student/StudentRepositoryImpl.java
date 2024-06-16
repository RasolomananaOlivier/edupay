/**
 * 
 */
package repository.student;

import java.sql.Connection;
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
		// TODO Auto-generated method stub

	}

	@Override
	public Student getStudent(int id) {
		// TODO Auto-generated method stub
		return null;
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
				Student student = Student.formResultSet(resultSet);
				
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
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteStudent(int id) {
		// TODO Auto-generated method stub

	}

}
