package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.common.UserConstants;
import com.mc.refillCard.dao.UserMapper;
import com.mc.refillCard.dto.UserDto;
import com.mc.refillCard.dto.UserSaveDto;
import com.mc.refillCard.entity.RoleUser;
import com.mc.refillCard.entity.User;
import com.mc.refillCard.entity.UserRelate;
import com.mc.refillCard.service.RoleService;
import com.mc.refillCard.service.RoleUserService;
import com.mc.refillCard.service.UserRelateService;
import com.mc.refillCard.service.UserService;
import com.mc.refillCard.util.AccountUtils;
import com.mc.refillCard.util.RedisUtil;
import com.mc.refillCard.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/****
 * @Author: MC
 * @Description: User业务层接口实现类
 * @Date 2020-8-11 17:57:52
 *****/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleUserService roleUserService;
    @Lazy
    @Autowired
    private UserRelateService userRelateService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     User条件+分页查询
     @param user 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(User user, int page, int size) {
        List<UserVo> userVos = new ArrayList<>();
        //分页
        PageHelper.startPage(page, size);
        List<User> users = userMapper.selectByExample(user);
        for (User userEntity : users) {
            UserVo userVo = BeanUtil.copyProperties(userEntity, UserVo.class);
            userVo.setToken("");
            userVos.add(userVo);
        }
        PageInfo pageInfo = new PageInfo(users);
        pageInfo.setList(userVos);
        return pageInfo;
    }

    /**
     * User条件查询
     * @param user
     * @return
     */
    @Override
    public List<User> findList(User user){
        //根据构建的条件查询数据
        return userMapper.selectByExample(user);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        userMapper.deleteByPrimaryKey(id);
        roleUserService.deleteByUserId(id);
    }

    /**
     * 修改User
     * @param user
     */
    @Override
    public void update(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updateDto(UserSaveDto userDto) throws Exception {
        User userValid = new User();
        userValid.setPhone(userDto.getPhone());
        userValid.setId(userDto.getId());
        User resultUser = userMapper.findByPrimary(userValid);
        if(resultUser !=null ){
            throw new Exception("联系电话已存在，请重新添加");
        }
        userValid = new User();
        userValid.setValidUserName(userDto.getUserName());
        userValid.setId(userDto.getId());
        resultUser = userMapper.findByPrimary(userValid);
        if(resultUser !=null ){
            throw new Exception("用户名已存在，请重新添加");
        }
        //验证密码
        String password = userDto.getPassword();
        String affirmPassword = userDto.getAffirmPassword();
        //密码和确认密码不一致提醒
        if(StringUtils.isNotBlank(password) && StringUtils.isNotBlank(affirmPassword)){
            if(!password.equals(affirmPassword)){
                throw new Exception("输入的密码和确认密码不一致，请重新输入");
            }
        }
        User user = BeanUtil.copyProperties(userDto, User.class);
        userMapper.updateByPrimaryKeySelective(user);
        //保存用户和权限
        saveUser(user);
    }

    /**
     * 修改用户状态
     *
     * @param userDto
     */
    @Override
    public void updateStatus(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setStatus(userDto.getStatus());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updatePwd(UserSaveDto userDto) throws Exception {
        //密码和确认密码
        String password = userDto.getPassword();
        String affirmPassword = userDto.getAffirmPassword();
        Long id = userDto.getId();
        if(StringUtils.isBlank(password)){
            throw new Exception("请输入的密码");
        }
        if(StringUtils.isBlank(affirmPassword)){
            throw new Exception("请输入的确认密码");
        }
        //密码和确认密码不一致提醒
        if(!password.equals(affirmPassword)){
            throw new Exception("输入的密码和确认密码不一致，请重新输入");
        }

        //原始密码校验
        String originalPassword = userDto.getOriginalPassword();
        if(StringUtils.isNotBlank(originalPassword)){
            if(originalPassword.equals(affirmPassword)){
                throw new Exception("原始密码不能和修改的密码一样，请重新输入");
            }
            String accountSecret = AccountUtils.createAccountSecret(String.valueOf(id), originalPassword);
            User user = userMapper.selectByPrimaryKey(id);
            if(!accountSecret.equals(user.getPassword())){
                throw new Exception("输入的原始密码错误，请重新输入");
            }
        }
        //加密后保存密码
        String accountSecret = AccountUtils.createAccountSecret(String.valueOf(id), password);
        User user =new User();
        user.setId(id);
        user.setPassword(accountSecret);
        userMapper.updateByPrimaryKeySelective(user);
        //退出登录
        logOut(user);
    }

    @Override
    public UserRelate save(String platformUserId, String sellerNick) {
        User user = new User();
        user.setUserName(sellerNick);
        user.setRemark(platformUserId);
        user.setRoleId(3L);
        user.setRoleName("普通用户");
        //保存用户
        userMapper.insertSelective(user);
        Long id = user.getId();
        String accountSecret = AccountUtils.createAccountSecret(String.valueOf(id), "123456");
        user.setPassword(accountSecret);
        user.setId(id);
        userMapper.updateByPrimaryKeySelective(user);
        //保存用户对照
        UserRelate userRelate = new UserRelate();
        userRelate.setAgisoUserId(platformUserId);
        userRelate.setUserId(id);
        userRelateService.add(userRelate);
        //保存用户组
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleId(3L);
        roleUser.setUserId(id);
        roleUserService.add(roleUser);
        return userRelate;
    }

    @Override
    public User findPlatformUserId(String platformUserId) {
        return userMapper.findPlatformUserId(platformUserId);
    }

    @Override
    public void resetPwd() {
        List<User> all = userMapper.findAll();
        for (User user : all) {
            if(!"admin".equals(user.getUserName())){
                String accountSecret = AccountUtils.createAccountSecret(String.valueOf(user.getId()), "123456");
                user.setPassword(accountSecret);
                update(user);
            }
        }
    }

    /**
     * 增加User
     * @param user
     */
    @Override
    public void add(User user){
        userMapper.insertSelective(user);
    }

    @Override
    public void addDto(UserSaveDto userDto) throws Exception {
        User userValid = new User();
        userValid.setPhone(userDto.getPhone());
        User resultUser = userMapper.findByPrimary(userValid);
        if(resultUser !=null ){
            throw new Exception("联系电话已存在，请重新添加");
        }
        userValid = new User();
        userValid.setValidUserName(userDto.getUserName());
        resultUser = userMapper.findByPrimary(userValid);
        if(resultUser !=null ){
            throw new Exception("用户名已存在，请重新添加");
        }

        User user = BeanUtil.copyProperties(userDto, User.class);
        userMapper.insertSelective(user);
        //保存用户和权限
        saveUser(user);
    }

    /**
     *  保存用户和权限
     * @param user
     */
    private void saveUser(User user) {
        //id
        Long id = user.getId();
        String password = user.getPassword();
        //密码加密
        if(StringUtils.isNotEmpty(password)){
            String accountSecret = AccountUtils.createAccountSecret(String.valueOf(id), password);
            user.setPassword(accountSecret);
            userMapper.updateByPrimaryKeySelective(user);
        }
        //加入权限
        Long roleId = user.getRoleId();
        if(roleId !=null){
            roleUserService.deleteByUserId(id);
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleId(roleId);
            roleUser.setUserId(id);
            roleUserService.add(roleUser);
        }
    }

    /**
     * 根据ID查询User
     * @param id
     * @return
     */
    @Override
    public User findById(Long id){
        return  userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    /**
     * 查询User全部数据
     * @return
     */
    @Override
    public List<UserVo> findAll() {
        List<UserVo> userVos = new ArrayList<>();
        //分页
        List<User> users = userMapper.selectByExample(new User());
        for (User userEntity : users) {
            UserVo userVo = BeanUtil.copyProperties(userEntity, UserVo.class);
            userVos.add(userVo);
        }
        return userVos;
    }

    @Override
    public UserVo findUser(User user) {
        User resultUser = userMapper.findUser(user);
        UserVo userVo = BeanUtil.copyProperties(resultUser, UserVo.class);
        return userVo;
    }

    @Override
    public UserVo findVoById(Long id) {
        return BeanUtil.copyProperties(userMapper.selectByPrimaryKey(id),UserVo.class);
    }


    @Override
    public Result relieveAdmin(String token, UserDto user) {
//        //没有token是在小程序操作，有token是在web端操作
//        if(token !=null){
//            String userIdStr = JwtUtil.getUserId(token);
//            if(StringUtils.isBlank(userIdStr)){
//                return Result.fall("当前身份失效,请重新登录");
//            }
//            //判断是否为管理员操作 true:是 false:否
//            if(!roleService.isAdminRole(Long.valueOf(userIdStr))){
//                return Result.fall("当前用户不是管理员,不可操作");
//            }
//        }
//        //根据id或者名称电话查询用户
//        String userName = user.getUserName();
//        Long id = user.getId();
//        User userEntity = null;
//        if(id !=null){
//            userEntity = userMapper.selectByPrimaryKey(id);
//        }else if(userName != null){
//            userEntity = userMapper.findByUserName(userName);
//        }
//        if (null == userEntity) {
//            return Result.fall("当前用户不存在,请重试");
//        }
//        Long userId = userEntity.getId();
//        if("admin".equals(userEntity.getUserName())){
//            return Result.fall("操作admin账号,请联系系统相关人员");
//        }
//        List<RoleUser> roleUserEntity = roleUserService.findByUserId(userId);
//        if(CollectionUtil.isNotEmpty(roleUserEntity)){
//            //原来存在先删除后在添加
//            roleUserService.deleteByUserId(userId);
//        }
//        //绑定用户为普通权限组
//        roleUserService.saveRoleUser(userId, RoleEnum.BASICS.getName());
//
//        //密码置空
//        userEntity.setPassword("");
//        userMapper.updateByPrimaryKeySelective(userEntity);
//        //清空token，保证退出
//        logOut(userEntity);
        return Result.success();
    }

    @Override
    public void logOut(User user) {
        //退出清除token信息
        String userId = UserConstants.PREFIX_USER_TOKEN + user.getId();
        redisTemplate.delete(userId);
        //退出清楚用户信息
        RedisUtil.del(UserConstants.PREFIX_USER_INFO + userId);
    }

}
