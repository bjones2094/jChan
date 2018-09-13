package com.bsj.util;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationUtil {
    public static final String ADMIN_ATTRIBUTE = "admin";
    public static final String USERNAME_ATTRIBUTE = "username";

    public static void addAuthorization(HttpServletRequest request, String username) {
        request.getSession().setAttribute(ADMIN_ATTRIBUTE, true);
        request.getSession().setAttribute(USERNAME_ATTRIBUTE, username);
    }

    public static void removeAuthorization(HttpServletRequest request) {
        request.getSession().removeAttribute(ADMIN_ATTRIBUTE);
        request.getSession().removeAttribute(USERNAME_ATTRIBUTE);
    }

    public static boolean isAuthorized(HttpServletRequest request) {
        Boolean isAdmin = (Boolean) request.getSession().getAttribute(ADMIN_ATTRIBUTE);
        return (isAdmin != null && isAdmin);
    }
}
