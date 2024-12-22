package org.jeecg.modules.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录表单
 *
 * @Author scott
 * @since  2019-01-18
 */
@Data
public class SysLoginModel {
    private String username;
    private String password;
    private String captcha;
    private String checkKey;
}
