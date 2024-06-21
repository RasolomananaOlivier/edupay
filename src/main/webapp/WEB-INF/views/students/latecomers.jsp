<%@page import="model.AcademicSession"%>
<%@page import="model.Faculty"%>
<%@page import="model.Level"%>
<%@page import="model.Student"%>
<%@page import="util.PaymentPeriod"%>
<%@page import="util.DateFormatter"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
List<Student> students = (List<Student>) request.getAttribute("students");
List<AcademicSession> academicSessions = (List<AcademicSession>) request.getAttribute("academicSessions");
PaymentPeriod selectedPaymentPeriod = (PaymentPeriod) request.getAttribute("paymentPeriod");
Integer selectedAcademicSessionId = (Integer) request.getAttribute("academicSessionId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.css"
	rel="stylesheet" />
<title>Liste des retardataires</title>
</head>
<body>
	<jsp:include page="/WEB-INF/components/header.jsp" />

	<main class="px-5 py-3">
		<h1 class="text-3xl font-bold">Liste des retardataires</h1>

		<div class="flex items-center justify-between">
			<div class="mt-1 text-slate-600">Retrouver ici la liste des
				retardataires</div>
		</div>

		<form action="<%= request.getContextPath() + "/students/latecomers" %>" method="get"
			class="bg-slate-100 flex items-start justify-between rounded-lg px-4 py-3 my-4">
			<div class="flex items-start gap-5 py-3">
				<div>
					<label for="academicSessionId"
						class="block mb-2 text-sm font-medium text-gray-900">Année universitaire</label>
					<select id="academicSessionId" name="academicSessionId"
						class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500">
						<%
							for (AcademicSession academicSession : academicSessions) {
						%>
							<option
								value="<%= academicSession.getId() %>"
								<%= academicSession.getId() == selectedAcademicSessionId ? "selected" : "" %>
							>
								<%= academicSession.getYear() + " - " + (academicSession.getYear() + 1) %>
							</option>
						<%
							}
						%>
					</select>
				</div>

				<div>
					<label for="paymentPeriod"
						class="block mb-2 text-sm font-medium text-gray-900">Mois</label>
					<select id="paymentPeriod" name="paymentPeriod"
						class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500">

						<%
							for (PaymentPeriod paymentPeriod : PaymentPeriod.values()) {
						%>
							<option
								value="<%=paymentPeriod.toString()%>"
								<%= paymentPeriod == selectedPaymentPeriod ? "selected" : "" %>
							>
								<%= paymentPeriod.getLabel() %>
							</option>
						<%
							}
						%>
					</select>
				</div>
			</div>

			<button type="submit"
				class="mb-1 me-2 rounded-full bg-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Rechercher</button>
		</form>

		<div class="mt-5">
			Résultats : <span class="font-bold"><%=students.size()%> </span>
			étudiants
		</div>

		<% if (!students.isEmpty()) { %>
			<div class="mt-5">
				<form
					href="<%= request.getContextPath() + "/students/latecomers" %>"
					method="POST"
				>
					<input type="hidden" name="academicSessionId" value="<%= selectedAcademicSessionId %>" />
					<input type="hidden" name="paymentPeriod" value="<%= selectedPaymentPeriod %>" />

					<input type="submit"
						value="Notifier par email les <%= students.size() %> retardataires"
						class="mb-1 me-2 rounded-full bg-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
					/>
				</form>
			</div>
		<% } %>

		<div class="flex flex-col gap-4 pt-5">
			<%
			for (Student student : students) {
			%>

			<div class="flex flex-col gap-5 rounded-md border p-5">
				<div class="flex justify-between">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">N° matricule</div>
						<div class="font-semibold text-slate-800"><%=student.getId()%></div>
					</div>
				</div>

				<div class="flex flex-col">
					<div class="text-sm text-slate-700">Nom</div>
					<div class="font-semibold text-slate-800">
						<%=student.getName()%>
					</div>
				</div>

				<div class="flex gap-10">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Email</div>
						<div class="font-semibold text-slate-800"><%=student.getEmail()%></div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Date de naissance</div>
						<div class="font-semibold text-slate-800"><%= DateFormatter.format(student.getBirthDate())%></div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Sexe</div>
						<div class="font-semibold text-slate-800"><%=student.getGender().getLabel()%>
						</div>
					</div>
				</div>

				<div class="flex gap-10">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Institution</div>
						<div class="font-semibold text-slate-800"><%=student.getFaculty().getName()%>
						</div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Niveau</div>
						<div class="font-semibold text-slate-800"><%=student.getLevel().getName()%>
						</div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Année universitaire</div>
						<div class="font-semibold text-slate-800"><%=student.getSession().getYear()%>
							-
							<%=student.getSession().getYear() + 1%>
						</div>
					</div>
				</div>
			</div>
			<%
			}
			%>


		</div>
	</main>


	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.js"></script>

</body>
</html>