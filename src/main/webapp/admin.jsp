<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
        /* Add some basic styling */
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        .button-container {
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .button-container a {
            text-decoration: none;
        }
        .btn {
            padding: 15px 25px;
            font-size: 18px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            background-color: #4CAF50;
            color: white;
            transition: background-color 0.3s ease;
        }
        .btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<h1>Welcome to Admin Dashboard</h1>

<div class="button-container">
    <a href="addProductAdmin.jsp"><button class="btn">Add Items</button></a>
    <a href="<%=request.getContextPath()%>/viewProductsAdminController"><button class="btn">List Items</button></a>
    <a href="<%=request.getContextPath()%>/trackOrderController"><button class="btn">Order Items</button></a>
    <a href="dashboard.jsp"><button class="btn">Dashboard</button></a>
</div>

</body>
</html>
