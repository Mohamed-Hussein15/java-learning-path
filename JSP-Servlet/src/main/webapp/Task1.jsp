<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%! 
    // Function declaration
    public String concatIdAndName(int id, String name) {
        return id + " - " + name;
    }
%>

<%
    int id = 101;
    String name = "Test";

    String result = concatIdAndName(id, name);
%>

<!DOCTYPE html>
<html>
<body>
    <h2>Result:</h2>
    <p><%= result %></p>
</body>
</html>
