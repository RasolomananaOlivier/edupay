package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AcademicSession;
import model.EquipmentAmount;
import model.Faculty;
import model.Level;
import model.MonthAmount;
import model.Student;
import repository.academicSession.AcademicSessionRepository;
import repository.academicSession.AcademicSessionRepositoryImpl;
import repository.amount.AmountRepository;
import repository.amount.AmountRepositoryImpl;
import repository.equipmentAmount.EquipmentAmountRepository;
import repository.equipmentAmount.EquipmentAmountRepositoryImpl;
import repository.faculty.FacultyRepository;
import repository.faculty.FacultyRepositoryImpl;
import repository.level.LevelRepository;
import repository.level.LevelRepositoryImpl;
import repository.student.StudentRepository;
import repository.student.StudentRepositoryImpl;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Servlet implementation class StudentController
 */
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentRepository studentRepository;
	private AcademicSessionRepository sessionRepository;
	private LevelRepository levelRepository;
	private FacultyRepository facultyRepository;
	private AmountRepository monthAmountRepository;
	private EquipmentAmountRepository equipmentAmountRepository;

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
			throws ServletException, IOException {
		String action = request.getPathInfo();

		if (action != null) {
			switch (action) {
				case "/store":
					insertStudent(request, response);
					break;
				case "/update":
					updateStudent(request, response);
					break;
				default:
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					break;
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	// TODO Create amountsAvailability Map<levelId, boolean>
	private void listStudents(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Student> students = null;

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

		request.setAttribute("levels", levels);
		request.setAttribute("faculties", faculties);
		request.setAttribute("academicSessions", sessions);
		request.setAttribute("levelAmountsAvailability", levelAmountsAvailability);

		request.setAttribute("students", students);
		request.getRequestDispatcher("/WEB-INF/views/students/index.jsp").forward(request, response);
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

	private void insertStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = Student.fromRequest(request);

		studentRepository.addStudent(student);

		response.sendRedirect(request.getContextPath() + "/students");
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = Student.fromRequest(request);

		studentRepository.updateStudent(student);

		response.sendRedirect(request.getContextPath() + "/students");
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		studentRepository.deleteStudent(request.getParameter("studentId"));

		response.sendRedirect(request.getContextPath() + "/students");
	}
}
