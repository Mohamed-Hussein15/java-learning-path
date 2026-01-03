<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>Food Order</title>
</head>
<body>

<h2>Order Food</h2>

<form action="order.jsp" method="post">
    Food Name:
    <input type="text" name="food" required>
    <input type="submit" value="Add Order">
</form>

<%
    String food = request.getParameter("food");

    if (food != null && !food.trim().isEmpty()) {

        // Get session
        ArrayList<String> orders =
                (ArrayList<String>) session.getAttribute("orders");

        // If first time, create list
        if (orders == null) {
            orders = new ArrayList<>();
        }

        // Add new food order
        orders.add(food);

        // Save back to session
        session.setAttribute("orders", orders);
    }
%>

<br>
<a href="allorders.jsp">View All Orders</a>

</body>
</html>
