package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AcademicSession;
import model.Faculty;
import model.Level;
import model.Student;
import repository.academicSession.AcademicSessionRepository;
import repository.academicSession.AcademicSessionRepositoryImpl;
import repository.faculty.FacultyRepository;
import repository.faculty.FacultyRepositoryImpl;
import repository.level.LevelRepository;
import repository.level.LevelRepositoryImpl;
import repository.student.StudentRepository;
import repository.student.StudentRepositoryImpl;

import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * Servlet implementation class StudentController
 */
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentRepository studentRepository;
	private AcademicSessionRepository sessionRepository;
	private LevelRepository levelRepository;
	private FacultyRepository facultyRepository;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentController() {
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
	
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Student> students = studentRepository.getAllStudents();
		
		request.setAttribute("students", students);
		request.getRequestDispatcher("/WEB-INF/views/students/index.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<Level> levels = levelRepository.getLevels();
    	List<Faculty> faculties = facultyRepository.getFaculties();
    	List<AcademicSession> sessions = sessionRepository.getSessions();
    	
    	request.setAttribute("levels", levels);
    	request.setAttribute("faculties", faculties);
    	request.setAttribute("academicSessions", sessions);
    	
        request.getRequestDispatcher("/WEB-INF/views/students/create.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to show edit form
        request.getRequestDispatcher("/students/edit.jsp").forward(request, response);
    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Student student = Student.fromRequest(request);
    	
    	studentRepository.addStudent(student);
    	
    	response.sendRedirect(request.getContextPath() + "/students");
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to update student
        response.sendRedirect("/students");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to delete student
        response.sendRedirect("/students");
    }
}
