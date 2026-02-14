package user.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import javax.sql.DataSource;

import user.model.User;
import user.service.UserService;

public class UserServiceImpl implements UserService {

    private final DataSource dataSource;

    public UserServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findByEmail(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement("SELECT ID, NAME, EMAIL, PASSWORD FROM HR.USERS WHERE UPPER(EMAIL) = UPPER(?)");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getLong("ID"), rs.getString("NAME"), rs.getString("EMAIL"), rs.getString("PASSWORD"));
            }
        } catch (Exception e) {
            System.out.println("UserServiceImpl findByEmail: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeQuietly(ps, conn);
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement("SELECT ID, NAME, EMAIL, PASSWORD FROM HR.USERS WHERE ID = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getLong("ID"), rs.getString("NAME"), rs.getString("EMAIL"), rs.getString("PASSWORD"));
            }
        } catch (Exception e) {
            System.out.println("UserServiceImpl findById: " + e.getMessage());
        } finally {
            closeQuietly(ps, conn);
        }
        return null;
    }

    @Override
    public boolean createUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement("INSERT INTO HR.USERS (NAME, EMAIL, PASSWORD) VALUES (?, ?, ?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("UserServiceImpl createUser: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeQuietly(ps, conn);
        }
        return false;
    }

    @Override
    public boolean validateUser(String email, String password) {
        User user = findByEmail(email);
        return user != null && password != null && password.equals(user.getPassword());
    }

    private void closeQuietly(PreparedStatement ps, Connection conn) {
        try {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("closeQuietly: " + e.getMessage());
        }
    }
}
