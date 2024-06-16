package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class PaymentController
 */
@WebServlet(urlPatterns = { "/", "/payments", "/payments/create", "/payments/store", "/payments/show/*",
		"/payments/edit/*", "/payments/update", "/payments/delete/*" })
public class PaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaymentController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		String[] pathInfo = request.getPathInfo() != null ? request.getPathInfo().split("/") : new String[0];
		System.out.println(path);
		switch (path) {
			case "/payments": {
				response.getWriter().append("/payments");
				break;
			}
			case "/payments/create": {
				response.getWriter().append("/payments/create");
				break;
			}
			case "/payments/edit": {
				response.getWriter().append("/payments/edit");
				break;
			}
			default:
//				response.getWriter().append("/payments/default");
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				// throw new IllegalArgumentException("Unexpected value: " + path);
		}

		// response.getWriter().append("Served at:
		// ").append(request.getContextPath()).append(path);
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

}
