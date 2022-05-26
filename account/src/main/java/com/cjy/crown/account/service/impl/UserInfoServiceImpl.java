package com.cjy.crown.account.service.impl;

import com.cjy.crown.account.dto.UserView;
import com.cjy.crown.account.service.UserInfoService;
import com.cjy.crown.common.dto.Result;
import com.cjy.crown.security.repository.UserRepository;
import com.cjy.crown.security.utils.WebUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/15 20:38
 * @description：
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Result getUserInfo() {
        Result result = new Result();
        UserView userView = new UserView();
        BeanUtils.copyProperties(userRepository.findById(WebUtil.getUserId()).get(), userView);
        result.addData("userInfo", userView);
        return result;
    }
}
