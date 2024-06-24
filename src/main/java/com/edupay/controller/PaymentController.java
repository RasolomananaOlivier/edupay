package com.edupay.controller;

import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.edupay.repository.amount.AmountRepository;
import com.edupay.repository.amount.AmountRepositoryImpl;
import com.edupay.repository.paymentItem.PaymentItemRepository;
import com.edupay.repository.paymentItem.PaymentItemRepositoryImpl;
import com.edupay.repository.student.StudentRepository;
import com.edupay.repository.student.StudentRepositoryImpl;
import com.itextpdf.html2pdf.HtmlConverter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.edupay.model.EquipmentAmount;
import com.edupay.model.MonthAmount;
import com.edupay.model.Payment;
import com.edupay.model.PaymentItem;
import com.edupay.model.Student;
import com.edupay.repository.equipmentAmount.EquipmentAmountRepository;
import com.edupay.repository.equipmentAmount.EquipmentAmountRepositoryImpl;
import com.edupay.repository.payment.PaymentRepository;
import com.edupay.repository.payment.PaymentRepositoryImpl;
import com.edupay.util.PaymentPeriod;
import com.edupay.util.RandomStringGenerator;
import com.edupay.util.ReceiptFormatter;

/**
 * Servlet implementation class PaymentController
 */
public class PaymentController extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;
	private final PaymentRepository paymentRepository;
	private final StudentRepository studentRepository;
    private final PaymentItemRepository paymentItemRepository;
	private final AmountRepository amountRepository;
	private final EquipmentAmountRepository equipmentAmountRepository;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaymentController() {
		super();
		paymentRepository = new PaymentRepositoryImpl();
		studentRepository = new StudentRepositoryImpl();
        paymentItemRepository = new PaymentItemRepositoryImpl();
		amountRepository = new AmountRepositoryImpl();
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
				case "/receipt": {
					showReceipt(request, response);
					break;
				}
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
		String action = request.getPathInfo();

		if (action != null) {
			switch (action) {
				case "/store":
					insertPayment(request, response);
					break;
				case "/update":
					updatePayment(request, response);
					break;
				default:
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					break;
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void listPayments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Payment> payments = paymentRepository.getMany();

		request.setAttribute("payments", payments);

		request.getRequestDispatcher("/WEB-INF/views/payments/index.jsp").forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String studentId = request.getParameter("studentId");
		Student student = studentRepository.getStudent(studentId);
		if (student == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		MonthAmount monthAmount = amountRepository.getByLevelId(student.getLevelId());
		EquipmentAmount equipmentAmount = equipmentAmountRepository.getByLevelId(student.getLevelId());

		ArrayList<PaymentPeriod> disabledPaymentPeriods = new ArrayList<>();
		List<PaymentItem> paymentItems = paymentItemRepository
				.getBySessionIdAndStudentId(student.getAcademicSessionId(), student.getId());
		if (paymentItems != null && !paymentItems.isEmpty()) {
			for (PaymentItem paymentItem : paymentItems) {
				disabledPaymentPeriods.add(paymentItem.getPeriod());
			}
		}

		request.setAttribute("student", student);
		request.setAttribute("disabledPaymentPeriods", disabledPaymentPeriods);
		request.setAttribute("monthAmount", monthAmount);
		request.setAttribute("equipmentAmount", equipmentAmount);

		request.getRequestDispatcher("/WEB-INF/views/payments/create.jsp").forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String paymentId = request.getParameter("paymentId");

		if (paymentId == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Payment payment = paymentRepository.getById(paymentId);
		MonthAmount monthAmount = amountRepository.getByLevelId(payment.getLevelId());
		EquipmentAmount equipmentAmount = equipmentAmountRepository.getByLevelId(payment.getLevelId());

		request.setAttribute("payment", payment);

		ArrayList<PaymentPeriod> usedPaymentPeriods = new ArrayList<>();
		for (PaymentItem paymentItem : payment.getPaymentItems()) {
			usedPaymentPeriods.add(paymentItem.getPeriod());
		}

		ArrayList<PaymentPeriod> disabledPaymentPeriods = new ArrayList<>();

		List<PaymentItem> paymentItems = paymentItemRepository
				.getBySessionIdAndStudentId(payment.getAcademicSessionId(), payment.getStudentId());
		if (paymentItems != null && !paymentItems.isEmpty()) {
			for (PaymentItem paymentItem : paymentItems) {
				if (!paymentItem.getPaymentId().equals(payment.getId())) {
					disabledPaymentPeriods.add(paymentItem.getPeriod());
				}
			}
		}

		request.setAttribute("disabledPaymentPeriods", disabledPaymentPeriods);
		request.setAttribute("usedPaymentPeriods", usedPaymentPeriods);
		request.setAttribute("monthAmount", monthAmount);
		request.setAttribute("equipmentAmount", equipmentAmount);

		request.getRequestDispatcher("/WEB-INF/views/payments/edit.jsp").forward(request, response);
	}

	private void showReceipt(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String paymentId = request.getParameter("paymentId");
		Payment payment = paymentRepository.getById(paymentId);

		String formattedHtmlContent = ReceiptFormatter.formatHTML(payment);

		// Convert HTML to PDF
		response.setContentType("application/pdf");
		HtmlConverter.convertToPdf(formattedHtmlContent, response.getOutputStream());
	}

	private void insertPayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String studentId = request.getParameter("studentId");
		Student student = studentRepository.getStudent(studentId);

		MonthAmount monthAmount = amountRepository.getByLevelId(student.getLevelId());
		EquipmentAmount equipmentAmount = equipmentAmountRepository.getByLevelId(student.getLevelId());

		String paymentId = RandomStringGenerator.generate(5);

		List<PaymentItem> newPaymentItems = new ArrayList<>();
		String[] paymentPeriods = request.getParameterValues("paymentPeriods");
		if (paymentPeriods != null) {
			if (paymentPeriods.length == 0) {
				response.sendRedirect(request.getContextPath() + "/payments/new?studentId=" + studentId);
				return;
			}

			for (String paymentPeriod : paymentPeriods) {
				PaymentItem paymentItem = new PaymentItem();
				if (paymentPeriod.equals(PaymentPeriod.EQUIPMENT.toString())) {
					paymentItem.setAmount(equipmentAmount.getValue());
				} else {
					paymentItem.setAmount(monthAmount.getValue());
				}
				paymentItem.setPaymentId(paymentId);
				paymentItem.setPeriod(PaymentPeriod.valueOf(paymentPeriod));
				newPaymentItems.add(paymentItem);
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/payments/new?studentId=" + studentId);
			return;
		}

		Payment payment = new Payment();
		payment.setId(paymentId);
		payment.setStudentId(studentId);
		payment.setLevelId(student.getLevelId());
		payment.setAcademicSessionId(student.getAcademicSessionId());
		Date now = new Date();
		payment.setCreatedAt(now);
		payment.setUpdatedAt(now);

		paymentRepository.addOne(payment);

		paymentItemRepository.addMany(newPaymentItems);

		response.sendRedirect(request.getContextPath() + "/payments");
	}

	private void updatePayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String paymentId = request.getParameter("paymentId");

		if (paymentId == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Payment payment = paymentRepository.getById(paymentId);
		if (payment == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		MonthAmount monthAmount = amountRepository.getByLevelId(payment.getLevelId());
		EquipmentAmount equipmentAmount = equipmentAmountRepository.getByLevelId(payment.getLevelId());

		ArrayList<PaymentItem> newPaymentItems = new ArrayList<>();

		String[] paymentPeriods = request.getParameterValues("paymentPeriods");
		if (paymentPeriods != null) {
			for (String paymentPeriod : paymentPeriods) {
				PaymentItem paymentItem = new PaymentItem();
				paymentItem.setPaymentId(paymentId);

				if (paymentPeriod.equals(PaymentPeriod.EQUIPMENT.toString())) {
					paymentItem.setAmount(equipmentAmount.getValue());
				} else {
					paymentItem.setAmount(monthAmount.getValue());
				}
				paymentItem.setPeriod(PaymentPeriod.valueOf(paymentPeriod));
				newPaymentItems.add(paymentItem);
			}

			if (newPaymentItems.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/payments/edit?paymentId=" + paymentId);
				return;
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/payments/edit?paymentId=" + paymentId);
			return;
		}

		payment.setUpdatedAt(new Date());
		paymentRepository.updateOne(payment);

		paymentItemRepository
				.deleteMany(payment.getPaymentItems().stream().map((PaymentItem::getId)).toList());

		payment.setPaymentItems(newPaymentItems);
		paymentItemRepository.addMany(newPaymentItems);

		response.sendRedirect(request.getContextPath() + "/payments");
	}

	private void deletePayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String paymentId = request.getParameter("paymentId");

		paymentRepository.deleteOne(paymentId);

		response.sendRedirect(request.getContextPath() + "/payments");
	}
}
