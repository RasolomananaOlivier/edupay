package com.edupay.repository.student;

import java.util.List;

import com.edupay.model.Student;
import com.edupay.util.PaymentPeriod;

public interface StudentRepository {

	void addStudent(Student student);

	Student getStudent(String id);

	List<Student> getAllStudents(String name, Boolean isMinor, Integer levelId, Integer facultyId);

	List<Student> getLatecomers(Integer academicSessionId, PaymentPeriod paymentPeriod);

	void updateStudent(Student student);

	void deleteStudent(String id);
}
