package com.francishairsalon.filter;

import com.francishairsalon.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//Security filter for managing user authentication and verifying role-based permissions
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    // Main filter logic
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String requestUri = req.getRequestURI();
        // Removing the application prefix to focus on the specific page request
        String path = requestUri.substring(contextPath.length());

        //Letting public resources through without requiring a login
        if (isPublicResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Check whether the user is logged in
        HttpSession session = req.getSession(false);
        User loggedInUser = (session != null)
                ? (User) session.getAttribute("user")
                : null;

        boolean isLoggedIn = (loggedInUser != null);

        //Restricted areas for only logged-in customers or admins
        boolean isCustomerPath = path.startsWith("/customer/") || path.equals("/customer");
        boolean isAdminPath = path.startsWith("/admin/") || path.equals("/admin");

        if (isCustomerPath || isAdminPath) {

            if (!isLoggedIn) {
                // If it's not authenticated, send to login page
                resp.sendRedirect(contextPath + "/login.jsp");
                return;
            }

            if (isAdminPath && !"admin".equalsIgnoreCase(loggedInUser.getRole())) {
                // If it's authenticated, but not an admin, then forward to customer area
                resp.sendRedirect(contextPath + "/customer/dashboard");
                return;
            }
        }

        // Allow the request to continue
        chain.doFilter(request, response);
    }

    // Destroy (cleanup)
    @Override
    public void destroy() {
        // No cleanup needed
    }

    // Public resource check
    private boolean isPublicResource(String path) {
        // The actual public pages
        if (path.equals("/")
                || path.equals("/index.jsp")
                || path.equals("/login.jsp")
                || path.equals("/register.jsp")) {
            return true;
        }

        return false;
    }
}
