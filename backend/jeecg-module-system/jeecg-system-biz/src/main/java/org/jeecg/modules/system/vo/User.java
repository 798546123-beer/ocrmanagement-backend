package org.jeecg.modules.system.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.jeecg.common.system.util.JwtUtil;

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
    public User(org.jeecg.modules.system.pojo.User user){
        JSONObject information = new JSONObject();
        information.put("name",user.getUsername());
        information.put("number",user.getNumber());
        information.put("phone",user.getPhone());
        information.put("gender",user.getGender());
        this.information=information;
        this.role=user.getType_name();
        this.username=user.getUsername();
        this.token= JwtUtil.sign(user.getUsername(),user.getPassword());
    }

}
