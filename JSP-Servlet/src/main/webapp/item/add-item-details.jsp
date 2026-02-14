<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="item.model.Item"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Add Item Details</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
  <style type="text/css">
  * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Poppins', sans-serif; }
  body { display: flex; align-items: center; justify-content: center; min-height: 100vh; padding: 40px; background: linear-gradient(135deg, #71b7e6, #9b59b6); }
  .container { max-width: 600px; width: 100%; background: rgba(255,255,255,0.95); padding: 40px 50px; border-radius: 20px; box-shadow: 0 15px 50px rgba(0,0,0,0.2); }
  .text { font-size: 2rem; font-weight: 700; text-align: center; background: linear-gradient(45deg, #71b7e6, #9b59b6); -webkit-background-clip: text; -webkit-text-fill-color: transparent; margin-bottom: 30px; }
  .form-row { margin-bottom: 25px; }
  .form-row label { display: block; color: #666; font-size: 0.95rem; margin-bottom: 6px; }
  .form-row input, .form-row textarea { width: 100%; padding: 12px 14px; border: 2px solid rgba(0,0,0,0.12); border-radius: 8px; font-size: 1rem; }
  .form-row textarea { min-height: 80px; resize: vertical; }
  .button { display: block; width: 100%; max-width: 280px; margin: 30px auto 20px; padding: 16px; font-size: 1.1rem; font-weight: 600; border: none; border-radius: 50px; cursor: pointer; background: linear-gradient(45deg, #71b7e6, #9b59b6); color: white; }
  .button:hover { opacity: 0.95; transform: translateY(-2px); }
  .back { text-align: center; margin-top: 20px; }
  .back a { color: #666; text-decoration: none; font-weight: 500; }
  .back a:hover { color: #3498db; }
  </style>
</head>
<body>
<%
  Item item = (Item) request.getAttribute("item");
  if (item == null) {
    response.sendRedirect(request.getContextPath() + "/ItemController?action=show-items");
    return;
  }
  String ctx = request.getContextPath();
%>
<div class="container">
  <div class="text">Add Item Details</div>
  <p style="margin-bottom:15px;color:#555;">Item: <strong><%= item.getName() %></strong> (ID: <%= item.getId() %>)</p>
  <form action="<%= ctx %>/ItemController" method="post">
    <input type="hidden" name="action" value="save-item-details">
    <input type="hidden" name="id" value="<%= item.getId() %>">
    <div class="form-row">
      <label>Description</label>
      <textarea name="desc" placeholder="Item description"></textarea>
    </div>
    <div class="form-row">
      <label>Issue Date (yyyy-mm-dd)</label>
      <input type="date" name="issue_date">
    </div>
    <div class="form-row">
      <label>Expiry Date (yyyy-mm-dd)</label>
      <input type="date" name="expiry_date">
    </div>
    <input type="submit" value="Save Item Details" class="button">
  </form>
  <p class="back"><a href="<%= ctx %>/ItemController?action=show-items">Back To Items</a></p>
</div>
</body>
</html>
