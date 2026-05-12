package com.francishairsalon.servlets;

import com.francishairsalon.dao.AppointmentDAO;
import com.francishairsalon.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/customer/cancel")
public class CancelAppointmentServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String appointmentIdParam = request.getParameter("appointmentId");

        try {
            int appointmentId = Integer.parseInt(appointmentIdParam);
            appointmentDAO.cancelAppointment(appointmentId, user.getUserId());
        } catch (NumberFormatException e) {
            // If the appointmentId is Invalid, ignore the request and return to dashboard
        } catch (Exception e) {
            // Log cancellation error; still return to dashboard
        }

        response.sendRedirect(request.getContextPath() + "/customer/dashboard?cancelled=true");
    }
}
