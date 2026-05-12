<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.francishairsalon.model.Appointment" %>
<%@ page import="com.francishairsalon.model.Service" %>
<%@ page import="com.francishairsalon.model.Stylist" %>
<%
  List<Appointment> appointments  = (List<Appointment>) request.getAttribute("appointments");
  Integer todayCount       = (Integer) request.getAttribute("todayCount");
  Integer totalCustomers   = (Integer) request.getAttribute("totalCustomers");
  List<Service>  services  = (List<Service>)  request.getAttribute("activeServices");
  List<Stylist>  stylists  = (List<Stylist>)  request.getAttribute("activeStylists");

  int today      = (todayCount      != null) ? todayCount      : 0;
  int customers  = (totalCustomers  != null) ? totalCustomers  : 0;
  int totalSvcs  = (services        != null) ? services.size() : 0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Dashboard - Francis Hair Salon</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/dashboard">Francis Hair Salon</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                <li class="nav-item"><span class="nav-link text-warning">Admin</span></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Content -->
<div class="container-fluid my-4 px-4">

    <h3>Admin Dashboard</h3>
    <p class="text-muted">Manage all appointments and monitor salon activity.</p>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if (request.getAttribute("success") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("success") %></div>
    <% } %>

    <!-- Stats -->
    <div class="row mb-4">
        <div class="col-md-4">
            <div class="card text-center border-danger">
                <div class="card-body">
                    <h2><%= today %></h2>
                    <p class="text-muted mb-0">Today's Appointments</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-center border-success">
                <div class="card-body">
                    <h2><%= customers %></h2>
                    <p class="text-muted mb-0">Total Customers</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-center border-primary">
                <div class="card-body">
                    <h2><%= totalSvcs %></h2>
                    <p class="text-muted mb-0">Total Services</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Appointments Table -->
    <div class="card">
        <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
            <h5 class="mb-0">All Appointments</h5>
            <small><%= (appointments != null ? appointments.size() : 0) %> total record(s)</small>
        </div>
        <div class="card-body p-0">
            <% if (appointments == null || appointments.isEmpty()) { %>
                <div class="text-center py-5">
                    <p class="text-muted">No appointments found.</p>
                </div>
            <% } else { %>
                <table class="table table-striped table-hover mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>#ID</th>
                            <th>Customer</th>
                            <th>Service</th>
                            <th>Stylist</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Status</th>
                            <th>Update Status</th>
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
                            <td class="text-muted">#<%= appt.getAppointmentId() %></td>
                            <td><strong><%= appt.getCustomerName() != null ? appt.getCustomerName() : "—" %></strong></td>
                            <td><%= appt.getServiceName()  != null ? appt.getServiceName()  : "—" %></td>
                            <td><%= appt.getStylistName()  != null ? appt.getStylistName()  : "—" %></td>
                            <td><%= appt.getAppointmentDate() %></td>
                            <td><%= appt.getAppointmentTime() %></td>
                            <td><span class="badge bg-<%= badgeColor %>"><%= appt.getStatus() %></span></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/update-status"
                                      method="post" class="d-flex gap-2 align-items-center">
                                    <input type="hidden" name="appointmentId" value="<%= appt.getAppointmentId() %>">
                                    <select name="status" class="form-select form-select-sm" style="width:140px;">
                                        <option value="confirmed"  <%= "confirmed".equals(status)  ? "selected" : "" %>>Confirmed</option>
                                        <option value="completed"  <%= "completed".equals(status)  ? "selected" : "" %>>Completed</option>
                                        <option value="cancelled"  <%= "cancelled".equals(status)  ? "selected" : "" %>>Cancelled</option>
                                    </select>
                                    <button type="submit" class="btn btn-sm btn-dark">Save</button>
                                </form>
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
    <p class="mb-0">&copy; 2026 Francis Hair Salon — Admin Panel</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
