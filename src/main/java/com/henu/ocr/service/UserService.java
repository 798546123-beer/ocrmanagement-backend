package com.henu.ocr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henu.ocr.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    public List<User> getUserByNameFuzzy(String name);

    List<User> getUserById(String id);

    boolean addUser(User user);
}
