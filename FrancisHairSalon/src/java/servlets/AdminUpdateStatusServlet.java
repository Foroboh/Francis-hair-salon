package com.francishairsalon.servlets;

import com.francishairsalon.dao.AppointmentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/update-status")
public class AdminUpdateStatusServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String appointmentIdParam = request.getParameter("appointmentId");
        String status = request.getParameter("status");

        try {
            int appointmentId = Integer.parseInt(appointmentIdParam);
            appointmentDAO.updateStatus(appointmentId, status);
        } catch (NumberFormatException e) {
            // If the appointmentId is Invalid; skip the update
        } catch (Exception e) {
            // if there's a database error, still return to admin dashboard
        }

        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}
