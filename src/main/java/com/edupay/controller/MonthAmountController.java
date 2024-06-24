package com.edupay.controller;

import com.edupay.repository.amount.AmountRepository;
import com.edupay.repository.amount.AmountRepositoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.edupay.model.Level;
import com.edupay.model.MonthAmount;
import com.edupay.repository.level.LevelRepository;
import com.edupay.repository.level.LevelRepositoryImpl;

import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Servlet implementation class MonthAmountController
 */
public class MonthAmountController extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;

	private final AmountRepository monthAmountRepository;
	private final LevelRepository levelRepository;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MonthAmountController() {
		monthAmountRepository = new AmountRepositoryImpl();
		levelRepository = new LevelRepositoryImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		List<MonthAmount> monthAmounts = monthAmountRepository.getMonthAmounts();
		List<Integer> disabledLevelIds = monthAmounts.stream().map((MonthAmount::getLevelId)).toList();

		request.setAttribute("levels", levels);
		request.setAttribute("disabledLevelIds", disabledLevelIds);

		request.getRequestDispatcher("/WEB-INF/views/amounts/create.jsp").forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
		String idString = request.getParameter("amountId");

		Integer id = null;
		try {
			id = Integer.parseInt(idString);

			MonthAmount amount = monthAmountRepository.getMonthAmount(id);
			List<Level> levels = levelRepository.getLevels();

			List<MonthAmount> monthAmounts = monthAmountRepository.getMonthAmounts();
			List<Integer> disabledLevelIds = new ArrayList<>();
			for (MonthAmount monthAmount : monthAmounts) {
				if (!Objects.equals(monthAmount.getLevelId(), amount.getLevelId())) {
					disabledLevelIds.add(monthAmount.getLevelId());
				}
			}

			request.setAttribute("levels", levels);
			request.setAttribute("amount", amount);
			request.setAttribute("disabledLevelIds", disabledLevelIds);

			request.getRequestDispatcher("/WEB-INF/views/amounts/edit.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void insertAmount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("level") == null || request.getParameter("amountValue") == null) {
			response.sendRedirect(request.getContextPath() + "/amounts/new");
		} else {
			monthAmountRepository.addMonthAmount(MonthAmount.fromRequest(request));
			response.sendRedirect(request.getContextPath() + "/amounts");
		}
	}

	private void updateAmount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("level") == null || request.getParameter("amountValue") == null) {
			response.sendRedirect(request.getContextPath() + "/amounts/edit?amountId=" + request
					.getParameter("amountId"));
		} else {
			monthAmountRepository.updateMonthAmount(MonthAmount.fromRequest(request));
			response.sendRedirect(request.getContextPath() + "/amounts");
		}
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
