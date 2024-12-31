<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Categories</title>
</head>
<body>
    <h1>Categories</h1>

    <ul>
        <c:forEach var="category" items="${categories}">
            <li>
                ${category.categoryName}
                <form method="post" action="categoriesController">
                    <input type="hidden" name="categoryId" value="${category.categoryId}">
                    <input type="submit" value="View Subcategories">
                </form>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
