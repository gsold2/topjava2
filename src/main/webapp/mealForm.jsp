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
    <c:if test="${not empty param.id}">
        <input type="hidden" name="id" value="${param.id}"/>
    </c:if>
    <div>
        <label>DateTime:</label>
        <input type="datetime-local" name="dateTime" value="${requestScope.dateTime}">
    </div>
    <div>
        <label>Description:</label>
        <input type="text" name="description" value="${requestScope.description}">
    </div>
    <div class="tableRow">
        <label>Calories:</label>
        <input type="number" name="calories" value="${requestScope.calories}">
    </div>
    <input type="submit" value="Save"/>
    <input onclick="window.history.go(-1); return false;" type="button" value="Cancel"/>
</form>

</body>
</html>
