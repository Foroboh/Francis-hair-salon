package com.francishairsalon.servlets;

import com.francishairsalon.dao.AppointmentDAO;
import com.francishairsalon.dao.ServiceDAO;
import com.francishairsalon.dao.StylistDAO;
import com.francishairsalon.model.Appointment;
import com.francishairsalon.model.Service;
import com.francishairsalon.model.Stylist;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO;
    private ServiceDAO serviceDAO;
    private StylistDAO stylistDAO;

    @Override
    public void init() throws ServletException {
        appointmentDAO = new AppointmentDAO();
        serviceDAO = new ServiceDAO();
        stylistDAO = new StylistDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Appointment> appointments = appointmentDAO.getAll();
            int todayCount = appointmentDAO.getTodayCount();
            int totalCustomers = appointmentDAO.getTotalCustomers();
            List<Service> activeServices = serviceDAO.getAllActive();
            List<Stylist> activeStylists = stylistDAO.getAllActive();

            request.setAttribute("appointments", appointments);
            request.setAttribute("todayCount", todayCount);
            request.setAttribute("totalCustomers", totalCustomers);
            request.setAttribute("activeServices", activeServices);
            request.setAttribute("activeStylists", activeStylists);

            request.getRequestDispatcher("/WEB-INF/admin/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Unable to load dashboard data. Please try again.");
            request.getRequestDispatcher("/WEB-INF/admin/dashboard.jsp").forward(request, response);
        }
    }
}
