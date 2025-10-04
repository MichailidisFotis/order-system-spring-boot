package com.example.order_system_spring_boot.middlewares;


import com.example.order_system_spring_boot.components.users.User;
import com.example.order_system_spring_boot.helpers.ResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class RequireLogin implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        ResponseEntity responseEntity;


        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        System.out.print("User Session:");
        System.out.println(user);

        if (user == null) {

            responseEntity = ResponseHandler.responseBuilder("Login is required to proceed", HttpStatus.UNAUTHORIZED, null, false);

            res.setStatus(401);
            res.setContentType("application/json");
            String jsonResponse = objectMapper.writeValueAsString(responseEntity.getBody());
            res.getWriter().write(jsonResponse);
            return;
        }

        filterChain.doFilter(req, res);

    }


    private void sendErrorResponse(HttpServletResponse httpResponse,
                                   org.springframework.http.ResponseEntity<Object> responseEntity, int status) throws IOException {
        httpResponse.setStatus(status);
        httpResponse.setContentType("application/json");
        String jsonResponse = objectMapper.writeValueAsString(responseEntity.getBody());
        httpResponse.getWriter().write(jsonResponse);
    }
}
