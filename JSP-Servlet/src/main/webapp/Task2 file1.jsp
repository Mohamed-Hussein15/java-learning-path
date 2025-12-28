<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>

<h2>Registration Form</h2>

<form action="Task2 file2.jsp" method="post">

    Full Name:
    <input type="text" name="fullName" /><br><br>

    Password:
    <input type="password" name="password" /><br><br>

    Age:
    <input type="text" name="age" /><br><br>

    Address (Radio):<br>
    <input type="radio" name="addressRadio" value="Cairo"> Cairo<br>
    <input type="radio" name="addressRadio" value="Alex"> Alex<br>
    <input type="radio" name="addressRadio" value="Menofia"> Menofia<br><br>

    Address (Select):
    <select name="addressSelect">
        <option value="Cairo">Cairo</option>
        <option value="Alex">Alex</option>
        <option value="Menofia">Menofia</option>
    </select><br><br>

    <input type="submit" value="Submit" />

</form>

</body>
</html>
