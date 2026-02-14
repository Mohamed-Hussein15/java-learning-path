<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
  if (Boolean.TRUE.equals(loggedIn)) {
    response.sendRedirect(request.getContextPath() + "/ItemController?action=show-items");
  } else {
    response.sendRedirect(request.getContextPath() + "/AuthController");
  }
%>
