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
            <th>Description</th>
            <th>Price</th>
            <th>Bestseller</th>
            <th>Action</th>
        </tr>
        <c:forEach var="product" items="${productList}">
            <tr>
                <td>${product.productId}</td>
                <td>${product.name}</td>
                <td><img src="${product.imageUrl}" alt="Product Image" width="100"></td>
                <td>${product.description}</td>
                <td>${product.price}</td>
                <td>${product.bestseller ? "Yes" : "No"}</td>
                <td>
                    <!-- Form thêm vào giỏ hàng -->
                    <form action="<%=request.getContextPath()%>/addToCartController" method="post">
                        <input type="hidden" name="id" value="${product.productId}">
                        <input type="hidden" name="title" value="${product.name}">
                        <input type="hidden" name="image" value="${product.imageUrl}">
                        <input type="hidden" name="description" value="${product.description}">
                        <input type="hidden" name="price" value="${product.price}">
                        <input type="hidden" name="bestseller" value="${product.bestseller}">
                        <!-- Dropdown for selecting size -->
                        <select id="size-${product.productId}" name="sizeName">
                            <c:forEach var="size" items="${product.sizes}">
                                <option value="${size}">${size}</option>
                            </c:forEach>
                        </select>
                        <!-- Add to Cart Button -->
                        <button type="submit" style="cursor: pointer;">Add to Cart</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
