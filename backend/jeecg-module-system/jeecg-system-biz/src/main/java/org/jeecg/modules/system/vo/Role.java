package org.jeecg.modules.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    private Integer roleId;
    private String roleName;
    private String permission;
    private List<String> permissionList;
    public Role(org.jeecg.modules.system.entity.Role role){
        this.roleId=role.getRoleId();
        this.roleName=role.getRoleName();
        this.permission=role.getPermission();
    }
}
