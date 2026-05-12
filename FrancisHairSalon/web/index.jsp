<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Francis Hair Salon</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .hero { background-color: #343a40; color: white; padding: 80px 0; }
        .service-card { margin-bottom: 20px; }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Francis Hair Salon</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navMenu">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/register">Register</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Hero -->
<div class="hero text-center">
    <div class="container">
        <h1>Welcome to Francis Hair Salon</h1>
        <p class="lead">Professional hair care services for everyone</p>
        <a href="${pageContext.request.contextPath}/register" class="btn btn-warning btn-lg me-2">Book an Appointment</a>
        <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-light btn-lg">Login</a>
    </div>
</div>

<!-- Services Section -->
<div class="container my-5">
    <h2 class="text-center mb-4">Our Services</h2>
    <div class="row">
        <div class="col-md-4 service-card">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">Haircut &amp; Styling</h5>
                    <p class="card-text">Professional cuts and styles for men and women.</p>
                </div>
            </div>
        </div>
        <div class="col-md-4 service-card">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">Color &amp; Highlights</h5>
                    <p class="card-text">Full color, highlights, Hair painting, and more.</p>
                </div>
            </div>
        </div>
        <div class="col-md-4 service-card">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">Treatments</h5>
                    <p class="card-text">Deep conditioning, keratin treatments and scalp care.</p>
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
