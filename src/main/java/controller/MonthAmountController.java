package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AcademicSession;
import model.Faculty;
import model.Level;
import model.MonthAmount;
import model.Student;
import repository.amount.AmountRepository;
import repository.amount.AmountRepositoryImpl;
import repository.level.LevelRepository;
import repository.level.LevelRepositoryImpl;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class MonthAmountController
 */
public class MonthAmountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AmountRepository monthAmountRepository;
	private LevelRepository levelRepository;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonthAmountController() {
        monthAmountRepository = new AmountRepositoryImpl();
        levelRepository = new LevelRepositoryImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();

		if (action == null) {
			listAmounts(request, response);
		} else {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/delete":
				deleteAmount(request, response);
				break;
			default:
				listAmounts(request, response);
				break;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();

		if (action != null) {
			switch (action) {
			case "/store":
				insertAmount(request, response);
				break;
			case "/update":
				updateAmount(request, response);
				break;
			default:
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				break;
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void listAmounts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<MonthAmount> monthAmounts = monthAmountRepository.getMonthAmounts();
		
		request.setAttribute("monthAmounts", monthAmounts);

		request.getRequestDispatcher("/WEB-INF/views/amounts/index.jsp").forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Level> levels = levelRepository.getLevels();
		
		request.setAttribute("levels", levels);

		request.getRequestDispatcher("/WEB-INF/views/amounts/create.jsp").forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/views/amounts/edit.jsp").forward(request, response);
	}

	private void insertAmount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		monthAmountRepository.addLMonthAmount(MonthAmount.fromRequest(request));

		response.sendRedirect(request.getContextPath() + "/amounts");
	}

	private void updateAmount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		response.sendRedirect(request.getContextPath() + "/amounts");
	}

	private void deleteAmount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("amountId");
		
		Integer id = null;
		try {
			id = Integer.parseInt(idString);
			
			monthAmountRepository.deleteMonthAmount(id);
			
			response.sendRedirect(request.getContextPath() + "/amounts");
		} catch (Exception e) {
			// TODO: handle exception
		}


	}

}
