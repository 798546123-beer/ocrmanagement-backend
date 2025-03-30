package com.henu.ocr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("role_id")
    private Integer roleId;
    @TableField("permission_id")
    private String permissionId;
    public Permission(Integer roleId,String permissionId){
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
}