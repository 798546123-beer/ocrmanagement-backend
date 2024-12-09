package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.stereotype.Component;

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
    @TableField(value = "permission")
    private String permission;
    @TableField(value = "role_name")
    private String roleName;
}
