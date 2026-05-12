package com.francishairsalon.dao;

import com.francishairsalon.model.Service;
import com.francishairsalon.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Data Access Object for the Services table.
public class ServiceDAO {

    // Get all active services
    public List<Service> getAllActive() {
        final String sql = "SELECT service_id, service_name, description, "
                + "duration_min, price, is_active "
                + "FROM Services WHERE is_active = 1 "
                + "ORDER BY service_name";

        List<Service> services = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                services.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("[ServiceDAO] Error in getAllActive()");
            e.printStackTrace();
        }

        return services;
    }

    // Find a service by primary key
    public Service findById(int id) {
        final String sql = "SELECT service_id, service_name, description, "
                + "duration_min, price, is_active "
                + "FROM Services WHERE service_id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ServiceDAO] Error in findById()");
            e.printStackTrace();
        }

        return null;
    }

    // Internal helper
    private Service mapRow(ResultSet rs) throws SQLException {
        Service svc = new Service();
        svc.setServiceId(rs.getInt("service_id"));
        svc.setServiceName(rs.getString("service_name"));
        svc.setDescription(rs.getString("description"));
        svc.setDurationMin(rs.getInt("duration_min"));
        svc.setPrice(rs.getDouble("price"));
        svc.setActive(rs.getBoolean("is_active"));
        return svc;
    }
}
