package com.bsj.interceptor;

import com.bsj.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BannerContentInterceptor implements HandlerInterceptor {
    @Autowired
    private BoardService boardService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        request.setAttribute("boards", boardService.getBoardNames());
        return true;
    }
}
