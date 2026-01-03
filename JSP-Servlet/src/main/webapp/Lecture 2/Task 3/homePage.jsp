<%@ page import="javax.servlet.http.Cookie" %>
<html>
<head>
    <title>Home Page</title>
</head>
<body>

<h2>Home Page</h2>

<%
    // If coming from form submission
    String favPlace = request.getParameter("favPlace");

    if (favPlace != null && !favPlace.trim().isEmpty()) {

        Cookie placeCookie = new Cookie("favPlace", favPlace);

        // 1 month = 30 days
        placeCookie.setMaxAge(60 * 60 * 24 * 30);

        response.addCookie(placeCookie);
    }

    // Read cookie
    String savedPlace = null;
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
        for (Cookie c : cookies) {
            if (c.getName().equals("favPlace")) {
                savedPlace = c.getValue();
                break;
            }
        }
    }
%>

<% if (savedPlace != null) { %>
    <h3>Your Favorite Place is:</h3>
    <p><b><%= savedPlace %></b></p>
<% } else { %>
    <p>No favorite place saved yet.</p>
<% } %>

<br>
<a href="start.html">Change Favorite Place</a>

</body>
</html>
