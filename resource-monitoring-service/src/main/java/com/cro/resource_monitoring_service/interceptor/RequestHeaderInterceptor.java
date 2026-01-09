package com.cro.resource_monitoring_service.interceptor;

import com.cro.resource_monitoring_service.constant.ApplicationConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle( HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler) throws Exception {

        String username = request.getHeader(ApplicationConstants.X_CRO_USERNAME);
        String role = request.getHeader(ApplicationConstants.X_CRO_ROLE);
        if (username == null || StringUtils.isBlank(username) || role == null || StringUtils.isBlank(role)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(
                    "Missing or invalid authentication headers"
            );
            return false;
        }
        return true;
    }
}
