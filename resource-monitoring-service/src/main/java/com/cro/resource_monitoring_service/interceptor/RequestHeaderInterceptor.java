package com.cro.resource_monitoring_service.interceptor;

import com.cro.resource_monitoring_service.constant.ApplicationConstants;
import com.cro.resource_monitoring_service.exception.UnauthorizedOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
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
            throw new UnauthorizedOperationException("Missing or invalid authorization headers");
        }
        return true;
    }
}
