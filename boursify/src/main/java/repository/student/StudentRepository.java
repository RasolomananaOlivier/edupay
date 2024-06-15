package repository.student;

import java.util.List;

import model.Student;

public interface StudentRepository {

	void addStudent(Student student);

	Student getStudent(int id);

	List<Student> getAllStudents();

	void updateStudent(Student student);

	void deleteStudent(int id);
}
