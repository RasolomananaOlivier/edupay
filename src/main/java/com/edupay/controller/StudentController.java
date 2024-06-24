package com.edupay.controller;

import com.edupay.repository.amount.AmountRepository;
import com.edupay.repository.amount.AmountRepositoryImpl;
import com.edupay.repository.student.StudentRepository;
import com.edupay.repository.student.StudentRepositoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.edupay.model.AcademicSession;
import com.edupay.model.EquipmentAmount;
import com.edupay.model.Faculty;
import com.edupay.model.Level;
import com.edupay.model.MonthAmount;
import com.edupay.model.Student;
import com.edupay.repository.academicSession.AcademicSessionRepository;
import com.edupay.repository.academicSession.AcademicSessionRepositoryImpl;
import com.edupay.repository.equipmentAmount.EquipmentAmountRepository;
import com.edupay.repository.equipmentAmount.EquipmentAmountRepositoryImpl;
import com.edupay.repository.faculty.FacultyRepository;
import com.edupay.repository.faculty.FacultyRepositoryImpl;
import com.edupay.repository.level.LevelRepository;
import com.edupay.repository.level.LevelRepositoryImpl;
import com.edupay.util.Mail;
import com.edupay.util.PaymentPeriod;

import java.io.IOException;
import java.io.Serial;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class StudentController
 */
public class StudentController extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;

	private final StudentRepository studentRepository;
	private final AcademicSessionRepository sessionRepository;
	private final LevelRepository levelRepository;
	private final FacultyRepository facultyRepository;
	private final AmountRepository monthAmountRepository;
	private final EquipmentAmountRepository equipmentAmountRepository;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentController() {
		studentRepository = new StudentRepositoryImpl();
		sessionRepository = new AcademicSessionRepositoryImpl();
		levelRepository = new LevelRepositoryImpl();
		facultyRepository = new FacultyRepositoryImpl();
		monthAmountRepository = new AmountRepositoryImpl();
		equipmentAmountRepository = new EquipmentAmountRepositoryImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();

		if (action == null) {
			listStudents(request, response);
		} else {
			switch (action) {
				case "/new":
					showNewForm(request, response);
					break;
				case "/edit":
					showEditForm(request, response);
					break;
				case "/delete":
					deleteStudent(request, response);
					break;
				case "/latecomers":
					listLateComers(request, response);
					break;
				default:
					listStudents(request, response);
					break;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String action = request.getPathInfo();

		if (action != null) {
			switch (action) {
				case "/store":
					insertStudent(request, response);
					break;
				case "/update":
					updateStudent(request, response);
					break;
				case "/latecomers":
					notifyLateComers(request, response);
					break;
				default:
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					break;
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Student> students;

		String name = request.getParameter("q");
		String minor = request.getParameter("minor");

		String levelString = request.getParameter("levelId");
		Integer levelId = levelString != null && !levelString.equals("all") ? Integer.parseInt(levelString)
				: null;

		String facultyString = request.getParameter("facultyId");
		Integer facultyId = facultyString != null && !facultyString.equals("all")
				? Integer.parseInt(facultyString)
				: null;

		students = studentRepository.getAllStudents(name, minor != null ? true : null, levelId, facultyId);

		List<Level> levels = levelRepository.getLevels();
		List<Faculty> faculties = facultyRepository.getFaculties();
		List<AcademicSession> sessions = sessionRepository.getSessions();

		Map<Integer, Boolean> levelAmountsAvailability = getIntegerBooleanMap(levels);

		request.setAttribute("levels", levels);
		request.setAttribute("faculties", faculties);
		request.setAttribute("academicSessions", sessions);
		request.setAttribute("levelAmountsAvailability", levelAmountsAvailability);

		request.setAttribute("students", students);
		request.getRequestDispatcher("/WEB-INF/views/students/index.jsp").forward(request, response);
	}

	private Map<Integer, Boolean> getIntegerBooleanMap(List<Level> levels) {
		Map<Integer, Boolean> levelMonthAmountsAvailability = new LinkedHashMap<>();
		List<MonthAmount> monthAmounts = monthAmountRepository.getMonthAmounts();
		for (MonthAmount monthAmount : monthAmounts) {
			levelMonthAmountsAvailability.put(monthAmount.getLevelId(), true);
		}

		Map<Integer, Boolean> levelEquipmentAmountsAvailability = new LinkedHashMap<>();
		List<EquipmentAmount> equipmentAmounts = equipmentAmountRepository.getMonthAmounts();
		for (EquipmentAmount equipmentAmount : equipmentAmounts) {
			levelEquipmentAmountsAvailability.put(equipmentAmount.getLevelId(), true);
		}

		Map<Integer, Boolean> levelAmountsAvailability = new LinkedHashMap<>();
		for (Level level : levels) {
			boolean isMonthAmountAvailable = levelMonthAmountsAvailability.containsKey(level.getId());
			boolean isEquipmentAmountAvailable = levelEquipmentAmountsAvailability.containsKey(level.getId());
			levelAmountsAvailability.put(level.getId(), isMonthAmountAvailable && isEquipmentAmountAvailable);
		}
		return levelAmountsAvailability;
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Level> levels = levelRepository.getLevels();
		List<Faculty> faculties = facultyRepository.getFaculties();
		List<AcademicSession> sessions = sessionRepository.getSessions();

		request.setAttribute("levels", levels);
		request.setAttribute("faculties", faculties);
		request.setAttribute("academicSessions", sessions);

		request.getRequestDispatcher("/WEB-INF/views/students/create.jsp").forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = studentRepository.getStudent(request.getParameter("studentId"));

		List<Level> levels = levelRepository.getLevels();
		List<Faculty> faculties = facultyRepository.getFaculties();
		List<AcademicSession> sessions = sessionRepository.getSessions();

		request.setAttribute("levels", levels);
		request.setAttribute("faculties", faculties);
		request.setAttribute("academicSessions", sessions);

		request.setAttribute("student", student);
		// Logic to show edit form
		request.getRequestDispatcher("/WEB-INF/views/students/edit.jsp").forward(request, response);
	}

	private void listLateComers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer academicSessionId;
		PaymentPeriod paymentPeriod;

		String rawAcademicSessionId = request.getParameter("academicSessionId");
		if (rawAcademicSessionId != null && !rawAcademicSessionId.isEmpty()) {
			academicSessionId = Integer.parseInt(rawAcademicSessionId);
		} else {
			academicSessionId = sessionRepository.getLatest().getId();
		}

		String rawPaymentPeriod = request.getParameter("paymentPeriod");
		if (rawPaymentPeriod != null && !rawPaymentPeriod.isEmpty()) {
			paymentPeriod = PaymentPeriod.valueOf(rawPaymentPeriod);
		} else {
			paymentPeriod = PaymentPeriod.EQUIPMENT;
		}

		if (rawAcademicSessionId == null || rawPaymentPeriod == null) {
			response.sendRedirect(request.getContextPath() + "/students/latecomers?academicSessionId="
					+ academicSessionId + "&paymentPeriod=" + paymentPeriod);
			return;
		}

		List<Student> students = studentRepository.getLatecomers(academicSessionId, paymentPeriod);
		List<AcademicSession> academicSessions = sessionRepository.getSessions();

		request.setAttribute("students", students);
		request.setAttribute("academicSessions", academicSessions);
		request.setAttribute("academicSessionId", academicSessionId);
		request.setAttribute("paymentPeriod", paymentPeriod);

		request.getRequestDispatcher("/WEB-INF/views/students/latecomers.jsp").forward(request, response);
	}

	private void notifyLateComers(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Integer academicSessionId;
		PaymentPeriod paymentPeriod;

		String rawAcademicSessionId = request.getParameter("academicSessionId");
		if (rawAcademicSessionId != null && !rawAcademicSessionId.isEmpty()) {
			academicSessionId = Integer.parseInt(rawAcademicSessionId);
		} else {
			academicSessionId = sessionRepository.getLatest().getId();
		}

		String rawPaymentPeriod = request.getParameter("paymentPeriod");
		if (rawPaymentPeriod != null && !rawPaymentPeriod.isEmpty()) {
			paymentPeriod = PaymentPeriod.valueOf(rawPaymentPeriod);
		} else {
			paymentPeriod = PaymentPeriod.EQUIPMENT;
		}

		if (rawAcademicSessionId == null || rawPaymentPeriod == null) {
			response.sendRedirect(request.getContextPath() + "/students/latecomers?academicSessionId="
					+ academicSessionId + "&paymentPeriod=" + paymentPeriod);
			return;
		}

		List<Student> students = studentRepository.getLatecomers(academicSessionId, paymentPeriod);

		sendEmailToLatecomers(students, academicSessionId, paymentPeriod);

		request.setAttribute("students", students);

		response.sendRedirect(request.getContextPath() + "/students/latecomers?academicSessionId="
				+ academicSessionId + "&paymentPeriod=" + paymentPeriod);
	}

	private void sendEmailToLatecomers(List<Student> students, Integer academicSessionId, PaymentPeriod paymentPeriod) {
		List<String> emails = students.stream().map(Student::getEmail).toList();

		String template = """
				<!DOCTYPE html>
				<html lang="fr">
				  <head>
				    <meta charset="UTF-8" />
				    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
				    <title>Notification de Bourse d'Étude</title>
				  </head>
				  <body
				    style="
				      font-family: Arial, sans-serif;
				      line-height: 1.6;
				      background-color: #f4f4f4;
				      padding: 20px;
				    "
				  >
				    <div
				      style="
				        max-width: 600px;
				        margin: 0 auto;
				        background-color: #fff;
				        padding: 20px;
				        border: 1px solid #ddd;
				        border-radius: 5px;
				      "
				    >
				      <h2 style="color: #333">Notification Importante</h2>
				      <p style="color: #333">Cher(e) étudiant(e),</p>
				      <p style="color: #333">
				        Nous tenons à vous informer qu'il vous reste trois semaines pour
				        récupérer votre bourse d'étude pour le mois de {1} de l'année universitaire {2}.
				      </p>
				      <p style="color: #333">
				        Veuillez vous assurer de compléter toutes les démarches nécessaires
				        avant la date limite afin de garantir le versement de votre bourse.
				      </p>
				      <p style="color: #333">
				        Si vous avez des questions ou besoin d'assistance, n'hésitez pas à
				        contacter notre service d'assistance aux étudiants.
				      </p>
				      <p style="color: #333">Cordialement,</p>
				      <p style="color: #333">L'équipe des Bourses d'Étude</p>
				    </div>
				  </body>
				</html>
				""";

		String body = template.replace("{1}", paymentPeriod.getLabel()).replace("{2}",
				sessionRepository.getById(academicSessionId).getYear() + " - " + (sessionRepository
						.getById(academicSessionId).getYear() + 1));

		Mail.send(body, emails);
	}

	private void insertStudent(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Student student = Student.fromRequest(request);

		studentRepository.addStudent(student);

		response.sendRedirect(request.getContextPath() + "/students");
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Student student = Student.fromRequest(request);

		studentRepository.updateStudent(student);

		response.sendRedirect(request.getContextPath() + "/students");
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		studentRepository.deleteStudent(request.getParameter("studentId"));

		response.sendRedirect(request.getContextPath() + "/students");
	}
}
