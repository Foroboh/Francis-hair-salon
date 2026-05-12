package com.francishairsalon.dao;

import com.francishairsalon.model.Stylist;
import com.francishairsalon.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Data Access Object for the Stylists table.
public class StylistDAO {

    // Get all active stylists
    public List<Stylist> getAllActive() {
        final String sql = "SELECT stylist_id, first_name, last_name, specialty, "
                + "phone, is_active "
                + "FROM Stylists WHERE is_active = 1 "
                + "ORDER BY last_name, first_name";

        List<Stylist> stylists = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                stylists.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("[StylistDAO] Error in getAllActive()");
            e.printStackTrace();
        }

        return stylists;
    }

    // Find a stylist by primary key
    public Stylist findById(int id) {
        final String sql = "SELECT stylist_id, first_name, last_name, specialty, "
                + "phone, is_active "
                + "FROM Stylists WHERE stylist_id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[StylistDAO] Error in findById()");
            e.printStackTrace();
        }

        return null;
    }

    // Internal helper
    private Stylist mapRow(ResultSet rs) throws SQLException {
        Stylist s = new Stylist();
        s.setStylistId(rs.getInt("stylist_id"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        s.setSpecialty(rs.getString("specialty"));
        s.setPhone(rs.getString("phone"));
        s.setActive(rs.getBoolean("is_active"));
        return s;
    }
}
