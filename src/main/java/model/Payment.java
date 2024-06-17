package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.Gender;

public class Payment {
	private String id;
	private String studentId;
	private Student student;
	private int academicSessionId;
	private AcademicSession academicSession;
	private int levelId;
	private Level level;
	private Date createdAt;
	private Date updatedAt;
	private List<PaymentItem> paymentItems;

	public Payment() {
		super();
	}

	public Payment(String id,

			String studentId,
			Student student,

			int academicSessionId,
			AcademicSession academicSession,

			int levelId,
			Level level,

			Date createdAt,
			Date updatedAt,

			List<PaymentItem> paymentItems) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.student = student;
		this.academicSessionId = academicSessionId;
		this.academicSession = academicSession;
		this.levelId = levelId;
		this.level = level;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.paymentItems = paymentItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public int getAcademicSessionId() {
		return academicSessionId;
	}

	public void setAcademicSessionId(int academicSessionId) {
		this.academicSessionId = academicSessionId;
	}

	public AcademicSession getAcademicSession() {
		return academicSession;
	}

	public void setAcademicSession(AcademicSession academicSession) {
		this.academicSession = academicSession;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<PaymentItem> getPaymentItems() {
		return paymentItems;
	}

	public void setPaymentItems(List<PaymentItem> paymentItems) {
		this.paymentItems = paymentItems;
	}

	public static Payment fromResultSet(ResultSet resultSet) throws SQLException {
		Payment payment = new Payment(
				resultSet.getString("Payment.id"),

				resultSet.getString("Payment.studentId"),
				new Student(
						resultSet.getString("Student.id"),
						resultSet.getString("Student.name"),
						Gender.valueOf(resultSet.getString("Student.gender")),
						resultSet.getDate("Student.birthDate"),
						resultSet.getString("Student.email"),
						resultSet.getInt("Student.facultyId"),
						resultSet.getInt("Student.levelId"),
						resultSet.getInt("Student.academicSessionId"),
						resultSet.getDate("Student.createdAt"),
						resultSet.getDate("Student.updatedAt"),
						new Level(
								resultSet.getInt("Student.Level.id"),
								resultSet.getString("Student.Level.name")),
						new AcademicSession(
								resultSet.getInt("Student.AcademicSession.id"),
								resultSet.getInt("Student.AcademicSession.year")),
						new Faculty(
								resultSet.getInt("Student.Faculty.id"),
								resultSet.getString("Student.Faculty.name"))),

				resultSet.getInt("Payment.academicSessionId"),
				new AcademicSession(
						resultSet.getInt("AcademicSession.id"),
						resultSet.getInt("AcademicSession.year")),

				resultSet.getInt("Level.id"),
				new Level(resultSet.getInt("Level.id"), resultSet.getString("Level.name")),

				resultSet.getDate("Payment.createdAt"),
				resultSet.getDate("Payment.updatedAt"),

				new ArrayList<PaymentItem>());

		return payment;
	}
}
