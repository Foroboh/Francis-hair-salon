package com.francishairsalon.servlets;

import com.francishairsalon.dao.AppointmentDAO;
import com.francishairsalon.model.Appointment;
import com.francishairsalon.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/dashboard")
public class CustomerDashboardServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            List<Appointment> appointments = appointmentDAO.getByUserId(user.getUserId());
            request.setAttribute("appointments", appointments);
            request.getRequestDispatcher("/WEB-INF/customer/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Unable to load your appointments. Please try again.");
            request.getRequestDispatcher("/WEB-INF/customer/dashboard.jsp").forward(request, response);
        }
    }
}
