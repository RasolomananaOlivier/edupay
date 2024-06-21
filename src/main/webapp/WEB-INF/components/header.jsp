<header class="flex justify-between p-3">
    <div class="font-bold text-blue-600 font-mono">Boursify</div>

    <div class="flex justify-between gap-3">
        <a href="<%= request.getContextPath() + "/students" %>">Etudiants</a>
        <a href="<%= request.getContextPath() + "/amounts" %>">Montants</a>
        <a href="<%= request.getContextPath() + "/equipment-amounts" %>">Equipements</a>
        <a href="<%= request.getContextPath() + "/payments" %>">Paiements</a>
    </div>
</header>