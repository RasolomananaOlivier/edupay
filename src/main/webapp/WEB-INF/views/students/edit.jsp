<%@page import="model.Student"%>
<%@page import="model.AcademicSession"%>
<%@page import="model.Faculty"%>
<%@page import="model.Level"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
List<Level> levels = (List<Level>) request.getAttribute("levels");
List<Faculty> faculties = (List<Faculty>) request.getAttribute("faculties");
List<AcademicSession> sessions = (List<AcademicSession>) request.getAttribute("academicSessions");
Student student = (Student) request.getAttribute("student");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.css"
	rel="stylesheet" />
<title>Modification</title>
</head>
<body>
	<jsp:include page="/WEB-INF/components/header.jsp" />

	<main class="px-3 py-5 sm:px-40 md:px-60 lg:px-80">
		<h1 class="text-3xl font-medium">Modifier les informations sur un
			étudiant</h1>
		<p class="mb-5 text-slate-600">Veuillez remplir les champs
			suivants pour modifier les informations sur un étudiant</p>

		<form action="update" method="post">
			<div class="mb-6 flex flex-col gap-5">
				<div>
					<label for="id"
						class="mb-2 block text-sm font-medium text-gray-900">N°
						matricule</label> <input type="text" id="id" name="id"
						value="<%=student.getId()%>"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="2413" required />
				</div>

				<div>
					<label for="name"
						class="mb-2 block text-sm font-medium text-gray-900">Nom</label> <input
						type="text" id="name" name="name" value="<%=student.getName()%>"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="John Doe" required />
				</div>

				<div>
					<label for="email"
						class="mb-2 block text-sm font-medium text-gray-900">Email</label>
					<input type="email" id="email" name="email"
						value="<%=student.getEmail()%>"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="john@gmail.com" required />
				</div>

				<div>
					<label for="date"
						class="mb-2 block text-sm font-medium text-gray-900">Date
						de naissance</label> <input type="date" id="date" name="birthDate"
						value="<%=student.getBirthDate()%>"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						required />
				</div>

				<div>
					<div class="mb-2">Genre</div>
					<div class="flex items-center gap-4">
						<div class="flex items-center">
							<input id="default-radio-1" name="gender" type="radio"
								<%=student.getGender().name() == "MALE" ? "checked='checked'" : ""%>
								value="MALE" name="default-radio"
								class="h-4 w-4 border-gray-300 bg-gray-100 text-blue-600 focus:ring-blue-500" />
							<label for="default-radio-1"
								class="ms-2 text-sm font-medium text-gray-900">Homme</label>
						</div>
						<div class="flex items-center">
							<input id="default-radio-2" name="gender" type="radio"
								value="FEMALE" name="default-radio"
								<%=student.getGender().name() == "FEMALE" ? "checked='checked'" : ""%>
								class="h-4 w-4 border-gray-300 bg-gray-100 text-blue-600 focus:ring-blue-500" />
							<label for="default-radio-2"
								class="ms-2 text-sm font-medium text-gray-900">Femme</label>
						</div>
					</div>
				</div>

				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">Institution</label>
					<select id="countries" name="faculty"
						class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">

						<%
						for (Faculty faculty : faculties) {
						%>
						<option
							<%= student.getFacultyId() == faculty.getId() ? "selected" : ""%>
							value="<%=faculty.getId()%>">
							<%=faculty.getName()%>
						</option>
						<%
						}
						%>
					</select>
				</div>

				<div>
					<label for="level"
						class="mb-2 block text-sm font-medium text-gray-900">Niveau</label>
					<select id="countries" name="level"
						class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
						<%
						for (Level level : levels) {
						%>
						<option value="<%=level.getId()%>"
						<%= student.getLevelId() == level.getId() ? "selected" : ""%>
						>
							<%=level.getName()%>
						</option>
						<%
						}
						%>
					</select>
				</div>

				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">Année
						universitaire</label> <select id="countries" name="academicSession"
						class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
						<%
						for (AcademicSession academicSession : sessions) {
						%>
						<option value="<%=academicSession.getId()%>" 
						<%= student.getAcademicSessionId() == academicSession.getId() ? "selected" : ""%>
						>
							<%=academicSession.getYear()%>
						</option>
						<%
						}
						%>
					</select>
				</div>
			</div>

			<div class="flex justify-end">
				<button type="submit"
					class="w-full rounded-full bg-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 sm:w-auto dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Confirmer</button>
			</div>

		</form>
	</main>



	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.js"></script>
</body>
</html>