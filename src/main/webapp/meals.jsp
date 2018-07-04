<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
</head>
<body class="bg-light">
<div class="container">
    <div class="row">
        <div class="col">
            <h1 class="h3">Meals</h1>

            <div class="table-responsive">
                <table class="table table-striped" style="border-collapse: separate">
                    <thead>
                    <tr>
                        <th scope="col">Дата/Время</th>
                        <th scope="col">Описание</th>
                        <th scope="col">Калории</th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="mealItem" items="${mealsWithExceed}">
                                <tr style="color:${mealItem.exceed ? '#f00' : '#0f0'}">
                                    <td>
                                        <fmt:parseDate
                                                value="${ mealItem.dateTime }"
                                                pattern="yyyy-MM-dd'T'HH:mm"
                                                var="parsedDateTime" type="both" />
                                        <fmt:formatDate
                                                pattern="dd.MM.yyyy HH:mm"
                                                value="${ parsedDateTime }" />
                                    </td>
                                    <td>${mealItem.description}</td>
                                    <td>${mealItem.calories}</td>
                                    <td class="text-center">
                                        <c:url var="loadLink" value="meals">
                                            <c:param name="command" value="LOAD" />
                                            <c:param name="mealId" value="${mealItem.id}" />
                                        </c:url>
                                        <a href="${loadLink}" style="color: inherit">
                                            <i class="fa fa-pencil"></i>
                                        </a>
                                    </td>
                                    <td class="text-center">
                                        <c:url var="deleteLink" value="meals">
                                            <c:param name="command" value="DELETE" />
                                            <c:param name="mealId" value="${mealItem.id}" />
                                        </c:url>
                                        <a href="${deleteLink}" style="color: inherit" onclick="if(!confirm('Are you sure you want to delete the entry')) return false">
                                            <i class="fa fa-remove"></i>
                                        </a>
                                    </td>
                                </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <a href="index.html" class="d-inline">Home</a> |
            <a href="#" onclick="window.location.href='add-meal-form.jsp'">Add Meal</a>
        </div>
    </div>
</div>

    <script src="js/bootstrap.min.js"></script>
</body>
</html>
