package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.modules.system.entity.User;
import org.jeecg.modules.system.mapper.RoleMapper;
import org.jeecg.modules.system.mapper.UserMapper;
import org.jeecg.modules.system.pojo.UserInfo;
import org.jeecg.modules.system.service.RoleService;
import org.jeecg.modules.system.service.UserService;
import org.jeecg.modules.system.util.EncodeUtil;
import org.jeecg.modules.system.vo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zoey
 * \\_/__/
 * @Date: 2024/11/11/21:17
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    public UserMapper userMapper;
    @Resource
    public RoleMapper roleMapper;
    @Autowired
    public BaseCommonService baseCommonService;
    @Resource
    private RoleService roleService;

    @Override
    public boolean checkUserIsEffective(String username) {
        return userMapper.getUserName(username) != null;
    }

    @Override
    public UserInfo getUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        try {
            userInfo.setRole(roleMapper.getRoleById(user.getUserTypeId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        userInfo.setId(user.getId());
        userInfo.setAge(user.getUserAge());
        userInfo.setUsername(user.getName());
        userInfo.setPassword(user.getPwd());
        userInfo.setGender(user.getUserGender());
        userInfo.setNumber(user.getUserNumber());
        userInfo.setPhone(user.getUserPhone());
        if (userInfo.getRole() == null) {
            System.out.println("用户角色不存在");
        }
        return userInfo;
    }
    @Override
    public org.jeecg.modules.system.vo.User getUserVO(UserInfo userInfo){
        Role roleVO=roleService.getRoleVO(userInfo.getRole());
        return new org.jeecg.modules.system.vo.User(userInfo, roleVO.getPermissionList());
    }

    @Override
    public boolean save(User user) {
        try {
            user.setPwd(EncodeUtil.encodePassword(user.getPwd()));
            userMapper.insert(user);
        } catch (Exception e) {
            log.debug("md5加密出错");
            return false;
        }
        return true;
    }

}
