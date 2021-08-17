<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <link href="styles.css" rel="stylesheet" type="text/css">
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2>

<form action="meals" method="post">
    <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.Meal"/>
    <c:set var="meal" value="${requestScope.meal}"/>
    <c:if test="${not empty meal}">
        <input type="hidden" name="id" value="${meal.id}"/>
        <c:set var="dateTime" value="${meal.dateTime}"/>
        <c:set var="description" value="${meal.description}"/>
        <c:set var="calories" value="${meal.calories}"/>
    </c:if>
    <div>
            <label>DateTime:</label>
            <input type="datetime-local" name="dateTime" value="<c:out default="" value="${dateTime}"/>">
    </div>
    <div>
            <label>Description:</label>
            <input type="text" name="description" value="<c:out default="" value="${description}"/>">
    </div>
    <div class="tableRow">
            <label>Calories:</label>
            <input type="number" name="calories" value="<c:out default="" value="${calories}"/>">
    </div>
    <input type="submit" value="Save"/>
    <input action="action" onclick="window.history.go(-1); return false;" type="submit" value="Cancel"/>
</form>

</body>
</html>
