package com.cjy.crown.account.service.impl;


import com.cjy.crown.account.dto.PwdLoginModel;
import com.cjy.crown.account.service.rest.SmsService;
import com.cjy.crown.common.dto.Result;
import com.cjy.crown.common.dto.enums.ResultEnum;
import com.cjy.crown.security.bo.LoginUser;
import com.cjy.crown.security.constants.RedisConstant;
import com.cjy.crown.security.entity.User;
import com.cjy.crown.security.enums.UserState;
import com.cjy.crown.security.repository.UserRepository;
import com.cjy.crown.account.dto.RegistryUserDTO;
import com.cjy.crown.account.service.LoginService;
import com.cjy.crown.security.utils.JwtUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/29 18:53
 * @description：
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    SmsService smsService;

    @Override
    public Result registry(RegistryUserDTO registryUserDTO) {
        if (StringUtils.isBlank(registryUserDTO.getLoginame()) || StringUtils.isBlank(registryUserDTO.getPassword())) {
            return new Result(ResultEnum.HAVE_NO_PWD_OR_LOGINAME);
        }
        if (null != userRepository.findByLoginame(registryUserDTO.getLoginame())) {
            return new Result(ResultEnum.REPEAT_LOGINAME);
        }
        if (StringUtils.isNotBlank(registryUserDTO.getPhone()) && null != userRepository.findByPhone(registryUserDTO.getPhone())) {
            return new Result(ResultEnum.REPEAT_PHONE);
        }
        if (StringUtils.isNotBlank(registryUserDTO.getEmail()) && null != userRepository.findByEmail(registryUserDTO.getEmail())) {
            return new Result(ResultEnum.REPEAT_EMAIL);
        }
        User user = new User();
        BeanUtils.copyProperties(registryUserDTO, user);
        if (StringUtils.isBlank(user.getUsername())) {
            user.setUsername(RandomStringUtils.random(10, true, true));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setState(UserState.OFFLINE.getCode());
        user.setCreateTime(new Date(System.currentTimeMillis()));
        userRepository.save(user);
        return new Result(ResultEnum.REGISTRY_SUCCESS);
    }

    @Override
    public Result loginWithPwd(PwdLoginModel loginModel) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginModel.getLoginame(), loginModel.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            return new Result(ResultEnum.DENY_LOGINAME_PWD);
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User user = loginUser.getUser();
        String token = JwtUtil.createToken(new HashMap<String, Object>() {{
            put("user_id", user.getId());
        }});
        Result result = new Result(ResultEnum.LOGIN_SUCCESS);
        result.setData(new HashMap<String, Object>() {{
            put("token", token);
        }});
        redisTemplate.opsForValue().set(RedisConstant.USER_ID + user.getId(), user);
        return result;
    }

    @Override
    public Result sendLoginCode(String phone) {
        if (userRepository.findByPhone(phone) == null) {
            return new Result(ResultEnum.NO_REGISTRY_PHONE);
        }
        String code = smsService.sendLoginCode(phone);
        Result result;
        if (StringUtils.isNotBlank(code)) {
            result = new Result(ResultEnum.SEND_LOGIG_CODE_SUCCESS);
            result.addData("code", code);
        } else {
            result = new Result(ResultEnum.SEND_LOGIG_CODE_FAILED);
        }
        return result;
    }

    @Override
    public Result validateLoginCode(String phone, String code) {
        boolean ifSuccess = smsService.validateLoginCode(phone, code);
        Result result;
        if (ifSuccess) {
            result = new Result(ResultEnum.VALIDATE_LOGIG_CODE_SUCCESS);
            User user = userRepository.findByPhone(phone);
            String token = JwtUtil.createToken(
                    new HashMap<String, Object>() {
                        {
                            put("user_id", user.getId());
                        }
                    });
            redisTemplate.opsForValue().set(RedisConstant.USER_ID + user.getId(), user);
            result.addData("token", token);
        } else {
            result = new Result(ResultEnum.VALIDATE_LOGIG_CODE_FAILED);
        }
        return result;
    }
}
