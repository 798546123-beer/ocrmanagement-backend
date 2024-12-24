package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.system.util.RedisCacheUtil;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.system.entity.PageEntity;
import org.jeecg.modules.system.entity.Role;
import org.jeecg.modules.system.mapper.RoleMapper;
import org.jeecg.modules.system.service.PermissionService;
import org.jeecg.modules.system.vo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.jeecg.common.api.vo.Result.ok;

/**
 * @author 25612
 */
@RestController
@RequestMapping("permission")
public class PermissionController {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RedisCacheUtil redisCacheUtil;
    @Resource
    private PermissionService permissionService;
    @IgnoreAuth
    @GetMapping("/getPermissionList")
    public Result<List<PageEntity>> getPermissionList() {
        List<PageEntity> permissionList = permissionService.getPermissionList();
        return Result.OK(permissionList);
    }
    @ApiOperation("根据id获取角色权限")
    @GetMapping("/getRolePermission")
    @IgnoreAuth
    public Result<JSONObject> getRolePermission(@Param(value = "roleId") String roleId){
        Role role = roleMapper.selectById(roleId);
        //如果是空的就报错
        if(role==null){
            return Result.Error("角色不存在！");
        }
        return ok(String.valueOf(role));
    }
    @ApiOperation("根据token获取角色权限")
    @GetMapping("/getRolePermissionByToken")
    @IgnoreAuth
    public Result<?> getRolePermission(@Param(value = "token") String token, HttpServletRequest request){
        token=token==null?request.getHeader("X-ACCESS-TOKEN"):token;
        //把token解析得到username和password
        String username= JwtUtil.getUsername(token);
        //去redis里查询user表符合username的数据项的role字段
        User userVO= (User) redisCacheUtil.get(CommonConstant.LOGIN_SUCCESS+username);
        JSONObject information = userVO.getInformation();
        JSONArray permissionList = information.getJSONArray("permissionList");
        if (permissionList == null || permissionList.isEmpty()) {
            return Result.error("没有权限！");
        }

        List<JSONObject> permissions = new ArrayList<>();
        try {
            //这个try 中可以写一个增强for循环
            for (int i = 0; i < permissionList.size(); i++) {
                permissions.add(permissionList.getJSONObject(i));
            }
        } catch (Exception e) {
            return Result.error( e.getMessage());
        }
        return Result.ok(permissions);
    }
}
