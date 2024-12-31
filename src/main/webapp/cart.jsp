<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="Entity.cartEntity" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Shopping Cart</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h2 {
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table th, table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }
        table th {
            background-color: #f4f4f4;
        }
        img {
            max-width: 100px;
            height: auto;
        }
        .empty-cart {
            text-align: center;
            font-size: 1.2em;
            color: #777;
        }
        .order-button {
            text-align: center;
            margin-top: 20px;
        }
        .order-button button {
            padding: 10px 20px;
            font-size: 1em;
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
        }
        .order-button button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <h2>Your Shopping Cart</h2>

    <%
        List<cartEntity> cartItems = (List<cartEntity>) request.getAttribute("cartItems");
        if (cartItems == null || cartItems.isEmpty()) {
    %>
        <p class="empty-cart">Your cart is empty.</p>
    <%
        } else {
    %>
    
    <table>
        <tr>
            <th>Product Name</th>
            <th>Image</th>
            <th>Price</th>
            <th>Size</th>
            <th>Quantity</th>
            <th>Action</th>
        </tr>
        <%
            for (cartEntity item : cartItems) {
        %>
        <tr>
            <td><%= item.getProduct().getName() %></td>
            <td>
                <img src="<%= item.getProduct().getImageUrl() %>" alt="Product Image">
            </td>
            <td>$<%= String.format("%.2f", item.getProduct().getPrice()) %></td>
            <td><%= item.getSizeId() %></td>
            <td><%= item.getQuantity() %></td>
            <td>
                <!-- Form cho hành động xóa -->
                <form action="<%=request.getContextPath()%>/cartController" method="post">
                    <input type="hidden" name="cartId" value="<%= item.getCartId() %>">
                    <input type="hidden" name="action" value="remove">
                    <button type="submit" style="cursor: pointer;">XÓA</button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>
<form action="<%=request.getContextPath()%>/proceedToCheckout.jsp" method="get">
    <div class="order-button">
        <button type="submit">Process Check out</button>
    </div>
</form>

    <%
        }
    %>
</body>
</html>
