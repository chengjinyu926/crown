package com.cjy.crown.account.service.impl;

import com.cjy.crown.security.bo.LoginUser;
import com.cjy.crown.security.entity.User;
import com.cjy.crown.security.enums.UserState;
import com.cjy.crown.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/30 15:43
 * @description：
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository  userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
      User user = userRepository.findByLoginame(s);
        if (user==null){
            throw new UsernameNotFoundException("用户不存在");
        }else if (user.getState().equals(UserState.FORBIDDEN)){
            throw new UsernameNotFoundException("用户已被禁用");
        }
        return new LoginUser(user);
    }
}
