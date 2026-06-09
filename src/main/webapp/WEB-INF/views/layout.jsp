<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - For'Azy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-dark px-3">
        <span class="navbar-brand fw-bold">For'Azy</span>
    </nav>
    <div class="container-fluid mt-3">
        <div class="row">
            <div class="col-2">
                <div class="bg-white rounded shadow-sm p-3">
                    <p class="text-muted fw-semibold small mb-2">Menu</p>
                    <nav class="nav flex-column nav-pills">
                        <a href="${pageContext.request.contextPath}/" class="nav-link ${currentPage == 'home' ? 'text-white active' : 'text-dark'}">Accueil</a>
                        <a href="${pageContext.request.contextPath}/demande/form" class="nav-link ${currentPage == 'formDemande' ? 'text-white active' : 'text-dark'}">Creer une demande</a>
                        <a href="${pageContext.request.contextPath}/demande/liste" class="nav-link ${currentPage == 'listeDemande' ? 'text-white active' : 'text-dark'}">Liste des demandes</a>
                        <a href="${pageContext.request.contextPath}/demande/liste-alertes" class="nav-link ${currentPage == 'listeDemandeAlertes' ? 'text-white active' : 'text-dark'}">Demandes avec alertes</a>
                        <a href="${pageContext.request.contextPath}/devis/form" class="nav-link ${currentPage == 'formDevis' ? 'text-white active' : 'text-dark'}">Creer un devis</a>
                        <a href="${pageContext.request.contextPath}/devis/liste" class="nav-link ${currentPage == 'listeDevis' ? 'text-white active' : 'text-dark'}">Liste des devis</a>
                        <a href="${pageContext.request.contextPath}/demandeStatut/formAjout" class="nav-link ${currentPage == 'demandeStatutFormAjout' ? 'text-white active' : 'text-dark'}">Créer demande statut</a>
                        <a href="${pageContext.request.contextPath}/demandeStatut/formUpdate/${demandeStatut != null ? demandeStatut.id : ""}" class="nav-link ${currentPage == 'demandeStatutFormUpdate' ? 'text-white active' : 'hidden'}">Update demande statut id ${demandeStatut != null ? demandeStatut.id : ""} </a>
                        <a href="${pageContext.request.contextPath}/demandeStatut/liste" class="nav-link ${currentPage == 'demandeStatutListe' ? 'text-white active' : 'text-dark'}">Liste demande statuts</a>
                    </nav>
                </div>
            </div>
            <div class="col-10">
                <div class="bg-white rounded shadow-sm p-4">
                    <h1>ETU004171</h1>
                    <jsp:include page="${content}" />
                </div>
            </div>
        </div>
    </div>
</body>
</html>