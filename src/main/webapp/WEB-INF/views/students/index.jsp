<%@page import="model.AcademicSession"%>
<%@page import="model.Faculty"%>
<%@page import="model.Level"%>
<%@page import="model.Student"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
List<Student> students = (List<Student>) request.getAttribute("students");
List<Level> levels = (List<Level>) request.getAttribute("levels");
List<Faculty> faculties = (List<Faculty>) request.getAttribute("faculties");
List<AcademicSession> sessions = (List<AcademicSession>) request.getAttribute("academicSessions");
Map<Integer, Boolean> levelAmountsAvailability = (Map<Integer, Boolean>) request.getAttribute("levelAmountsAvailability");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.css"
	rel="stylesheet" />
<title>Liste des étudiants</title>
</head>
<body>
	<div class="flex justify-between p-3">
		<div class="font-bold text-blue-600 font-mono">Boursify</div>

		<div class="flex justify-between gap-3">
			<a href="students/index.jsp"> Etudiants </a> <a> Montant </a> <a>
				Paiement </a>
		</div>
	</div>

	<main class="px-5 py-3">
		<h1 class="text-3xl font-bold">Liste des étudiants</h1>

		<div class="flex items-center justify-between">
			<div class="mt-1 text-slate-600">Retrouver ici la liste des
				étudiants</div>

			<a href="students/new"
				class="mb-2 me-2 rounded-full bg-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Nouvel
				étudiant</a>
		</div>

		<div>

			<form action="students" method="get"
				class="bg-slate-100 rounded-lg px-4 py-3 my-4">

				<div class="mt-3 flex gap-2 items-center">
					<input type="search" id="first_name" name="q"
						value="<%=request.getParameter("q") != null && request.getParameter("q") != "" ? request.getParameter("q") : ""%>"
						class="bg-gray block w-full rounded-full border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="Entrez le nom d'un étudiant" />
					<div>
						<button type="submit"
							class="mb-1 me-2 rounded-full bg-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Rechercher</button>
					</div>
				</div>

				<div class="flex items-end justify-end gap-5 py-3">

					<div class="mt-3 flex gap-2">
						<div class="flex items-center">
							<input id="checked-checkbox" type="checkbox" name="minor"
								<%=request.getParameter("minor") != null ? "checked" : ""%>
								class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2">
							<label for="checked-checkbox"
								class="ms-2 text-sm font-medium text-gray-900">Afficher
								seulement les mineurs</label>
						</div>
					</div>

					<div>
						<label for="countries"
							class="block mb-2 text-sm font-medium text-gray-900">Niveau</label>
						<select id="countries" name="levelId"
							class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500">
							<option value="all">Tout</option>
							
							<% 
							
							String selectedLevel = request.getParameter("levelId");
							
							Integer levelId = null;
							try {
								levelId = Integer.parseInt(selectedLevel); 
							} catch(Exception e) {
								
							}							
							%>
							
							<%
							for (Level level : levels) {
							%>
							<option <%= levelId != null && levelId == level.getId() ? "selected" : ""%> value="<%=level.getId()%>">
								<%=level.getName()%>
							</option>
							<%
							}
							%>
						</select>
					</div>

					<div>
						<label for="countries"
							class="block mb-2 text-sm font-medium text-gray-900">Etablissement</label>
						<select id="countries" name="facultyId"
							class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500">
							<option value="all">Tout</option>
							
							<% 
							
							String selectedFaculty = request.getParameter("facultyId");
							
							Integer facultyId = null;
							try {
								facultyId = Integer.parseInt(selectedFaculty); 
							} catch(Exception e) {
								
							}							
							%>
							
							<%
							for (Faculty faculty : faculties) {
							%>
							<option  <%= facultyId != null && facultyId == faculty.getId() ? "selected" : ""%> value="<%=faculty.getId()%>">
								<%=faculty.getName()%>
							</option>
							<%
							}
							%>
						</select>
					</div>
				</div>


			</form>

		</div>





		<div class="mt-5">
			Résultats : <span class="font-bold"><%=students.size()%> </span>
			étudiants
		</div>

		<div class="flex flex-col gap-4 pt-5">
			<%
			for (int i = 0; i < students.size(); i++) {
			%>

			<div class="flex flex-col gap-5 rounded-md border p-5">
				<div class="flex justify-between">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">N° matricule</div>
						<div class="font-semibold text-slate-800"><%=students.get(i).getId()%></div>
					</div>

					<div class="flex gap-1">
						<% if (levelAmountsAvailability.get(students.get(i).getLevelId()) == true) { %>
							<a href="payments/new?studentId=<%=students.get(i).getId()%>"
								class="mb-2 me-2 rounded-full bg-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Payer</a>
						<% } %>
						<a href="students/edit?studentId=<%=students.get(i).getId()%>"
							class="mb-2 me-2 rounded-full bg-green-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-green-800 focus:outline-none focus:ring-4 focus:ring-green-300 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">Modifier</a>
						<a href="students/delete?studentId=<%=students.get(i).getId()%>"
							class="mb-2 me-2 rounded-full border border-red-700 px-5 py-2.5 text-center text-sm font-medium text-red-700 hover:bg-red-800 hover:text-white focus:outline-none focus:ring-4 focus:ring-red-300 dark:border-red-500 dark:text-red-500 dark:hover:bg-red-600 dark:hover:text-white dark:focus:ring-red-900">Supprimer</a>
					</div>
				</div>

				<div class="flex flex-col">
					<div class="text-sm text-slate-700">Nom</div>
					<div class="font-semibold text-slate-800">
						<%=students.get(i).getName()%>
					</div>
				</div>

				<div class="flex gap-10">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Email</div>
						<div class="font-semibold text-slate-800"><%=students.get(i).getEmail()%></div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Date de naissance</div>
						<div class="font-semibold text-slate-800"><%=students.get(i).getBirthDate()%></div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Sexe</div>
						<div class="font-semibold text-slate-800"><%=students.get(i).getGender().getLabel()%>
						</div>
					</div>
				</div>

				<div class="flex gap-10">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Institution</div>
						<div class="font-semibold text-slate-800"><%=students.get(i).getFaculty().getName()%>
						</div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Niveau</div>
						<div class="font-semibold text-slate-800"><%=students.get(i).getLevel().getName()%>
						</div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Année universitaire</div>
						<div class="font-semibold text-slate-800"><%=students.get(i).getSession().getYear()%>
							-
							<%=students.get(i).getSession().getYear() + 1%>
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