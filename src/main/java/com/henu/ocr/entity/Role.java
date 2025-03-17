package com.henu.ocr.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henu.ocr.pojo.PermissionModel;
import io.swagger.v3.core.util.Json;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zoey
 * \\_/__/
 * @Date: 2024/11/11/19:11
 * @Description:
 */
@Data
@Component
@TableName("role")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    private Integer roleId;
    @TableField(exist = false)
    private List<PermissionModel> permissions;
    @TableField(value = "role_name")
    private String roleName;
}
