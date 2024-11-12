package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zoey
 * \\_/__/
 * @Date: 2024/11/11/19:11
 * @Description:
 */
@Entity
@Data
@Component
@TableName("role")
public class Role {
    @Id
    @TableId(value = "role_id")
    private Integer roleId;
    @TableField(value = "permission")
    private String permission;
    @TableField(value = "role_name")
    private String roleName;

}
