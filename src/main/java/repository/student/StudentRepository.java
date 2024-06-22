package repository.student;

import java.util.List;

import model.Student;
import util.PaymentPeriod;

public interface StudentRepository {

	void addStudent(Student student);

	Student getStudent(String id);

	List<Student> getAllStudents(String name, Boolean isMinor, Integer levelId, Integer facultyId);

	List<Student> getLatecomers(Integer academicSessionId, PaymentPeriod paymentPeriod);

	void updateStudent(Student student);

	void deleteStudent(String id);
}
