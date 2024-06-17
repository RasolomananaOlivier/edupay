package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AcademicSession;
import model.Faculty;
import model.Level;
import model.Payment;
import model.Student;
import repository.academicSession.AcademicSessionRepository;
import repository.academicSession.AcademicSessionRepositoryImpl;
import repository.faculty.FacultyRepository;
import repository.faculty.FacultyRepositoryImpl;
import repository.level.LevelRepository;
import repository.level.LevelRepositoryImpl;
import repository.payment.PaymentRepository;
import repository.payment.PaymentRepositoryImpl;
import repository.student.StudentRepository;
import repository.student.StudentRepositoryImpl;
import util.PaymentPeriod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet implementation class PaymentController
 */
public class PaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PaymentRepository paymentRepository;
	private StudentRepository studentRepository;
	private AcademicSessionRepository sessionRepository;
	private LevelRepository levelRepository;
	private FacultyRepository facultyRepository;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaymentController() {
		super();
		paymentRepository = new PaymentRepositoryImpl();
		studentRepository = new StudentRepositoryImpl();
		sessionRepository = new AcademicSessionRepositoryImpl();
		levelRepository = new LevelRepositoryImpl();
		facultyRepository = new FacultyRepositoryImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getPathInfo();
		System.out.println(action);
		if (action == null) {
			listPayments(request, response);
		} else {
			switch (action) {
				case "/new": {
					showNewForm(request, response);
					break;
				}
				case "/edit":
					showEditForm(request, response);
					break;
				case "/delete":
					deletePayment(request, response);
					break;
				default:
					listPayments(request, response);
					break;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void listPayments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Payment> payments = paymentRepository.getMany();

		request.setAttribute("payments", payments);
		request.getRequestDispatcher("/WEB-INF/views/payments/index.jsp").forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Student> students = studentRepository.getAllStudents();
		List<PaymentPeriod> paymentPeriods = Arrays.asList(PaymentPeriod.values());
		List<AcademicSession> sessions = sessionRepository.getSessions();

		request.setAttribute("students", students);
		request.setAttribute("paymentPeriods", paymentPeriods);
		request.setAttribute("academicSessions", sessions);

		request.getRequestDispatcher("/WEB-INF/views/payments/create.jsp").forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String paymentId = request.getParameter("paymentId");
		System.out.println(paymentId);

		if (paymentId == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Payment payment = paymentRepository.getById(paymentId);
		request.setAttribute("payment", payment);

		if (payment == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// List<Level> levels = levelRepository.getLevels();
		// List<Faculty> faculties = facultyRepository.getFaculties();
		// List<AcademicSession> sessions = sessionRepository.getSessions();

		// request.setAttribute("levels", levels);
		// request.setAttribute("faculties", faculties);
		// request.setAttribute("academicSessions", sessions);

		// request.setAttribute("payment", payment);
		// Logic to show edit form
		request.getRequestDispatcher("/WEB-INF/views/payments/edit.jsp").forward(request, response);
	}

	private void insertPayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Payment payment = Payment.fromRequest(request);

		// paymentRepository.addPayment(payment);

		// response.sendRedirect(request.getContextPath() + "/payments");
	}

	private void updatePayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Payment payment = Payment.fromRequest(request);

		// paymentRepository.updatePayment(payment);

		// response.sendRedirect(request.getContextPath() + "/payments");
	}

	private void deletePayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// paymentRepository.deletePayment(request.getParameter("paymentId"));

		// response.sendRedirect(request.getContextPath() + "/payments");
	}
}
