<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>All Orders</title>
</head>
<body>

<h2>All Orders in This Session</h2>

<%
    ArrayList<String> orders =
            (ArrayList<String>) session.getAttribute("orders");

    if (orders == null || orders.isEmpty()) {
%>
        <p>No orders yet.</p>
<%
    } else {
%>
        <ul>
        <% for (String order : orders) { %>
            <li><%= order %></li>
        <% } %>
        </ul>
<%
    }
%>

<br>
<a href="order.jsp">Add More Orders</a>

</body>
</html>
