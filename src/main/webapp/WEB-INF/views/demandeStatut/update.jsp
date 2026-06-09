<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:if test="${error != null}">
    <div class="alert alert-danger">
        ${error}
    </div>
</c:if>
<c:if test="${success != null}">
    <div class="alert alert-success">
        ${success}  
    </div>
</c:if>
<h1>Update demande statut</h1>
<form action="${pageContext.request.contextPath}/demandeStatut/update" method="post">
<input type="hidden" name="idDemandeStatut" value="${demandeStatut.id}">
    <div class="mt-3 mb-3">
        <label for="refDemande">Reference de la demande</label>
        <input type="text" name="refDemande" class="form-control" value="${demandeStatut.demande.reference}">
    </div>
    <div class="mb-3">
        <label for="dateChangementStatut">Date et heure</label>
        <input type="datetime-local" name="dateChangementStatut" class="form-control" value="${demandeStatut.dateChangementStatut}">
    </div>
    <div class="mb-3">
        <label for="statuts">Statut de la demande</label>
        <select name="idStatut" class="form-select">
            <c:forEach var="statut" items="${statuts}"> 
                <option value="${statut.id}" ${demandeStatut.statut.id == statut.id ? "selected" : ""}>${statut.libelle}</option>
            </c:forEach>
        </select>
    </div>

    <button type="submit" class="btn btn-primary">Enregistrer les modifications</button>
</form>
