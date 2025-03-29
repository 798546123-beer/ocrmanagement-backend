package com.henu.ocr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henu.ocr.entity.User;
import com.henu.ocr.model.UserDTO;
import com.henu.ocr.vo.UserVO;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> getUserByNameFuzzy(String name);

    List<User> getUserById(String id);

    boolean addUser(User user);

    IPage<User> getAllUserByOrder(Integer pageNum, Integer pageSize);

    IPage<UserDTO> getAllUserDTOByOrder(Integer pageNum, Integer pageSize);

    IPage<UserVO> getAllUserVOByOrder(IPage<UserDTO> userDTOlist);

    UserVO getUserVOById(String id);

    boolean updateUser(User user);

    boolean deleteUserById(String id);
}