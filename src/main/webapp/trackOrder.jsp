<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Track Orders</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        img {
            max-width: 100px;
            max-height: 100px;
        }
    </style>
</head>
<body>
<h1>Track Orders</h1>
<c:set var="currentOrderId" value="" />
<table>
    <thead>
        <tr>
            <th>Order Detail ID</th>
            <th>Order ID</th>
            <th>Product Name</th>
            <th>Product Image</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Order Date</th>
            <th>Status</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="orderDetail" items="${orderDetailsList}">
            <!-- Kiểm tra nếu order_id khác order_id hiện tại, thì hiển thị total_amount -->
            <c:if test="${orderDetail.orderId != currentOrderId}">
                <tr>
                    <td colspan="6" style="text-align:right; font-weight:bold;">Total Amount: ${orderDetail.totalAmount}</td>
                    <td colspan="3"></td>
                </tr>
                <c:set var="currentOrderId" value="${orderDetail.orderId}" />
            </c:if>

            <tr>
                <td>${orderDetail.orderDetailId}</td>
                <td>${orderDetail.orderId}</td>
                <td>${orderDetail.productName}</td>
                <td><img src="${orderDetail.productImage}" alt="${orderDetail.productName}" /></td>
                <td>${orderDetail.quantity}</td>
                <td>${orderDetail.price}</td>
                <td>${orderDetail.orderDate}</td>
                <td>${orderDetail.orderStatus}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
