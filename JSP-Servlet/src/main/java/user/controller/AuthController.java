package user.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import user.model.User;
import user.service.UserService;
import user.service.impl.UserServiceImpl;

/**
 * Handles login, signup, and logout (Level 3 & 4).
 * Session and cookies applied; logout clears session and removes cookies.
 */
public class AuthController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_USER = "user";
    public static final String SESSION_LOGGED_IN = "loggedIn";

    @Resource(name = "jdbc/connection")
    private DataSource dataSource;

    private UserService getUserService() {
        return new UserServiceImpl(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            doLogout(request, response);
            return;
        }
        if ("signup".equals(action)) {
            request.getRequestDispatcher("/auth/signup.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("signup".equals(action)) {
            doSignup(request, response);
            return;
        }
        doLogin(request, response);
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null) email = "";
        if (password == null) password = "";

        UserService userService = getUserService();
        if (!userService.validateUser(email, password)) {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        User user = userService.findByEmail(email);
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_USER, user);
        session.setAttribute(SESSION_LOGGED_IN, Boolean.TRUE);

        Cookie emailCookie = new Cookie("userEmail", email);
        emailCookie.setMaxAge(60 * 60 * 24 * 7);
        emailCookie.setPath("/");
        response.addCookie(emailCookie);

        response.sendRedirect(request.getContextPath() + "/ItemController?action=show-items");
    }

    private void doSignup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (name == null) name = "";
        if (email == null) email = "";
        if (password == null) password = "";

        name = name.trim();
        email = email.trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("errorMessage", "Name, email and password are required.");
            request.getRequestDispatcher("/auth/signup.jsp").forward(request, response);
            return;
        }

        UserService userService = getUserService();
        if (userService.findByEmail(email) != null) {
            request.setAttribute("errorMessage", "Email already exists. Please use another email or login.");
            request.getRequestDispatcher("/auth/signup.jsp").forward(request, response);
            return;
        }

        User user = new User(name, email, password);
        if (!userService.createUser(user)) {
            request.setAttribute("errorMessage", "Failed to create account. Please try again.");
            request.getRequestDispatcher("/auth/signup.jsp").forward(request, response);
            return;
        }

        request.setAttribute("successMessage", "Account created successfully. Please login.");
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(SESSION_USER);
            session.removeAttribute(SESSION_LOGGED_IN);
            session.invalidate();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                Cookie remove = new Cookie(c.getName(), "");
                remove.setMaxAge(0);
                remove.setPath("/");
                response.addCookie(remove);
            }
        }

        response.sendRedirect(request.getContextPath() + "/AuthController");
    }
}
