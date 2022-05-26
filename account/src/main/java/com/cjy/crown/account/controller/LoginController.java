package com.cjy.crown.account.controller;

import com.cjy.crown.account.dto.PwdLoginModel;
import com.cjy.crown.account.dto.RegistryUserDTO;
import com.cjy.crown.account.service.LoginService;
import com.cjy.crown.common.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ：JinYu
 * @date ：Created in 2022/3/29 18:44
 * @description：
 */
@CrossOrigin
@RestController
@RequestMapping("account/login")
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping("registry")
    public Result registry(@RequestBody RegistryUserDTO registryUserDTO) {
        return loginService.registry(registryUserDTO);
    }

    @PostMapping("loginWithPwd")
    public Result login(@RequestBody PwdLoginModel loginModel) {
        return loginService.loginWithPwd(loginModel);
    }

    @GetMapping("sendLoginCode")
    public Result sendLoginCode(@RequestParam String phone) {
        Result result = loginService.sendLoginCode(phone);
        return result;
    }

    @GetMapping("validateLoginCode")
    public Result validateLoginCode(@RequestParam String phone, @RequestParam String code) {
        return loginService.validateLoginCode(phone, code);
    }

}
