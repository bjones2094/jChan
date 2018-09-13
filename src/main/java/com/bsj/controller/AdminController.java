package com.bsj.controller;

import com.bsj.service.AdminService;
import com.bsj.util.AuthorizationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping
    public String adminPost(Model model,
                        @RequestParam(value = "sentFromURL", required = false) String sentFromURL) {
        if(StringUtils.isNotBlank(sentFromURL)) {
            model.addAttribute("sentFromURL", sentFromURL);
        }
        return "admin";
    }

    @PostMapping("/login")
    public ModelAndView adminLogin(HttpServletRequest request,
                                   RedirectAttributes redirectAttributes,
                                   @RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam(value = "sentFromURL", required = false) String sentFromURL) {
        RedirectView redirectView;
        if(adminService.authenticateAdmin(username, password)) {
            AuthorizationUtil.addAuthorization(request, username);
            String redirectURL = StringUtils.isBlank(sentFromURL) ? (request.getContextPath() + "/") : sentFromURL;
            redirectView = new RedirectView(redirectURL);
        }
        else {
            redirectAttributes.addFlashAttribute("sentFromURL", sentFromURL);
            redirectAttributes.addFlashAttribute("submitError", "Invalid username or password.");
            redirectView = new RedirectView(request.getContextPath() + "/admin");
        }
        return new ModelAndView(redirectView);
    }

    @PostMapping("/logout")
    public ModelAndView adminLogout(HttpServletRequest request,
                                    @RequestParam(value = "sentFromURL", required = false) String sentFromURL) {
        AuthorizationUtil.removeAuthorization(request);
        String redirectURL = StringUtils.isBlank(sentFromURL) ? (request.getContextPath() + "/") : sentFromURL;
        RedirectView redirectView = new RedirectView(redirectURL);
        return new ModelAndView(redirectView);
    }
}
