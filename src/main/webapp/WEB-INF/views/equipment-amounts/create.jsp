<%@page import="model.Level"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
List<Level> levels = (List<Level>) request.getAttribute("levels");
List<Integer> disabledLevelIds = (List<Integer>) request.getAttribute("disabledLevelIds");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.css"
	rel="stylesheet" />
<title>Nouvel équipement</title>
</head>
<body>
	<jsp:include page="/WEB-INF/components/header.jsp" />
	
	<main class="px-3 py-5 sm:px-40 md:px-60 lg:px-80">
		<h1 class="text-3xl font-medium">Ajouter un équipement</h1>
		<p class="mb-5 text-slate-600">Veuillez remplir les champs
			suivants pour ajouter un équipement</p>

		<form action="store" method="post">
			<div class="mb-6 flex flex-col gap-5">
				<!-- <div>
					<label for="amountId"
						class="mb-2 block text-sm font-medium text-gray-900">Identifiant</label> 
						<input type="text" id="amountId" name="amountId"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="2413" required />
				</div> -->

				<div>
					<label for="level"
						class="mb-2 block text-sm font-medium text-gray-900">Niveau</label>
					<select id="countries" name="level" required
						class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
						<%
							for (Level level : levels) {
						%>
							<option
								value="<%=level.getId()%>"
								<%= disabledLevelIds.contains(level.getId()) ? "disabled" : "" %>
							>
								<%=level.getName()%>
							</option>
						<%
							}
						%>
					</select>
				</div>

				<div>
					<label for="amountValue"
						class="mb-2 block text-sm font-medium text-gray-900">Montant</label>
					<input type="number" id="amountValue" name="amountValue"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="22,000" required min="1" step="1" />
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