<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Subcategories</title>
</head>
<body>
    <h1>Subcategories</h1>

    <ul>
        <c:forEach var="subcategory" items="${subcategories}">
            <li>${subcategory.subcategoryName}</li>
        </c:forEach>
    </ul>

    <a href="categoriesController">Back to Categories</a>
</body>
</html>
