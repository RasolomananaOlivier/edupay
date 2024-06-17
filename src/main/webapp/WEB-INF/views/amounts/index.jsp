<%@page import="model.MonthAmount"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
List<MonthAmount> monthAmounts = (List<MonthAmount>) request.getAttribute("monthAmounts");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.css"
	rel="stylesheet" />
<title>Liste des montants</title>
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


		<h1 class="text-3xl font-bold">Liste des montants</h1>


		<div class="flex justify-between items-end">
			<div class="text-slate-600 mt-1">Retrouver ici la liste des
				montants</div>

			<a href="amounts/new"
				class="text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Nouveau montant</a>
		</div>

		<div class="flex flex-col gap-4 pt-5">
		
			<% for(MonthAmount amount : monthAmounts) { %>
		
			<div class="flex flex-col gap-5 rounded-md p-5 border">
				<div class="flex justify-between">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Identifiant</div>
						<div class="font-semibold text-slate-800"> <%= amount.getId() %> </div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Niveau</div>
						<div class="font-semibold text-slate-800"> <%= amount.getLevel().getName() %> </div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Montant</div>
						<div class="font-semibold text-slate-800"><%= amount.getValue() %> AR</div>
					</div>
				</div>

				<div class="flex">
					<a href="amounts/edit?amountId=<%= amount.getId() %>"
						class="text-white bg-green-700 hover:bg-green-800 focus:outline-none focus:ring-4 focus:ring-green-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">Modifier</a>
					<a href="amounts/delete?amountId=<%= amount.getId() %>"
						class="text-red-700 hover:text-white border border-red-700 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:border-red-500 dark:text-red-500 dark:hover:text-white dark:hover:bg-red-600 dark:focus:ring-red-900">Supprimer</a>
				</div>
			</div>
		
		<% } %>

		</div>
	</main>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.js"></script>

</body>
</html>