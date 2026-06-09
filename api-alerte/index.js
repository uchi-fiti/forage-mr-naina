const refDemandeSelect = document.getElementById("refDemande");
const filterTextInput = document.getElementById("filterText");
const alertsBody = document.getElementById("alertsBody");
const btnRefresh = document.getElementById("btnRefresh");


let allAlertsData = [];

async function loadDemandes() {
    try {
        const response = await fetch(`${API_BASE_URL}/demandes`);
        if (!response.ok) throw new Error("Impossible de charger les demandes");
        
        const demandes = await response.json();
        refDemandeSelect.innerHTML = '<option value="">Toutes les demandes avec alertes</option>';
        
        demandes.forEach(demande => {
            const option = document.createElement("option");
            option.value = demande.reference;
            option.textContent = `${demande.reference} - ${demande.client.nom}`;
            refDemandeSelect.appendChild(option);
        });
    } catch (error) {
        console.error("Erreur chargement demandes:", error);
    }
}

async function fetchAllAlerts() {
    try {
        const response = await fetch(`${API_BASE_URL}/all-alertes`);
        if (!response.ok) throw new Error("Erreur serveur lors de la récupération des alertes.");
        allAlertsData = await response.json();
        renderTable(allAlertsData);
    } catch (error) {
        showError(error.message);
    }
}

async function fetchAlertsForOne(refDemande) {
    try {
        const usp = new URLSearchParams();
        usp.append("refDemande", refDemande);

        const demandeRes = await fetch(`${API_BASE_URL}/demandes`);
        const demandes = await demandeRes.json();
        const demandeInfo = demandes.find(d => d.reference === refDemande);

        const response = await fetch(`${API_BASE_URL}/all-by-refdemande`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: usp.toString()
        });

        if (!response.ok) throw new Error("Erreur lors de la récupération.");
        const alertes = await response.json();

        const singleData = [{
            reference: refDemande,
            client: demandeInfo ? demandeInfo.client.nom : "Inconnu",
            commune: demandeInfo ? demandeInfo.commune.nom : "Inconnue",
            dateDemande: demandeInfo ? demandeInfo.dateDemande : "-",
            alertes: alertes
        }];
        
        allAlertsData = singleData;
        renderTable(singleData);
    } catch (error) {
        showError(error.message);
    }
}

function getBadgeClass(type) {
    switch (type.toLowerCase()) {
        case 'rouge': return 'badge-rouge';
        case 'orange': return 'badge-orange';
        case 'jaune': return 'badge-jaune';
        default: return 'badge-info';
    }
}

function renderTable(data) {
    const filterText = filterTextInput.value.toLowerCase();
    
    const filtered = data.filter(item => {
        return (
            item.reference.toLowerCase().includes(filterText) ||
            item.client.toLowerCase().includes(filterText) ||
            item.commune.toLowerCase().includes(filterText)
        );
    });

    if (filtered.length === 0) {
        alertsBody.innerHTML = '<tr><td colspan="5" class="text-center py-4 text-muted">Aucune alerte trouvée correspondant à vos critères.</td></tr>';
        return;
    }

    alertsBody.innerHTML = filtered.map(item => `
        <tr>
            <td class="fw-bold">${item.reference}</td>
            <td>${item.client}</td>
            <td>${item.commune}</td>
            <td>${formatDate(item.dateDemande)}</td>
            <td>
                ${item.alertes && item.alertes.length > 0 ? 
                    item.alertes.map(a => `
                        <div class="alert-item text-white ${getBadgeClass(a.alerte)}">
                            ${a.alerte} (${a.statut1.libelle} → ${a.statut2.libelle})
                        </div>
                    `).join('') : 
                    '<span class="text-muted small">Aucune alerte</span>'
                }
            </td>
        </tr>
    `).join('');
}

function formatDate(dateStr) {
    if (!dateStr) return "-";
    try {
        const d = new Date(dateStr);
        return d.toLocaleString('fr-FR', { dateStyle: 'short', timeStyle: 'short' });
    } catch(e) { return dateStr; }
}

function showError(msg) {
    alertsBody.innerHTML = `<tr><td colspan="5" class="text-center py-4 text-danger fs-5">${msg}</td></tr>`;
}

refDemandeSelect.addEventListener("change", (e) => {
    alertsBody.innerHTML = '<tr><td colspan="5" class="text-center py-5"><div class="spinner-border text-primary"></div></td></tr>';
    if (e.target.value === "") {
        fetchAllAlerts();
    } else {
        fetchAlertsForOne(e.target.value);
    }
});

filterTextInput.addEventListener("input", () => {
    renderTable(allAlertsData);
});

btnRefresh.addEventListener("click", () => {
    if (refDemandeSelect.value === "") {
        fetchAllAlerts();
    } else {
        fetchAlertsForOne(refDemandeSelect.value);
    }
});

loadDemandes();
fetchAllAlerts();