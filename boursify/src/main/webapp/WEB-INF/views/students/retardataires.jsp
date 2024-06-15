<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<div class="flex justify-between p-3">
		<div class="font-bold text-blue-600 font-mono">Boursify</div>

		<div class="flex justify-between gap-3">
			<a href="students/index.jsp"> Etudiants </a> <a> Montant </a> <a>
				Paiement </a>
		</div>
	</div>

	<main class="px-5 py-3">
		<h1 class="text-3xl font-bold">Liste des retardataires</h1>

		<div class="flex items-center justify-between">
			<div class="mt-1 text-slate-600">Retrouver ici la liste des
				étudiants retardataires</div>

			<a href="create.jsp"
				class="mb-2 me-2 rounded-full bg-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Nouvel
				étudiant</a>
		</div>

		<div class="bg-slate-100 rounded-lg px-4 py-5 my-4">
			<div class="flex gap-2 items-end">
				<div class="flex flex-col gap-1 w-full">
					<label for="countries"
						class="block mb-2 text-sm font-medium text-gray-900">Selectionnez
						un mois</label> <select id="countries"
						class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
						<option selected>Choose a country</option>
						<option value="US">United States</option>
						<option value="CA">Canada</option>
						<option value="FR">France</option>
						<option value="DE">Germany</option>
					</select>
				</div>

				<div class="mb-2">
					<a href="create.jsp"
						class="mb-2 me-2 rounded-full bg-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Validater</a>
				</div>
			</div>
		</div>





		<div class="mt-5">
			Résultats : <span class="font-bold">50</span> étudiants
		</div>

		<div class="flex flex-col gap-4 pt-5">
			<div class="flex flex-col gap-5 rounded-md border p-5">
				<div class="flex justify-between">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">N° matricule</div>
						<div class="font-semibold text-slate-800">HF4567</div>
					</div>
				</div>

				<div class="flex flex-col">
					<div class="text-sm text-slate-700">Nom</div>
					<div class="font-semibold text-slate-800">Rasolomanana
						Herimanitra Olivier</div>
				</div>

				<div class="flex gap-10">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Email</div>
						<div class="font-semibold text-slate-800">Herimanitra@gmail.com</div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Date de naissance</div>
						<div class="font-semibold text-slate-800">01/01/2024</div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Sexe</div>
						<div class="font-semibold text-slate-800">Homme</div>
					</div>
				</div>

				<div class="flex gap-10">
					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Institution</div>
						<div class="font-semibold text-slate-800">ENI</div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Niveau</div>
						<div class="font-semibold text-slate-800">L3</div>
					</div>

					<div class="flex flex-col">
						<div class="text-sm text-slate-700">Année universitaire</div>
						<div class="font-semibold text-slate-800">2023 - 2024</div>
					</div>
				</div>
			</div>
		</div>
	</main>


	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.js"></script>

</body>
</html>