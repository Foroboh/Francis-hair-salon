package com.francishairsalon.dao;

import com.francishairsalon.model.User;
import com.francishairsalon.util.DBUtil;

import java.sql.*;

//Data Access Object for the Users table. 
public class UserDAO {

    // Find a User by e-mail
    public User findByEmail(String email) {
        final String sql = "SELECT user_id, first_name, last_name, email, phone, "
                + "password_hash, role, created_at "
                + "FROM Users WHERE email = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[UserDAO] Error in findByEmail()");
            e.printStackTrace();
        }

        return null;
    }

    // Find a User by primary key
    public User findById(int userId) {
        final String sql = "SELECT user_id, first_name, last_name, email, phone, "
                + "password_hash, role, created_at "
                + "FROM Users WHERE user_id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[UserDAO] Error in findById()");
            e.printStackTrace();
        }

        return null;
    }

    //Inserts a new user record into the Users table.
    public boolean createUser(User user) {
        final String sql = "INSERT INTO Users (first_name, last_name, email, phone, "
                + "password_hash, role) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getPasswordHash());
            ps.setString(6, user.getRole() != null ? user.getRole() : "customer");

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[UserDAO] Error in createUser()");
            e.printStackTrace();
        }

        return false;
    }

    // Authenticate a user
    public User authenticate(String email, String password) {
        User user = findByEmail(email);

        if (user == null) {
            return null;
        }

        if (user.getPasswordHash() != null && user.getPasswordHash().equals(password)) {
            return user;
        }

        return null;
    }

    // Internal helper
    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setRole(rs.getString("role"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}
