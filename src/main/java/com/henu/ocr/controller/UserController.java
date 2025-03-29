package com.henu.ocr.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.henu.ocr.IgnoreToken;
import com.henu.ocr.entity.User;
import com.henu.ocr.service.UserService;
import com.henu.ocr.util.Result;
import com.henu.ocr.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//在这个类里写user的增删查改
@RestController
@Tag(name = "用户接口")
@RequestMapping("user")
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("getUserById")
    @Operation(summary = "根据id查询用户信息")
    public List<User> getUserById(@RequestParam String id) {
        return userService.getUserById(id);
    }

    @GetMapping("getUserByNameFuzzy")
    @Operation(summary = "模糊查询用户信息")
    public Result<?> fuzzyQuery(@RequestParam String keyword) {
        try {
            List<User> userList = userService.getUserByNameFuzzy(keyword);
            if (userList.isEmpty()) {
                return Result.error("没有符合要求的人员");
            }
            return Result.OK("查询成功", userList);
        } catch (Exception e) {
            return Result.Exception(e.getMessage());
        }
    }

    @Operation(summary = "添加用户", description = "必需字段username,password,userTypeId，realname，非必需字段age，phone，userCompanyId，userGender")
    @PostMapping("addUser")
    public Result<?> addUser(@RequestBody User user) {
        try {
            boolean flag = userService.addUser(user);
            if (flag) {
                return Result.OK("添加成功");
            } else {
                return Result.error("添加失败");
            }
        } catch (Exception e) {
            return Result.Exception(e.getMessage());
        }
    }

    @Operation(summary = "更新用户信息", description = "根据用户ID更新用户信息，支持增量更新")
    @PutMapping("updateById")
    public Result<?> updateUser(@RequestBody User user) {
        try {
            if (userService.updateUser(user)) {
                return Result.OK("更新成功");
            } else {
                return Result.error("更新失败");
            }
        } catch (Exception e) {
            return Result.Exception(e.getMessage());
        }
    }

    @Operation(summary = "根据ID删除用户", description = "根据用户ID删除用户信息")
    @DeleteMapping("/deleteUserById")
    public Result<?> deleteUserById(@RequestParam String id) {
        try {
            boolean flag = userService.deleteUserById(id);
            if (flag) {
                return Result.OK("删除成功");
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            return Result.Exception(e.getMessage());
        }
    }

    //按照用户number排序全部用户信息
    @Operation(summary = "获取全部用户信息按照从小到大序号排序", description = "pageNum是页码；pageSize是页面数据行数,默认15")
    @GetMapping("getAllUserByOrder")
    public Result<?> getAllUserByOrder(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "15") Integer pageSize) {
        try {
            IPage<UserVO> userPage = userService.getAllUserVOByOrder(userService.getAllUserDTOByOrder(pageNum, pageSize));
            if (userPage.getRecords().isEmpty()) {
                return Result.error("查询失败，列表为空");
            }
            return Result.OK("查询成功", userPage);
        } catch (Exception e) {
            return Result.Exception(e.getMessage());
        }
    }

    @IgnoreToken
    @GetMapping("test")
    public String test() {
        return "test";
    }
}
