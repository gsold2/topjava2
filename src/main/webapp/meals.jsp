<%@ page import="java.time.LocalDateTime" %>
<%@ page import="static ru.javawebinar.topjava.util.TimeUtil.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <link href="styles.css" rel="stylesheet" type="text/css">
    <title>Meal list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<h4><a href="mealForm.jsp">Add Meal</a></h4>

<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <thead>

    <jsp:useBean id="mealToList" class="ru.javawebinar.topjava.model.MealTo"/>
    <c:set var="mealToList" value="${requestScope.mealsTo}"/>
    <c:forEach var="mealTo" items="${mealToList}">
        <tr style=color:${mealTo.excess != false ?  '#FF0000' : '#008000'}>
            <c:set var="dataTime" value="${mealTo.dateTime}"/>
            <td><%= DateTimeFormatter((LocalDateTime) pageContext.getAttribute("dataTime"))%>
            </td>
            <td><c:out value="${mealTo.description}"/></td>
            <td><c:out value="${mealTo.calories}"/></td>
            <td><a href="meals?action=update&id=${mealTo.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${mealTo.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
