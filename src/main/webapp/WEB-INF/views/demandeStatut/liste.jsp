<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:if test="{success != null}">
    <div class="alert alert-success">
        ${success}
    </div>
</c:if>
<table class="table">
    <thead class="table-dark">
        <tr>
            <th>Reference demande</th>
            <th>Statut</th>
            <th>Date Changement Statut</th>
            <th>Duree (minutes)</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="demandeStatut" items="${demandeStatuts}">
            <tr>
                <td>${demandeStatut.demande.reference}</td>
                <td>${demandeStatut.statut.libelle}</td>
                <td>${demandeStatut.dateChangementStatut}</td>
                <td>${demandeStatut.dureeTravaillee}</td>
                <td><a href="${pageContext.request.contextPath}/demandeStatut/formUpdate/${demandeStatut.id}" class="btn btn-warning">Modifier</a></td>
                <td><a href="${pageContext.request.contextPath}/demandeStatut/delete/${demandeStatut.id}" class="btn btn-danger">Supprimer</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>