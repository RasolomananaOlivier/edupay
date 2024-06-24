<%@page import="com.edupay.model.EquipmentAmount"%>
<%@page import="java.util.List"%>
<%@page import="com.edupay.util.CurrencyFormatter"%>
<%@ page contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
List<EquipmentAmount> equipments = (List<EquipmentAmount>) request.getAttribute("equipments");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.css"
	rel="stylesheet" />
<title>Liste des équipements</title>
</head>
<body>
	<jsp:include page="/WEB-INF/components/header.jsp" />

	<main class="px-5 py-3">


		<h1 class="text-3xl font-bold">Liste des équipements</h1>


		<div class="flex justify-between items-end">
			<div class="text-slate-600 mt-1">Retrouver ici la liste des
				équipements</div>

			<a href="<%= request.getContextPath() %>/equipment-amounts/new"
				class="text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Nouvel équipement</a>
		</div>

		<div class="flex flex-col gap-4 pt-5">
		
			<% for(EquipmentAmount equipment : equipments) { %>
		
			<div class="flex flex-col gap-5 rounded-md p-5 border">
				<div class="flex justify-between">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Identifiant</div>
						<div class="font-semibold text-slate-800"> <%= equipment.getId() %> </div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Niveau</div>
						<div class="font-semibold text-slate-800"> <%= equipment.getLevel().getName() %> </div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Montant</div>
						<div class="font-semibold text-slate-800"><%= CurrencyFormatter.format(equipment.getValue()) %></div>
					</div>
				</div>

				<div class="flex">
					<a href="<%= request.getContextPath() %>/equipment-amounts/edit?amountId=<%= equipment.getId() %>"
						class="text-white bg-green-700 hover:bg-green-800 focus:outline-none focus:ring-4 focus:ring-green-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">Modifier</a>
					<a href="<%= request.getContextPath() %>/equipment-amounts/delete?amountId=<%= equipment.getId() %>"
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