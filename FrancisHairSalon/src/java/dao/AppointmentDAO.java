package com.francishairsalon.dao;

import com.francishairsalon.model.Appointment;
import com.francishairsalon.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//JOIN queries pull display fields from Users, Stylists, and Services.
public class AppointmentDAO {

    // SELECT clause used by getByUserId() and getAll()
    private static final String SELECT_WITH_JOINS
            = "SELECT a.appointment_id, a.user_id, a.stylist_id, a.service_id, "
            + "       a.appointment_date, a.appointment_time, a.status, a.notes, a.created_at, "
            + "       (u.first_name + ' ' + u.last_name) AS customer_name, "
            + "       (st.first_name + ' ' + st.last_name) AS stylist_name, "
            + "       sv.service_name, sv.price AS service_price "
            + "FROM Appointments a "
            + "INNER JOIN Users    u  ON a.user_id    = u.user_id "
            + "INNER JOIN Stylists st ON a.stylist_id = st.stylist_id "
            + "INNER JOIN Services sv ON a.service_id = sv.service_id ";

    // Create new appointment
    
    public boolean createAppointment(Appointment apt) {
        final String sql
                = "INSERT INTO Appointments "
                + "(user_id, stylist_id, service_id, appointment_date, "
                + " appointment_time, status, notes) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, apt.getUserId());
            ps.setInt(2, apt.getStylistId());
            ps.setInt(3, apt.getServiceId());
            ps.setDate(4, apt.getAppointmentDate());
            ps.setTime(5, apt.getAppointmentTime());
            ps.setString(6, apt.getStatus() != null ? apt.getStatus() : "confirmed");
            ps.setString(7, apt.getNotes());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[AppointmentDAO] Error in createAppointment()");
            e.printStackTrace();
        }

        return false;
    }

    // Get by user (customer view)
    public List<Appointment> getByUserId(int userId) {
        final String sql = SELECT_WITH_JOINS
                + "WHERE a.user_id = ? "
                + "ORDER BY a.appointment_date DESC, a.appointment_time DESC";

        List<Appointment> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[AppointmentDAO] Error in getByUserId()");
            e.printStackTrace();
        }

        return list;
    }

    // Get all (admin view)
    public List<Appointment> getAll() {
        final String sql = SELECT_WITH_JOINS
                + "ORDER BY a.appointment_date DESC, a.appointment_time DESC";

        List<Appointment> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("[AppointmentDAO] Error in getAll()");
            e.printStackTrace();
        }

        return list;
    }

    // Cancel appointment (customer)
    public boolean cancelAppointment(int appointmentId, int userId) {
        final String sql
                = "UPDATE Appointments SET status = 'cancelled' "
                + "WHERE appointment_id = ? AND user_id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, appointmentId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[AppointmentDAO] Error in cancelAppointment()");
            e.printStackTrace();
        }

        return false;
    }

    // Update status (admin)
    public boolean updateStatus(int appointmentId, String status) {
        final String sql
                = "UPDATE Appointments SET status = ? "
                + "WHERE appointment_id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, appointmentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[AppointmentDAO] Error in updateStatus()");
            e.printStackTrace();
        }

        return false;
    }

    // Dashboard Data
    //Returns the number of appointments scheduled for today.
    public int getTodayCount() {
        final String sql
                = "SELECT COUNT(*) AS today_count "
                + "FROM Appointments "
                + "WHERE appointment_date = CAST(GETDATE() AS DATE)";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("today_count");
            }

        } catch (SQLException e) {
            System.err.println("[AppointmentDAO] Error in getTodayCount()");
            e.printStackTrace();
        }

        return 0;
    }

    //Returns the total number of different customers who have made at least one
    //appointment.
    public int getTotalCustomers() {
        final String sql
                = "SELECT COUNT(DISTINCT user_id) AS total_customers "
                + "FROM Appointments";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total_customers");
            }

        } catch (SQLException e) {
            System.err.println("[AppointmentDAO] Error in getTotalCustomers()");
            e.printStackTrace();
        }

        return 0;
    }

    // Internal helper
    private Appointment mapRow(ResultSet rs) throws SQLException {
        Appointment a = new Appointment();
        a.setAppointmentId(rs.getInt("appointment_id"));
        a.setUserId(rs.getInt("user_id"));
        a.setStylistId(rs.getInt("stylist_id"));
        a.setServiceId(rs.getInt("service_id"));
        a.setAppointmentDate(rs.getDate("appointment_date"));
        a.setAppointmentTime(rs.getTime("appointment_time"));
        a.setStatus(rs.getString("status"));
        a.setNotes(rs.getString("notes"));
        a.setCreatedAt(rs.getTimestamp("created_at"));

        // Display-only fields from JOINs
        a.setCustomerName(rs.getString("customer_name"));
        a.setStylistName(rs.getString("stylist_name"));
        a.setServiceName(rs.getString("service_name"));
        a.setServicePrice(rs.getDouble("service_price"));

        return a;
    }
}
