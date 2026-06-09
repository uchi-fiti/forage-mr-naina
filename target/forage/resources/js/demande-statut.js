function afficherJSONDemandeStatut(response) {
    const jsonContainer = document.getElementById("demandeStatutJson");
    jsonContainer.classList.remove("hidden");
    jsonContainer.innerText = JSON.stringify(response, null, 4);
}

async function getDetailsDemande(refDemande) {
    const params = new URLSearchParams();
    params.append("refDemande", refDemande);
    const response = await fetch("/forage/demande/details/json", {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: params.toString()
    });
    return await response.json();
}

const demandeStatutInput = document.getElementById("demandeStatutRefInput");
if (demandeStatutInput) {
    demandeStatutInput.addEventListener("blur", async function () {
        const refDemande = demandeStatutInput.value;
        if (!refDemande) {
            return;
        }
        const response = await getDetailsDemande(refDemande);
        afficherJSONDemandeStatut(response);
    });
}
