<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:forEach var="devi" items="${devis}">
    <h3>Devis numero ${devi.reference} :  ${devi.description}</h3>
    <table>
        <tr>
            <th>Libelle</th>
            <th>Quantite</th>
            <th>Unite</th>
            <th>Prix unitaire</th>
        </tr>
        <c:forEach var="detailsDevi" items="${devi.detailsDevis}">
            <tr>
                <td>${detailsDevi.libelle}</td>
                <td>${detailsDevi.quantite}</td>
                <td>${detailsDevi.unite}</td>     
                <td>${detailsDevi.prixUnitaire}</td>
            </tr>
        </c:forEach>
    </table>
    <hr>
</c:forEach>