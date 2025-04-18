package com.henu.ocr.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    private String userId;
    private String username;
    private String realname;
    private String userTypeId;
    private String userTypeName;
    private String userPhone;
    private int userNumber;
    private String userGender;
    private String userCompanyId;
    private String userCompanyName;
    private int age;
    private Boolean isDelete;
}
