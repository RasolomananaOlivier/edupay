package model;

import java.util.Date;
import java.util.List;

public class Payment {
	private String id;
	private String studentId;
	private Student student;
	private int academicSessionId;
	private AcademicSession academicSession;
	private Date createdAt;
	private Date updatedAt;
	private List<PaymentItem> paymentItems;

	public Payment(String id, String studentId, Student student, int academicSessionId, AcademicSession academicSession,
			Date createdAt, Date updatedAt, List<PaymentItem> paymentItems) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.student = student;
		this.academicSessionId = academicSessionId;
		this.academicSession = academicSession;
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
}
