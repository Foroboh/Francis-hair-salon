package com.francishairsalon.servlets;

import com.francishairsalon.dao.AppointmentDAO;
import com.francishairsalon.dao.ServiceDAO;
import com.francishairsalon.dao.StylistDAO;
import com.francishairsalon.model.Appointment;
import com.francishairsalon.model.Service;
import com.francishairsalon.model.Stylist;
import com.francishairsalon.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@WebServlet("/customer/book")
public class BookAppointmentServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO;
    private StylistDAO     stylistDAO;
    private ServiceDAO     serviceDAO;

    @Override
    public void init() throws ServletException {
        appointmentDAO = new AppointmentDAO();
        stylistDAO     = new StylistDAO();
        serviceDAO     = new ServiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Stylist> stylists = stylistDAO.getAllActive();
            List<Service> services = serviceDAO.getAllActive();
            request.setAttribute("stylists", stylists);
            request.setAttribute("services", services);
            request.getRequestDispatcher("/WEB-INF/customer/book-appointment.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Unable to load booking information. Please try again.");
            request.getRequestDispatcher("/WEB-INF/customer/book-appointment.jsp").forward(request, response);
        }
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

        String stylistIdParam      = request.getParameter("stylistId");
        String serviceIdParam      = request.getParameter("serviceId");
        String appointmentDate     = request.getParameter("appointmentDate");
        String appointmentTime     = request.getParameter("appointmentTime");
        String notes               = request.getParameter("notes");

        try {
            int stylistId = Integer.parseInt(stylistIdParam);
            int serviceId = Integer.parseInt(serviceIdParam);

            // Reject past dates
            java.sql.Date selectedDate = Date.valueOf(appointmentDate);
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            if (selectedDate.before(today)) {
                request.setAttribute("error", "Please select today's date or a future date.");
                reloadFormData(request);
                request.getRequestDispatcher("/WEB-INF/customer/book-appointment.jsp").forward(request, response);
                return;
            }

            Appointment appointment = new Appointment();
            appointment.setUserId(user.getUserId());
            appointment.setStylistId(stylistId);
            appointment.setServiceId(serviceId);
            appointment.setAppointmentDate(Date.valueOf(appointmentDate));
            appointment.setAppointmentTime(Time.valueOf(appointmentTime + ":00"));
            appointment.setNotes(notes);

            boolean created = appointmentDAO.createAppointment(appointment);

            if (created) {
                response.sendRedirect(request.getContextPath() + "/customer/dashboard?booked=true");
            } else {
                request.setAttribute("error", "Failed to book appointment. Please try again.");
                reloadFormData(request);
                request.getRequestDispatcher("/WEB-INF/customer/book-appointment.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid stylist or service selection.");
            reloadFormData(request);
            request.getRequestDispatcher("/WEB-INF/customer/book-appointment.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while booking. Please try again.");
            reloadFormData(request);
            request.getRequestDispatcher("/WEB-INF/customer/book-appointment.jsp").forward(request, response);
        }
    }

    private void reloadFormData(HttpServletRequest request) {
        try {
            List<Stylist> stylists = stylistDAO.getAllActive();
            List<Service> services = serviceDAO.getAllActive();
            request.setAttribute("stylists", stylists);
            request.setAttribute("services", services);
        } catch (Exception e) {
            // If it fails to reload, the JSP will display an empty form instead of crashing
        }
    }
}
