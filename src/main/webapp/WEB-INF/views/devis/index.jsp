<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<h1>Page de creation de devis</h1>
<c:choose>
    <c:when test="${success != null}">
        <h3>${success}</h3>
    </c:when>
    <c:otherwise>
        <c:if test="${error != null}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/devis/saveDevis" method="post">
            <div>
                Date et Heure (optionnel) :
                <input type="datetime-local" name="dateDevis">
            </div>
            <div>
                Description :
                <input type="text" name="description">
            </div>
            <div>
                Reference de la demande :
                <input type="text" name="refDemande" id="demandeInput">
            </div>
            <div class="json-container hidden" id="json-container">
                
            </div>
            <div>
                Type du devis :
                <select name="idType">
                    <c:forEach var="type" items="${types}">
                        <option value="${type.id}">${type.libelle}</option>
                    </c:forEach>
                </select>
            </div>
            <hr>

            <h3>Les details de votre devis seront ajoutés ici</h3>
            <div id="details-container">

            </div>

            <button type="button" onclick="ajouterDetail()">
                Ajouter detail
            </button>

            <button type="submit">
                Enregistrer
            </button>
        </form>
    </c:otherwise>
</c:choose>

<script src="${pageContext.request.contextPath}/resources/js/devis.js"></script>


