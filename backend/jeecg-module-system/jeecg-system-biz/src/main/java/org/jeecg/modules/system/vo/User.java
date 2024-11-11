package org.jeecg.modules.system.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.system.pojo.UserInfo;

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
    private String role;
    private String permission;
    private JSONObject  information;
    public User(UserInfo userInfo){
        JSONObject information = new JSONObject();
        information.put("name", userInfo.getUsername());
        information.put("number", userInfo.getNumber());
        information.put("phone", userInfo.getPhone());
        information.put("gender", userInfo.getGender());
        this.information=information;
        this.role= userInfo.getType_name();
        this.username= userInfo.getUsername();
        this.token= JwtUtil.sign(userInfo.getUsername(), userInfo.getPassword());
    }

}
