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
<title>Nouvel étudiant</title>
</head>
<body>



	<main class="px-3 py-5 sm:px-40 md:px-60 lg:px-80">
		<h1 class="text-3xl font-medium">Ajouter un étudiant</h1>
		<p class="mb-5 text-slate-600">Veuillez remplir les champs
			suivants pour ajouter un étudiant</p>

		<form>
			<div class="mb-6 flex flex-col gap-5">
				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">N°
						matricule</label> <input type="text" id="first_name"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="2413" required />
				</div>

				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">Nom</label> <input
						type="text" id="first_name"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="John Doe" required />
				</div>

				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">Email</label>
					<input type="email" id="first_name"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="John Doe" required />
				</div>

				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">Date
						de naissance</label> <input type="date" id="first_name"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="John Doe" required />
				</div>

				<div>
					<div class="mb-2">Genre</div>
					<div class="flex items-center gap-4">
						<div class="flex items-center">
							<input checked id="default-radio-1" type="radio" value=""
								name="default-radio"
								class="h-4 w-4 border-gray-300 bg-gray-100 text-blue-600 focus:ring-blue-500" />
							<label for="default-radio-1"
								class="ms-2 text-sm font-medium text-gray-900">Homme</label>
						</div>
						<div class="flex items-center">
							<input id="default-radio-2" type="radio" value=""
								name="default-radio"
								class="h-4 w-4 border-gray-300 bg-gray-100 text-blue-600 focus:ring-blue-500" />
							<label for="default-radio-2"
								class="ms-2 text-sm font-medium text-gray-900">Femme</label>
						</div>
					</div>
				</div>

				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">Institution</label>
					<input type="text" id="first_name"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="John Doe" required />
				</div>

				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">Niveau</label>
					<input type="text" id="first_name"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="John Doe" required />
				</div>

				<div>
					<label for="first_name"
						class="mb-2 block text-sm font-medium text-gray-900">Année
						universitaire</label> <input type="text" id="first_name"
						class="bg-gray block w-full rounded-lg border border-gray-300 p-2.5 text-sm text-gray-900 focus:border-blue-500 focus:ring-blue-500"
						placeholder="John Doe" required />
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