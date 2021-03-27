package com.mc.refillCard.config.shiro;

import com.mc.refillCard.common.UserConstants;
import com.mc.refillCard.config.shiro.jwt.AuthToken;
import com.mc.refillCard.entity.Menu;
import com.mc.refillCard.entity.Role;
import com.mc.refillCard.service.MenuService;
import com.mc.refillCard.service.RoleService;
import com.mc.refillCard.service.UserService;
import com.mc.refillCard.util.JwtUtil;
import com.mc.refillCard.util.RedisUtil;
import com.mc.refillCard.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    @Autowired
    MenuService menuService;
    @Autowired
    RoleService roleService;

    //必须重写，不然会报错
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("开始执行授权操作.......");

        //如果身份认证的时候没有传入User对象，这里只能取到userName
        //也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要User对象
        UserVo user = (UserVo) principalCollection.getPrimaryPrincipal();
        Long userId = user.getId();
        //获取用户模块（权限）
        List<Menu> menuList = menuService.findMenuByUserId(userId);
        Role role = roleService.findRoleByUserId(userId);
        //创建一个返回对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //判断用户权限
        if (menuList != null && menuList.size()>0){
            for (Menu menu :menuList) {
                //遍历权限集合，将权限结合添加到返回对象中
                info.addStringPermission(menu.getDisplayName());
            }
        }
        if (role!=null){
            info.addRole(String.valueOf(role.getGrade()));
        }

        return info;
    }

    //验证用户
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)  throws AuthenticationException {

        log.info("验证开始。。。");
        String token = (String) authenticationToken.getCredentials();

        //获取token中的userId
        String userId = JwtUtil.getUserId(token);
        if(!RedisUtil.hasKey(userId)){
            throw new IncorrectCredentialsException("身份验证失效，请重新登录");
        }
        String redisToken = RedisUtil.getStr(userId);
        if(!redisToken.equals(token)){
//            throw new RuntimeException("身份验证失败，请重新登录");
            throw new IncorrectCredentialsException("身份验证失效，请重新登录");
        }
        //token验证、
//        JwtUtil.checkSign(token);
        //去除app登录时添加的标记
        if(userId.indexOf(UserConstants.APP)>-1){
            userId = userId.replaceAll(UserConstants.APP,"");
        }
        UserVo userVo = userService.findVoById(Long.valueOf(userId));
        userVo.setToken(token);
        return new SimpleAuthenticationInfo(userVo, token, this.getName());
    }

}
