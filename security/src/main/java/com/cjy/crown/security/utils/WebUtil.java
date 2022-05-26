package com.cjy.crown.security.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/11 15:02
 * @description：
 */
public class WebUtil {
    private static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static String getUserId() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token) || token.equals("NULL")) {
            return null;
        } else {
            return JwtUtil.getUserId(token);
        }
    }

    public static String getToken() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return request.getHeader("Authorization");
    }
}
