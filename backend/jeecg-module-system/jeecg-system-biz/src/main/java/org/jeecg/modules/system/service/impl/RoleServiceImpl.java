package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.PageEntity;
import org.jeecg.modules.system.entity.Role;
import org.jeecg.modules.system.mapper.PermissionMapper;
import org.jeecg.modules.system.mapper.RoleMapper;
import org.jeecg.modules.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zoey
 * \\_/__/
 * @Date: 2024/11/11/22:23
 * @Description:
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Autowired
    private Role role;

    @Override
    public org.jeecg.modules.system.vo.Role getRoleVO(Role role){
        org.jeecg.modules.system.vo.Role roleVO = new org.jeecg.modules.system.vo.Role(role);
            List<String> permissionArray = Arrays.asList(role.getPermission().split(";"));
        List<PageEntity> permissionList = permissionMapper.getPermissionList();
        System.out.println(permissionList);
            //写一个for循环查询role_page表中id对应的权限的名字
        //pageEntity举例子如下：[PageEntity(pageName=数据采集, page=data_collection, id=1), PageEntity(pageName=项目管理, page=project_management, id=2), PageEntity(pageName=检验批管理, page=inspect_lot_management, id=3), PageEntity(pageName=数据报表, page=data_report, id=4)]
        List<String> temp_list=new ArrayList<>();
        for (int i = 0; i < permissionList.size(); i++) {
                PageEntity permission = permissionList.get(i);
                if(permissionArray.contains(permission.getId().toString())
                ){
                    temp_list.add(permission.toString());
                }
        }
        roleVO.setPermissionList(temp_list);
        return roleVO;
    }
}
