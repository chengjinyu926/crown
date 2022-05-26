package com.cjy.crown.account.service;

import com.cjy.crown.account.dto.PwdLoginModel;
import com.cjy.crown.account.dto.RegistryUserDTO;
import com.cjy.crown.common.dto.Result;
import org.springframework.http.ResponseEntity;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/29 18:53
 * @description：
 */
public interface LoginService {
    Result registry(RegistryUserDTO registryUserDTO);
    Result loginWithPwd(PwdLoginModel loginModel);
    Result sendLoginCode(String phone);
    Result validateLoginCode(String phone, String code);
}
