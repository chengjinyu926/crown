package com.cjy.crown.security.filter;

import com.cjy.crown.security.bo.LoginUser;
import com.cjy.crown.security.constants.RedisConstant;
import com.cjy.crown.security.entity.User;
import com.cjy.crown.security.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/9 14:30
 * @description：
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isEmpty(token)||token.equals("NULL")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String userId = JwtUtil.getUserId(token);
            User user = (User) redisTemplate.opsForValue().get(RedisConstant.USER_ID + userId);
        if (Objects.isNull(user)){
            //TODO
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(new LoginUser(user),null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
