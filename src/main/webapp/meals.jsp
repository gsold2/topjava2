<%@ page import="java.time.LocalDateTime" %>
<%@ page import="static ru.javawebinar.topjava.util.TimeUtil.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

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
<h4>Add Meal</h4>

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

    <tbody>
    <jsp:useBean id="mealToList" class="ru.javawebinar.topjava.model.MealTo"/>
    <c:set var="mealToList" value='${requestScope.mealsTo}'/>
    <c:forEach var="mealTo" items="${mealToList}">
        <tr style=color:${mealTo.excess != false ?  '#008000': '#FF0000'}>
            <c:set var="dataTime" value='${mealTo.dateTime}'/>
            <td><%= DateTimeFormatter((LocalDateTime) pageContext.getAttribute("dataTime"))%></td>
            <td><c:out value="${mealTo.description}"/></td>
            <td><c:out value="${mealTo.calories}"/></td>
            <td>Update</td>
            <td>Delete</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
