
<%--
<c:if test="${testid != null}">
    <p>${testid}</p>
</c:if> --%>
<%-- <c:if test="${success != null}">
    <p>${success}</p>
</c:if> --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${success != null}">
        <h1>${success}</h1>
    </c:when>
    <c:when test="${error != null}">
        <p>${error}</p>
    </c:when>
    <c:otherwise>
        <form action="${pageContext.request.contextPath}/demande/creer" method="post">
            <label for="dateDemande">Date et Heure de la demande (optionnel)</label>
            <input type="datetime-local" name="dateDemande" class="form-control">

            <label for="client">Choisissez un client</label>
            <select name="clientId">
                <c:forEach var="client" items = "${clients}">
                    <option value="${client.id}">${client.nom}</option>
                </c:forEach>
            </select>
            <label for="commune">Choisissez une commune</label>
            <select name="communeId">
                <c:forEach var="commune" items = "${communes}">
                    <option value="${commune.id}">${commune.nom}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-primary">Inserer une demande</button>
        </form>
    </c:otherwise>
</c:choose>