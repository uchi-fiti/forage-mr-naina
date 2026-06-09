function ajouterDetail() {
    const container = document.getElementById("details-container");

    const div = document.createElement("div");

    div.innerHTML = `
        <hr>
        Libelle: <input type="text" name="libelle">
        Quantite: <input type="number" name="quantite">
        Unite: <input type="text" name="unite">
        Prix unitaire: <input type="number" step="0.1" name="prix_unitaire">
        <button type="button" class="bouton-supprimer">Supprimer</button>
    `;
    container.appendChild(div);
    const boutonSupprimer = div.querySelector(".bouton-supprimer");
    boutonSupprimer.addEventListener("click", function() {
        div.remove();
    });
}
function afficherJSON(response) {
    const jsonContainer = document.getElementById("json-container");
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
    const json = await response.json();
    return json;
}
const demandeInput = document.getElementById("demandeInput");
demandeInput.addEventListener("blur", async function () {
    const refDemande = demandeInput.value;
    const response = await getDetailsDemande(refDemande);
    afficherJSON(response);
});

// should always clean cookies by cmd + shift + r after modifying this