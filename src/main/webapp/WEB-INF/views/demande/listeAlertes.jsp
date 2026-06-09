<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<h3>Liste des demandes avec alertes</h3>

<table class="table table-striped">
    <thead>
        <tr>
            <th>Référence</th>
            <th>Client</th>
            <th>Commune</th>
            <th>Date Demande</th>
            <th>Durée totale (h)</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="demande" items="${demandes}">
            <tr class="table-primary">
                <td>${demande.reference}</td>
                <td>${demande.client.nom}</td>
                <td>${demande.commune.nom}</td>
                <td>${demande.dateDemande}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty dureeTotaleMap[demande.reference]}">
                            <fmt:formatNumber value="${dureeTotaleMap[demande.reference]}" maxFractionDigits="2" /> h
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <c:if test="${not empty alertesMap[demande.reference]}">
                <tr>
                    <td colspan="5" style="padding-left: 50px;">
                        <table class="table table-bordered table-sm mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>Alerte</th>
                                    <th>Transition</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="alerte" items="${alertesMap[demande.reference]}">
                                    <tr>
                                        <td>
                                        <span class="badge ${alerte.alerte == 'Rouge' ? 'bg-danger' : (alerte.alerte == 'Orange' ? 'bg-warning' : (alerte.alerte == 'Jaune' ? 'bg-warning text-dark' : (alerte.alerte == 'Vert' ? 'bg-success' : 'bg-info')))}">
                                                ${alerte.alerte}
                                            </span>
                                        </td>
                                        <td>${alerte.statut1.libelle} → ${alerte.statut2.libelle}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </tbody>
</table>
