package com.henu.ocr.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henu.ocr.entity.User;
import com.henu.ocr.mapper.UserMapper;
import com.henu.ocr.model.UserDTO;
import com.henu.ocr.service.UserService;
import com.henu.ocr.util.EncodeUtil;
import com.henu.ocr.util.RedisUtil;
import com.henu.ocr.vo.UserVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<User> getUserByNameFuzzy(String keyword) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", keyword);
        return list(queryWrapper);
    }

    @Override
    public List<User> getUserById(String id) {
        //用mybatisplus里面的查询语句写
        return Collections.singletonList(getById(id));
    }

    @Override
    public boolean addUser(User user) {
        user.setPassword(EncodeUtil.encodePassword(user.getPassword()));
        return save(user);
    }

    @Override
    public boolean updateUser(User user) {
        User existingUser = getById(user.getUserId());
        if (existingUser == null) {
            return false;
        }
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            existingUser.setPassword(EncodeUtil.encodePassword(user.getPassword()));
        }
        if (user.getRealname() != null) {
            existingUser.setRealname(user.getRealname());
        }
        if (user.getUserTypeId() != null) {
            existingUser.setUserTypeId(user.getUserTypeId());
        }
        if (user.getUserPhone() != null) {
            existingUser.setUserPhone(user.getUserPhone());
        }
        if (user.getUserNumber() != 0) {
            existingUser.setUserNumber(user.getUserNumber());
        }
        if (user.getUserGender() != null) {
            existingUser.setUserGender(user.getUserGender());
        }
        if (user.getUserCompanyId() != null) {
            existingUser.setUserCompanyId(user.getUserCompanyId());
        }
        if (!ObjectUtils.isEmpty(user.getAge())) {
            existingUser.setAge(user.getAge());
        }
        if (user.getIsDelete() != null) {
            existingUser.setIsDelete(user.getIsDelete());
        }
        return updateById(existingUser);
    }

    @Override
    public boolean deleteUserById(String id) {
        return removeById(id);
    }

    @Override
    public IPage<User> getAllUserByOrder(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public IPage<UserDTO> getAllUserDTOByOrder(Integer pageNum, Integer pageSize) {
        Page<UserDTO> page = new Page<>(pageNum, pageSize);
        return userMapper.selectUserDTOPage(page);
    }

    @Override
    public IPage<UserVO> getAllUserVOByOrder(IPage<UserDTO> userDTOlist) {
        return userDTOlist.convert(userDTO -> {
            UserVO userVO = new UserVO();
            userVO.setUserId(userDTO.getUserId());
            userVO.setUsername(userDTO.getUsername());
            userVO.setRealname(userDTO.getRealname());
            userVO.setUserTypeId(userDTO.getUserTypeId());
            userVO.setUserTypeName(userDTO.getUserTypeName());
            userVO.setUserPhone(userDTO.getUserPhone());
            userVO.setUserNumber(userDTO.getUserNumber());
            userVO.setUserGender(userDTO.getUserGender());
            userVO.setUserCompanyId(userDTO.getUserCompanyId());
            userVO.setUserCompanyName(userDTO.getUserCompanyName());
            userVO.setIsDelete(userDTO.getIsDelete());
            userVO.setAge(userDTO.getAge());
            return userVO;
        });
    }

    @Override
    @Cacheable(value = "userVO", key = "#id")
    public UserVO getUserVOById(@NotNull String id) {
        return userMapper.getUserVOById(id);
    }
}
