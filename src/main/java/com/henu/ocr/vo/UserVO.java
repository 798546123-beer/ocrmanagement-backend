package com.henu.ocr.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.henu.ocr.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Data
@Component
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    private String userId;
    private String username;
    private String realname;
    private String userTypeId;
    private String userPhone;
    private int userNumber;
    private String userGender;
    private String userCompanyId;
    private int age;
    private Boolean isDelete;
    @Autowired
    public UserVO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.realname = user.getRealname();
        this.userTypeId = user.getUserTypeId();
        this.userPhone = user.getUserPhone();
        this.userNumber = user.getUserNumber();
        this.userGender = user.getUserGender();
        this.userCompanyId = user.getUserCompanyId();
        this.age = user.getAge();
        this.isDelete = user.getIsDelete();
    }
}
