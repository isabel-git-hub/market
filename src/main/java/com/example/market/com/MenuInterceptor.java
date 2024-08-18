package com.example.market.com;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class MenuInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 처리 전에 호출
        System.out.println("Pre-handle: " + request.getRequestURI());
        return true;  // true를 반환하면 컨트롤러로 요청이 전달됨
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러 처리 후, 뷰 렌더링 전에 호출
        System.out.println("Post-handle: " + request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 뷰 렌더링 후, 요청 완료 후 호출
        System.out.println("After-completion: " + request.getRequestURI());
    }
}
