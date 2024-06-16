package repository.student;

import java.util.List;
import java.util.Map;

import model.Student;

public interface StudentRepository {

	void addStudent(Student student);

	Student getStudent(String id);

	List<Student> getAllStudents(String name, Boolean isMinor, Integer levelId, Integer facultyId);

	void updateStudent(Student student);

	void deleteStudent(String id);
}
