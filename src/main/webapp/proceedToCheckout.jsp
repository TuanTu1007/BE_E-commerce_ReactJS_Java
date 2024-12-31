<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        .container {
            display: flex;
            justify-content: space-between;
            padding: 20px;
            max-width: 1200px;
            margin: auto;
        }

        .form-section, .cart-section {
            width: 48%;
        }

        h2 {
            font-size: 1.5em;
            margin-bottom: 10px;
            border-bottom: 2px solid #000;
            padding-bottom: 5px;
            display: inline-block;
        }

        .form-group {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-bottom: 15px;
        }

        .form-group input {
            width: calc(50% - 10px);
            padding: 10px;
            font-size: 1em;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .form-group input.full-width {
            width: 100%;
        }

        .cart-totals {
            margin-top: 20px;
            font-size: 1.1em;
        }

        .cart-totals div {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .payment-method {
            margin-top: 20px;
        }

        .payment-method div {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .payment-method div img {
            height: 20px;
            margin-right: 10px;
        }

        .payment-method input {
            margin-right: 10px;
        }

        .place-order {
            margin-top: 20px;
        }

        .place-order button {
            padding: 15px 30px;
            background-color: #000;
            color: #fff;
            border: none;
            font-size: 1.1em;
            border-radius: 5px;
            cursor: pointer;
        }

        .place-order button:hover {
            background-color: #444;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Delivery Information Section -->
        <div class="form-section">
            <h2>Delivery Information</h2>
            <form action="<%=request.getContextPath()%>/placeOrderController" method="post">
                <div class="form-group">
                    <input type="text" name="firstName" placeholder="First Name" required>
                    <input type="text" name="lastName" placeholder="Last Name" required>
                </div>
                <div class="form-group">
                    <input type="email" name="email" placeholder="Email Address" class="full-width" required>
                </div>
                <div class="form-group">
                    <input type="text" name="street" placeholder="Street" class="full-width" required>
                </div>
                <div class="form-group">
                    <input type="text" name="city" placeholder="City" required>
                    <input type="text" name="state" placeholder="State" required>
                </div>
                <div class="form-group">
                    <input type="text" name="zipcode" placeholder="Zipcode" required>
                    <input type="text" name="country" placeholder="Country" required>
                </div>
                <div class="form-group">
                    <input type="text" name="phone" placeholder="Phone" class="full-width" required>
                </div>

                <!-- Cart Section -->
                <div class="cart-section">
                    <h2>Cart Totals</h2>
                    <div class="cart-totals">
                        <div>
                            <span>Subtotal</span>
                            <span>$200.00</span>
                        </div>
                        <div>
                            <span>Shipping Fee</span>
                            <span>$10.00</span>
                        </div>
                        <div>
                            <strong>Total</strong>
                            <strong>$210.00</strong>
                        </div>
                    </div>

                    <div class="payment-method">
                        <h2>Payment Method</h2>
                        <div>
                            <input type="radio" name="payment" id="stripe" value="Stripe">
                            <img src="https://via.placeholder.com/50?text=stripe" alt="Stripe">
                            <label for="stripe">Stripe</label>
                        </div>
                        <div>
                            <input type="radio" name="payment" id="razorpay" value="Razorpay">
                            <img src="https://via.placeholder.com/50?text=Razorpay" alt="Razorpay">
                            <label for="razorpay">Razorpay</label>
                        </div>
                        <div>
                            <input type="radio" name="payment" id="cod" value="COD" checked>
                            <label for="cod">Cash on Delivery</label>
                        </div>
                    </div>

                    <div class="place-order">
                        <button type="submit">Place Order</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
