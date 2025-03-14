package com.henu.ocr.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henu.ocr.entity.User;
import com.henu.ocr.mapper.UserMapper;
import com.henu.ocr.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
        return save(user);
    }

    @Override
    //按照User的userNumber属性从小到大排序
    public List<User> getAllUserByOrder() {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, true, "user_number");
        return list(queryWrapper);
    }
}
