<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.francishairsalon.model.Appointment" %>
<%@ page import="com.francishairsalon.model.User" %>
<%
  User user = (User) session.getAttribute("user");
  List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Dashboard - Francis Hair Salon</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/customer/dashboard">Francis Hair Salon</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/customer/book">Book Appointment</a></li>
                <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/customer/dashboard">My Appointments</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Content -->
<div class="container my-4">

    <% if ("true".equals(request.getParameter("booked"))) { %>
        <div class="alert alert-success">Your appointment has been booked successfully!</div>
    <% } %>
    <% if ("true".equals(request.getParameter("cancelled"))) { %>
        <div class="alert alert-info">Your appointment has been cancelled.</div>
    <% } %>

    <h3>Welcome, <%= user != null ? user.getFirstName() : "Customer" %>!</h3>
    <p class="text-muted">Manage your appointments below.</p>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h5>My Appointments</h5>
        <a href="${pageContext.request.contextPath}/customer/book" class="btn btn-dark">+ Book New Appointment</a>
    </div>

    <div class="card">
        <div class="card-body p-0">
            <% if (appointments == null || appointments.isEmpty()) { %>
                <div class="text-center py-5">
                    <p class="text-muted">You have no appointments yet.</p>
                    <a href="${pageContext.request.contextPath}/customer/book" class="btn btn-dark">Book Your First Appointment</a>
                </div>
            <% } else { %>
                <table class="table table-striped mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Service</th>
                            <th>Stylist</th>
                            <th>Price</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                          for (Appointment appt : appointments) {
                            String status = appt.getStatus() != null ? appt.getStatus().toLowerCase() : "confirmed";
                            String badgeColor = "secondary";
                            if ("confirmed".equals(status))  badgeColor = "success";
                            if ("completed".equals(status))  badgeColor = "primary";
                            if ("cancelled".equals(status))  badgeColor = "danger";
                        %>
                        <tr>
                            <td><%= appt.getAppointmentDate() %></td>
                            <td><%= appt.getAppointmentTime() %></td>
                            <td><%= appt.getServiceName() != null ? appt.getServiceName() : "—" %></td>
                            <td><%= appt.getStylistName() != null ? appt.getStylistName() : "—" %></td>
                            <td>
                                <%
                                  double price = appt.getServicePrice();
                                  if (price > 0) { %>
                                    $<%= String.format("%.2f", price) %>
                                <% } else { %>
                                    —
                                <% } %>
                            </td>
                            <td><span class="badge bg-<%= badgeColor %>"><%= appt.getStatus() %></span></td>
                            <td>
                                <% if ("confirmed".equals(status)) { %>
                                    <form action="${pageContext.request.contextPath}/customer/cancel" method="post" style="display:inline;">
                                        <input type="hidden" name="appointmentId" value="<%= appt.getAppointmentId() %>">
                                        <button type="submit" class="btn btn-sm btn-outline-danger"
                                            onclick="return confirm('Cancel this appointment?')">Cancel</button>
                                    </form>
                                <% } else { %>
                                    —
                                <% } %>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } %>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="bg-dark text-white text-center py-3 mt-5">
    <p class="mb-0">&copy; 2026 Francis Hair Salon. All rights reserved.</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
