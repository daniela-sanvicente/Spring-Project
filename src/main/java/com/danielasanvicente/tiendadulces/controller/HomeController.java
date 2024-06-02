package com.danielasanvicente.tiendadulces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        boolean isLoggedIn = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    isLoggedIn = true;
                    break;
                }
            }
        }

        if (isLoggedIn) {
            model.addAttribute("navbar", "navbar_loggedin");
        } else {
            model.addAttribute("navbar", "navbar_loggedout");
        }

        return "index"; // Ensure home.html includes the navbar fragment appropriately
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}