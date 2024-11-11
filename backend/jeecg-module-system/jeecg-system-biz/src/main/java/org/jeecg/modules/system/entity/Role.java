package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
public class Role {
    @Id
    @TableId(value = "role_id")
    private Integer role_id;
    @TableId(value = "permission")
    private String permission;
    @TableId(value = "role_name")
    private String role_name;
}
