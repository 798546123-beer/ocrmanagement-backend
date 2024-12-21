package org.jeecg.common.api;

import org.jeecg.common.system.vo.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public interface CommonAPI {

    /**
     * 1查询用户角色信息
     * @param username
     * @return
     */
    Set<String> queryUserRoles(String username);

    /**
     * 1查询用户角色信息
     * @param userId
     * @return
     */
    Set<String> queryUserRolesById(String userId);


    /**
     * 2查询用户权限信息
     * @param userId
     * @return
     */
    Set<String> queryUserAuths(String userId);

    /**
     * 5根据用户账号查询用户信息
     * @param username
     * @return
     */
    public LoginUser getUserByName(String username);

    /**
     * 5根据用户账号查询用户Id
     * @param username
     * @return
     */
    public String getUserIdByName(String username);


    /**
     * 9查询用户信息
     * @param username
     * @return
     */
    SysUserCacheInfo getCacheUser(String username);

    /**
     * 14 普通字典的翻译，根据多个dictCode和多条数据，多个以逗号分割
     * @param dictCodes 例如：user_status,sex
     * @param keys 例如：1,2,0
     * @return
     */
    Map<String, List<DictModel>> translateManyDict(String dictCodes, String keys);


}
