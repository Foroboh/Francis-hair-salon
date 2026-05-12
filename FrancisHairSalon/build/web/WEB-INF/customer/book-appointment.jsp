<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.francishairsalon.model.Service" %>
<%@ page import="com.francishairsalon.model.User" %>
<%@ page import="com.francishairsalon.model.Stylist" %>
<%
  User user = (User) session.getAttribute("user");
  List<Service>  services = (List<Service>)  request.getAttribute("services");
  List<Stylist>  stylists = (List<Stylist>)  request.getAttribute("stylists");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Book Appointment - Francis Hair Salon</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/customer/dashboard">Francis Hair Salon</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/customer/book">Book Appointment</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/customer/dashboard">My Appointments</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Content -->
<div class="container my-4">
    <div class="row justify-content-center">
        <div class="col-md-7">
            <div class="card">
                <div class="card-header bg-dark text-white">
                    <h5 class="mb-0">Book an Appointment</h5>
                </div>
                <div class="card-body">

                    <% if (request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
                    <% } %>

                    <form action="${pageContext.request.contextPath}/customer/book" method="post">

                        <div class="mb-3">
                            <label for="serviceId" class="form-label">Service</label>
                            <select class="form-select" id="serviceId" name="serviceId" required>
                                <option value="">-- Select a service --</option>
                                <%
                                  if (services != null) {
                                    for (Service svc : services) {
                                %>
                                <option value="<%= svc.getServiceId() %>">
                                    <%= svc.getServiceName() %><% if (svc.getPrice() > 0) { %> - $<%= String.format("%.2f", svc.getPrice()) %><% } %>
                                </option>
                                <%
                                    }
                                  }
                                %>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="stylistId" class="form-label">Stylist</label>
                            <select class="form-select" id="stylistId" name="stylistId" required>
                                <option value="">-- Select a stylist --</option>
                                <%
                                  if (stylists != null) {
                                    for (Stylist sty : stylists) {
                                %>
                                <option value="<%= sty.getStylistId() %>">
                                    <%= sty.getFullName() %><% if (sty.getSpecialty() != null && !sty.getSpecialty().isEmpty()) { %> · <%= sty.getSpecialty() %><% } %>
                                </option>
                                <%
                                    }
                                  }
                                %>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="appointmentDate" class="form-label">Date</label>
                            <input type="date" class="form-control" id="appointmentDate" name="appointmentDate"
                                   required min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                            <div class="form-text">Earliest available: today</div>
                        </div>

                        <div class="mb-3">
                            <label for="appointmentTime" class="form-label">Time</label>
                            <input type="time" class="form-control" id="appointmentTime" name="appointmentTime"
                                   required min="09:00" max="18:00">
                            <div class="form-text">Available hours: 9:00 AM – 6:00 PM</div>
                        </div>

                        <div class="mb-3">
                            <label for="notes" class="form-label">Notes (optional)</label>
                            <textarea class="form-control" id="notes" name="notes" rows="3"
                                      placeholder="Any special requests or notes for your stylist..."></textarea>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-dark">Confirm Booking</button>
                            <a href="${pageContext.request.contextPath}/customer/dashboard" class="btn btn-outline-secondary">Cancel</a>
                        </div>
                    </form>

                </div>
            </div>
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
