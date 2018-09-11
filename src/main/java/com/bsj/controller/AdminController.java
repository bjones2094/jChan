package com.bsj.controller;

import com.bsj.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping
    public String admin() {
        return "admin";
    }

    @PostMapping("/login")
    public ModelAndView adminLogin(HttpServletRequest request,
                                   RedirectAttributes redirectAttributes,
                                   @RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        RedirectView redirectView;
        if(adminService.authenticateAdmin(username, password)) {
            request.getSession().setAttribute("admin", true);
            redirectView = new RedirectView(request.getContextPath() + "/");
        }
        else {
            redirectAttributes.addFlashAttribute("submitError", "Invalid username or password.");
            redirectView = new RedirectView(request.getContextPath() + "/admin");
        }
        return new ModelAndView(redirectView);
    }

    @PostMapping("/logout")
    public ModelAndView adminLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
        RedirectView redirectView = new RedirectView(request.getContextPath() + "/admin");
        return new ModelAndView(redirectView);
    }
}
