<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<h1>Liste des demandes</h1>
<c:choose>
	<c:when test="${empty demandes}">
		<p>Aucune demande enregistree.</p>
	</c:when>
	<c:otherwise>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Reference</th>
					<th>Client</th>
					<th>Commune</th>
					<th>Date</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="demande" items="${demandes}">
					<tr>
						<td>${demande.reference}</td>
						<td>${demande.client.nom}</td>
						<td>${demande.commune.nom}</td>
						<td>${demande.dateDemande}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>