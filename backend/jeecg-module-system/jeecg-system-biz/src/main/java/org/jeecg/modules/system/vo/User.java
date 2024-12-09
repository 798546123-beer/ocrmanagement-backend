package org.jeecg.modules.system.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.system.entity.Role;
import org.jeecg.modules.system.pojo.UserInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zoey
 * \\_/__/
 * @Date: 2024/11/11/15:05
 * @Description:
 */
@Data
public class User {
    //权限
    private String username;
    private String token;
    private String permission;
    private JSONObject  information;
    private Role role;
    public User(UserInfo userInfo){
        this.role=userInfo.getRole();
        JSONObject information = new JSONObject();
        information.put("name", userInfo.getUsername());
        information.put("number", userInfo.getNumber());
        information.put("phone", userInfo.getPhone());
        information.put("gender", userInfo.getGender());
        information.put("roleName", role.getRoleName());
        information.put("permission", role.getPermission());
        this.information=information;
        this.username= userInfo.getUsername();
        this.token= JwtUtil.sign(userInfo.getUsername(), userInfo.getPassword());
    }
    public User(UserInfo userInfo, List<?> permissionList){
        this.role=userInfo.getRole();
        JSONObject information = new JSONObject();
        information.put("name", userInfo.getUsername());
        information.put("number", userInfo.getNumber());
        information.put("phone", userInfo.getPhone());
        information.put("gender", userInfo.getGender());
        information.put("roleName", role.getRoleName());
        information.put("permissionList", permissionList);
        this.information=information;
        this.username= userInfo.getUsername();
        this.token= JwtUtil.sign(userInfo.getUsername(), userInfo.getPassword());
    }

}
