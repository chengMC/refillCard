package com.mc.refillCard.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.common.UserConstants;
import com.mc.refillCard.entity.User;
import com.mc.refillCard.service.RoleService;
import com.mc.refillCard.service.UserService;
import com.mc.refillCard.util.AccountUtils;
import com.mc.refillCard.util.JwtUtil;
import com.mc.refillCard.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 *
 * 登录接口
 * @author hu
 * @date 2020/11/17 上午9:20
 */

@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @SystemControllerLog(description = "登录:登录系统")
    @PostMapping
    public Result login(@RequestBody User user){
        String userName = user.getUserName();
        String password = user.getPassword();
        User userEntity = userService.findByUserName(userName);
        if (null == userEntity) {
            return Result.fall("当前用户不存在,请重试");
        }
        String userId = String.valueOf(userEntity.getId());
        //管理员登录
        String entityPassword = userEntity.getPassword();
        String accountSecret = AccountUtils.createAccountSecret(userId, password);
        if (!entityPassword.equals(accountSecret)) {
            return Result.fall("手机号或验证码错误，请重新输入");
        }

        UserVo userVo = BeanUtil.copyProperties(userEntity, UserVo.class);
        // 生成签名
        String token = JwtUtil.sign(userId);
        userVo.setToken(token);
        //token过期时间设置
        redisTemplate.opsForValue().set(UserConstants.PREFIX_USER_TOKEN + userId, token, UserConstants.USER_TOKEN_OUT, TimeUnit.DAYS);
        //用户信息存入
        return Result.success("登录成功",userVo);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @SystemControllerLog(description = "退出:退出登录")
    @PostMapping("/logout")
    public Result logOut(@RequestBody User user) {
        Long id = user.getId();
        if(id ==null ){
            return Result.fall("无效id");
        }
        UserVo userVo = userService.findVoById(id);
        userService.logOut(user);
        return Result.success("退出成功",userVo);
    }

}
