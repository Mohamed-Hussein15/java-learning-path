<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Result</title>
</head>
<body>

<h2>Submitted Data</h2>

<%
    String fullName = request.getParameter("fullName");
    String password = request.getParameter("password");
    String age = request.getParameter("age");
    String addressRadio = request.getParameter("addressRadio");
    String addressSelect = request.getParameter("addressSelect");
%>

<p><b>Full Name:</b> <%= fullName %></p>
<p><b>Password:</b> <%= password %></p>
<p><b>Age:</b> <%= age %></p>
<p><b>Address (Radio):</b> <%= addressRadio %></p>
<p><b>Address (Select):</b> <%= addressSelect %></p>

</body>
</html>
