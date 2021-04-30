package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.UserDto;
import com.mc.refillCard.dto.UserSaveDto;
import com.mc.refillCard.entity.User;
import com.mc.refillCard.entity.UserRelate;
import com.mc.refillCard.vo.UserVo;

import java.util.List;

/****
 * @Author: MC
 * @Description:User业务层接口
 * @Date 2020-8-11 17:57:52
 *****/
public interface UserService {


    /***
     * User多条件分页查询
     * @param user
     * @param page
     * @param size
     * @return
     */
    PageInfo findPage(User user, int page, int size);

    /***
     * User多条件搜索方法
     * @param user
     * @return
     */
    List<User> findList(User user);

    /**
    * 根据ID查询User
    * @param id
    * @return
    */
    User findById(Long id);

    /**
     * 根据ID查询User
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /***
     * 删除User
     * @param id
     */
    void delete(Long id);

    /***
     * 修改User数据
     * @param user
     */
    void update(User user);

    void updateDto(UserSaveDto userDto) throws Exception;

    /***
     * 新增User
     * @param user
     */
    void add(User user);

    void addDto(UserSaveDto userDto) throws Exception;

    /***
     * 查询所有User
     * @return
     */
    List<UserVo> findAll();

    UserVo findUser(User user);

    UserVo findVoById(Long id);

    /**
     * 取消用户的管理员权限
     *
     * @param token
     * @param user
     * @return
     */
    Result relieveAdmin(String token, UserDto user);

    /**
     * 退出登录
     *
     * @param user
     */
    void logOut(User user);


    /**
     * 修改用户状态
     *
     * @param userDto
     */
    void updateStatus(UserDto userDto);

    /**
     *  修改密码
     *
     * @param userDto
     */
    void updatePwd(UserSaveDto userDto) throws Exception;

    UserRelate save(String platformUserId, String sellerNick);


    User findPlatformUserId(String platformUserId);

    void resetPwd();

}
