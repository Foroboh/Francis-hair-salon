/**
 * Francis Hair Salon — main.js
 * Handles: alert auto-dismiss, mobile nav, date min, cancel confirm
 */

document.addEventListener('DOMContentLoaded', function () {

    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alert) {
        // Fade out then remove
        setTimeout(function () {
            alert.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
            alert.style.opacity = '0';
            alert.style.transform = 'translateY(-4px)';
            setTimeout(function () {
                if (alert.parentNode) {
                    alert.parentNode.removeChild(alert);
                }
            }, 500);
        }, 5000);

        // Manual close button
        const closeBtn = alert.querySelector('.alert-close');
        if (closeBtn) {
            closeBtn.addEventListener('click', function () {
                alert.style.transition = 'opacity 0.3s ease';
                alert.style.opacity = '0';
                setTimeout(function () {
                    if (alert.parentNode) {
                        alert.parentNode.removeChild(alert);
                    }
                }, 300);
            });
        }
    });

    // Mobile hamburger menu toggle
    const navToggle = document.getElementById('navToggle');
    const navMenu = document.getElementById('navMenu');

    if (navToggle && navMenu) {
        navToggle.addEventListener('click', function () {
            const isOpen = navMenu.classList.toggle('is-open');
            navToggle.setAttribute('aria-expanded', isOpen ? 'true' : 'false');

            // Animate hamburger lines
            const spans = navToggle.querySelectorAll('span');
            if (isOpen) {
                spans[0].style.transform = 'translateY(7px) rotate(45deg)';
                spans[1].style.opacity = '0';
                spans[2].style.transform = 'translateY(-7px) rotate(-45deg)';
            } else {
                spans[0].style.transform = '';
                spans[1].style.opacity = '';
                spans[2].style.transform = '';
            }
        });

        // Close menu when a link is clicked (mobile UX)
        navMenu.querySelectorAll('a').forEach(function (link) {
            link.addEventListener('click', function () {
                navMenu.classList.remove('is-open');
                navToggle.setAttribute('aria-expanded', 'false');
                const spans = navToggle.querySelectorAll('span');
                spans.forEach(function (s) {
                    s.style.transform = '';
                    s.style.opacity = '';
                });
            });
        });

        // Close menu when clicking outside
        document.addEventListener('click', function (e) {
            if (!navToggle.contains(e.target) && !navMenu.contains(e.target)) {
                navMenu.classList.remove('is-open');
                navToggle.setAttribute('aria-expanded', 'false');
                const spans = navToggle.querySelectorAll('span');
                spans.forEach(function (s) {
                    s.style.transform = '';
                    s.style.opacity = '';
                });
            }
        });
    }

    // Date input: set min date to today
    const dateInputs = document.querySelectorAll('input[type="date"]');
    if (dateInputs.length > 0) {
        const today = new Date();
        const yyyy = today.getFullYear();
        const mm = String(today.getMonth() + 1).padStart(2, '0');
        const dd = String(today.getDate()).padStart(2, '0');
        const todayStr = yyyy + '-' + mm + '-' + dd;

        dateInputs.forEach(function (input) {
            if (!input.getAttribute('min')) {
                input.setAttribute('min', todayStr);
            }
        });
    }

    // Confirm dialog before cancelling an appointment
    const cancelForms = document.querySelectorAll('.cancel-form');
    cancelForms.forEach(function (form) {
        form.addEventListener('submit', function (e) {
            const confirmed = window.confirm(
                    'Are you sure you want to cancel this appointment?\nThis action cannot be undone.'
                    );
            if (!confirmed) {
                e.preventDefault();
            }
        });
    });

    // Also handle any standalone cancel buttons with data-confirm
    const confirmButtons = document.querySelectorAll('[data-confirm]');
    confirmButtons.forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            const msg = btn.getAttribute('data-confirm') || 'Are you sure?';
            if (!window.confirm(msg)) {
                e.preventDefault();
                e.stopPropagation();
            }
        });
    });

    // Smooth scroll for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(function (anchor) {
        anchor.addEventListener('click', function (e) {
            const href = anchor.getAttribute('href');
            if (href === '#')
                return;
            const target = document.querySelector(href);
            if (target) {
                e.preventDefault();
                const navHeight = 68;
                const top = target.getBoundingClientRect().top + window.pageYOffset - navHeight - 16;
                window.scrollTo({top: top, behavior: 'smooth'});
            }
        });
    });

    // Highlight active nav link based on current path 
    const currentPath = window.location.pathname;
    document.querySelectorAll('.navbar-nav a').forEach(function (link) {
        const linkPath = link.getAttribute('href');
        if (linkPath && linkPath !== '#' && currentPath.endsWith(linkPath)) {
            link.classList.add('active');
        }
    });

    // Status form — auto-submit on select change
    const statusSelects = document.querySelectorAll('.status-select');
    statusSelects.forEach(function (sel) {
        sel.addEventListener('change', function () {
            const form = sel.closest('form');
            if (form) {
                form.submit();
            }
        });
    });

});
