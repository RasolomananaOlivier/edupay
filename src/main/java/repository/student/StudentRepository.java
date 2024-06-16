package repository.student;

import java.util.List;

import model.Student;

public interface StudentRepository {

	void addStudent(Student student);

	Student getStudent(String id);

	List<Student> getAllStudents();

	void updateStudent(Student student);

	void deleteStudent(String id);
}
