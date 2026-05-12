# Francis Hair Salon

## Overview
I created Francis Hair Salon as my CIS capstone project. It’s a Java web app that functions as an online system for a local hair salon, allowing customers to register and log in, and for the admin to manage appointments.

## Technologies Used
* Java (JSP and Servlets)
* JDBC for database access
* Microsoft SQL Server
* HTML, and JSP pages
* NetBeans IDE
* Apache Tomcat server

## Main Features
* User registration and login for salon customers
* Display of available services and prices
* Booking and managing appointments
* Basic admin features for managing customers and appointments
* Form validation and error handling on the server side

## Architecture
The website uses a layered structure:
* DAO layer for database access
* Model classes for domain objects (customers, appointments, services)
* Servlets to handle HTTP requests and responses
* Filters for authentication/authorization
* Utility classes such as database connection helpers

## How to Run (Course Environment)
1. Open the project in NetBeans.
2. Configure a SQL Server database with the same schema used in the project.
3. Update the database connection settings (JDBC URL, username, password) if needed.
4. Deploy the project to Apache Tomcat from NetBeans.
5. Access the site in a browser at the configured context path.

## What I Learned
Working on this project gave me hands-on experience with Java web development using JSP and Servlets, connecting to databases with JDBC, following an MVC structure, and deploying a Java web app on Tomcat.
