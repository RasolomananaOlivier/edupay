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
<title>Modification du montant</title>
</head>
<body>



	<main class="px-3 py-5 sm:px-40 md:px-60 lg:px-80">
		<h1 class="text-3xl font-medium">Modifier le montant</h1>
		<p class="mb-5 text-slate-600">Veuillez remplir les champs
			suivants pour modifier le montant</p>

		<form>
			<div class="mb-6 flex flex-col gap-5">
				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">Identifiant</label> <input type="text" id="first_name"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="2413" required />
				</div>

				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">Niveau</label> <input
						type="text" id="first_name"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="M1" required />
				</div>

				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">Montant</label>
					<input type="email" id="first_name"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="22,000" required />
				</div>
			</div>

			<div class="flex justify-end">
			<button type="submit"
				class="w-full rounded-full bg-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 sm:w-auto dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Sauvegarder</button>
			</div>
			
		</form>
	</main>



	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.js"></script>
</body>
</html>