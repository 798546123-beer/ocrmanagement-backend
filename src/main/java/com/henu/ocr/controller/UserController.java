package com.henu.ocr.controller;

import com.henu.ocr.entity.User;
import com.henu.ocr.service.UserService;
import com.henu.ocr.util.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//在这个类里写user的增删查改
@RestController
@Api(tags = "用户接口")
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/getUserById")
    public List<User> getUserById(@RequestParam String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getUserByNameFuzzy")
    public Result fuzzyQuery(@RequestParam String keyword) {
        try {
            List<User> userList = userService.getUserByNameFuzzy(keyword);
            if (userList.size() == 0) {
                return new Result(400, "查询失败");
            }
            return Result.OK("查询成功", userList);
        } catch (Exception e) {
            return Result.Exception();
        }
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user) {
        try {
            boolean flag = userService.addUser(user);
            if (flag) {
                return Result.OK("添加成功");
            } else {
                return Result.error("添加失败");
            }
        } catch (Exception e) {
            return Result.Exception();
        }
    }
    //按照用户number排序全部用户信息
    @GetMapping("/getAllUserByOrder")
    public Result getAllUserByOrder(){
        try {
            List<User> userList = userService.getAllUserByOrder();
            if (userList.size() == 0) {
                return Result.error( "查询失败，列表为空");
            }
            return Result.OK("查询成功", userList);
        } catch (Exception e) {
            return Result.Exception();
        }
    }
    @GetMapping("test")
    public String test(){
        return "test";
    }
}
