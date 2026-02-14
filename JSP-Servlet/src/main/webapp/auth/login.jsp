<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; min-height: 100vh; display: flex; align-items: center; justify-content: center; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 20px; }
    .box { background: rgba(255,255,255,0.95); padding: 40px; border-radius: 16px; box-shadow: 0 20px 60px rgba(0,0,0,0.2); width: 100%; max-width: 400px; }
    h1 { text-align: center; color: #333; margin-bottom: 24px; font-size: 1.8rem; }
    .form-group { margin-bottom: 18px; }
    .form-group label { display: block; color: #555; font-size: 0.9rem; margin-bottom: 6px; }
    .form-group input { width: 100%; padding: 12px 14px; border: 2px solid #e0e0e0; border-radius: 8px; font-size: 1rem; }
    .form-group input:focus { outline: none; border-color: #667eea; }
    .btn { width: 100%; padding: 14px; margin-top: 10px; font-size: 1rem; font-weight: 600; border: none; border-radius: 8px; cursor: pointer; background: linear-gradient(135deg, #667eea, #764ba2); color: white; }
    .btn:hover { opacity: 0.95; transform: translateY(-1px); }
    .link { text-align: center; margin-top: 20px; }
    .link a { color: #667eea; text-decoration: none; font-weight: 500; }
    .link a:hover { text-decoration: underline; }
    .error { background: #ffebee; color: #c62828; padding: 10px; border-radius: 8px; margin-bottom: 16px; font-size: 0.9rem; }
  </style>
  <script>
    window.onload = function() {
      <% String err = (String) request.getAttribute("errorMessage"); if (err != null) { %>
      alert("<%= err.replace("\"", "\\\"") %>");
      <% } %>
    };
  </script>
</head>
<body>
  <div class="box">
    <h1>Login</h1>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="error"><%= request.getAttribute("errorMessage") %></div>
    <% } %>
    <% if (request.getAttribute("successMessage") != null) { %>
    <div style="background:#e8f5e9;color:#2e7d32;padding:10px;border-radius:8px;margin-bottom:16px;"><%= request.getAttribute("successMessage") %></div>
    <% } %>
    <form action="<%= request.getContextPath() %>/AuthController" method="post">
      <div class="form-group">
        <label>Email</label>
        <input type="email" name="email" required placeholder="your@email.com">
      </div>
      <div class="form-group">
        <label>Password</label>
        <input type="password" name="password" required placeholder="Password">
      </div>
      <button type="submit" class="btn">Login</button>
    </form>
    <p class="link">Don't have an account? <a href="<%= request.getContextPath() %>/AuthController?action=signup">Sign up</a></p>
  </div>
</body>
</html>
