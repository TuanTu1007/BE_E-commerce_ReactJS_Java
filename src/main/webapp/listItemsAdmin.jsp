<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Product List</h1>
    <table border="1">
        <tr>
            <th>Product ID</th>
            <th>Name</th>
            <th>Image</th>
            <th>Price</th>
            <th>Category</th>
            <th>Bestseller</th>
            <th>Action</th>
        </tr>
        <c:forEach var="product" items="${productList}">
            <tr>
                <td>${product.productId}</td>
                <td>${product.name}</td>
                <td><img src="${product.imageUrl}" alt="Product Image" width="100"></td>
                <td>${product.price}</td>
                <td>${product.category.categoryName}</td>
                <td>${product.bestseller ? "Yes" : "No"}</td>
                <td>
                    <form action="<%=request.getContextPath()%>/viewProductsAdminController" method="post">
					    <input type="hidden" name="productId" value="${product.productId}">
					    <button type="submit" style="cursor: pointer;">XÓA</button>
					</form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
